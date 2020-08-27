package mech.mania.engine.service_layer.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.Config;
import mech.mania.engine.domain.game.GameLogic;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.PlayerConnectInfo;
import mech.mania.engine.domain.model.PlayerProtos;
import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class SendPlayerRequestsAndUpdateGameState extends CommandHandler {
    public SendPlayerRequestsAndUpdateGameState(UnitOfWorkAbstract uow) {
        super(uow);
    }
    private static final Logger LOGGER = Logger.getLogger(SendPlayerRequestsAndUpdateGameState.class.getName());

    @Override
    public void handle(Command command) {
        Map<String, PlayerDecision> playerDecisionMap = getSuccessfulPlayerDecisions(uow);
        GameState updatedGameState = GameLogic.doTurn(uow.getGameState(), playerDecisionMap);
        uow.setGameState(updatedGameState);
    }

    /**
     * Get player decisions from all players given a UnitOfWork
     * (containing a PlayerInfoMap) and return the successful requests.
     */
    private Map<String, PlayerDecision> getSuccessfulPlayerDecisions(UnitOfWorkAbstract uow) {
        Map<String, PlayerConnectInfo> playerInfoMap = uow.getPlayerConnectInfoMap();
        if (playerInfoMap == null || playerInfoMap.isEmpty()) {
            LOGGER.info("No players connected");
            return new HashMap<>();
        }

        AtomicInteger errors = new AtomicInteger();
        AtomicInteger numPlayers = new AtomicInteger();

        ConcurrentMap<Class<? extends Exception>, Integer> exceptions = new ConcurrentHashMap<>();

        // https://stackoverflow.com/questions/4759570/finding-number-of-cores-in-java
        int cores = Runtime.getRuntime().availableProcessors();
        // https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Callable<Map.Entry<String, PlayerDecision>>> tasks = new ArrayList<>();
        for (Map.Entry<String, PlayerConnectInfo> playerInfo : playerInfoMap.entrySet()) {
            tasks.add(() -> {
                URL url;
                PlayerDecision decision = null;
                String playerName = playerInfo.getKey();
                HttpURLConnection http = null;
                try {
                    // https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
                    url = new URL(playerInfo.getValue().getIpAddr());
                    URLConnection con = url.openConnection();
                    http = (HttpURLConnection) con;
                } catch (IOException e) {
                    LOGGER.warning(String.format("MalformedURLException: could not connect to player \"%s\" at url %s: %s",
                            playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                }

                assert http != null;
                try {
                    http.setRequestMethod("POST");
                    http.setDoOutput(true);
                    http.setConnectTimeout(Integer.parseInt(Config.getProperty("millisBetweenTurns")) / 4);
                    http.setReadTimeout(Integer.parseInt(Config.getProperty("millisBetweenTurns")) / 4);

                    PlayerProtos.PlayerTurn turn = GameLogic.constructPlayerTurn(new GameState(), playerName);
                    turn.writeTo(http.getOutputStream());

                    decision = PlayerDecision.parseFrom(http.getInputStream());
                } catch (InvalidProtocolBufferException e) {
                    LOGGER.warning(String.format("InvalidProtocolBufferException: could not connect to player \"%s\" at url %s: %s",
                            playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                    errors.getAndIncrement();
                } catch (ProtocolException e) {
                    LOGGER.warning(String.format("ProtocolException: could not connect to player \"%s\" at url %s: %s",
                            playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                    errors.getAndIncrement();
                } catch (IOException e) {
                    synchronized (exceptions) {
                        int numExceptions = exceptions.getOrDefault(e.getClass(), 0);
                        exceptions.put(e.getClass(), numExceptions + 1);
                        if (numExceptions > 6) {
                        } else if (numExceptions > 5) {
                            LOGGER.info("Not printing any more exceptions of type " + e.getClass().getName());
                        } else {
                            LOGGER.info(String.format("%s from player '%s' @ %s: %s", e.getClass().getName(), playerName, playerInfo.getValue().getIpAddr(), e.getMessage()));
                        }
                    }
                    errors.getAndIncrement();
                } finally {
                    http.disconnect();
                }
                numPlayers.getAndIncrement();

                return new AbstractMap.SimpleEntry<>(playerName, decision);
            });
        }

        List<Future<Map.Entry<String, PlayerDecision>>> results;
        Map<String, PlayerDecision> map = new HashMap<>();
        try {
            results = pool.invokeAll(tasks);

            for (Future<Map.Entry<String, PlayerDecision>> future : results) {
                Map.Entry<String, PlayerDecision> result = future.get();
                if (result.getValue() != null) {
                    map.put(result.getKey(), result.getValue());
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            LOGGER.warning(String.format("Exception encountered while getting PlayerDecisions (returning no valid decisions): %s", e.getMessage()));
        }

        LOGGER.info(String.format("Successfully sent PlayerTurn to %d players with %d errors: %s.", numPlayers.get() - errors.get(), errors.get(), exceptions));
        return map;
    }
}
