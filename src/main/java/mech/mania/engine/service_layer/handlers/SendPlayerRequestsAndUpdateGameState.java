package mech.mania.engine.service_layer.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.Config;
import mech.mania.engine.domain.game.GameLogic;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.PlayerConnectInfo;
import mech.mania.engine.domain.model.PlayerProtos;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.io.IOException;
import java.net.*;
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
        Map<String, CharacterProtos.CharacterDecision> playerDecisionMap = getSuccessfulPlayerDecisions(uow);
        GameState updatedGameState = GameLogic.doTurn(uow.getGameState(), playerDecisionMap);
        uow.setGameState(updatedGameState);
        shutdownExpiredPlayerServers(uow);
    }

    /**
     * Shuts down all servers in the expiredConnectInfo list of the UOW
     * @param uow the Unit of work
     */
    private void shutdownExpiredPlayerServers(UnitOfWorkAbstract uow) {
        // For each expired server, shut it down and remove it from the list
        for(Iterator<PlayerConnectInfo> itr = uow.getExpiredConnectInfoList().iterator(); itr.hasNext();){
            PlayerConnectInfo playerConnectInfo = itr.next();

            URL url;
            HttpURLConnection http = null;
            try {
                // https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
                url = buildShutdownUrl(playerConnectInfo);
                URLConnection con = url.openConnection();
                http = (HttpURLConnection) con;
            } catch (IOException e) {
                LOGGER.warning(String.format("MalformedURLException: could not shutdown player at url %s: %s",
                        playerConnectInfo.getIpAddr(), e.getMessage()));
            }

            assert http != null;
            try {
                http.setRequestMethod("GET");
                http.setConnectTimeout(1000);
                http.setReadTimeout(1000);
                http.setInstanceFollowRedirects(false);

                // Send the signal
                http.connect();

                // TODO: verify result

                // Mark this player server and removed
                itr.remove();
            } // TODO: Catch a connectionRefused exception and assume that means the instance was already shut down
            catch(ConnectException e) {
                LOGGER.warning(String.format("ConnectException: Instance already shutdown? Error: %s", e));
                itr.remove();
            }
            catch(IOException e) {
                LOGGER.warning(String.format("IOException: could not shutdown player at url %s: %s",
                        playerConnectInfo.getIpAddr(), e));
                itr.remove();
            }
        }
    }


    /**
     * Get player decisions from all players given a UnitOfWork
     * (containing a PlayerInfoMap) and return the successful requests.
     * Calls
     */
    private Map<String, CharacterProtos.CharacterDecision> getSuccessfulPlayerDecisions(UnitOfWorkAbstract uow) {
        Map<String, PlayerConnectInfo> playerInfoMap = uow.getPlayerConnectInfoMap();
        if (playerInfoMap == null || playerInfoMap.isEmpty()) {
            LOGGER.info("No players connected");
            return new HashMap<>();
        }

        AtomicInteger errors = new AtomicInteger();
        AtomicInteger numTotalDecisions = new AtomicInteger();

        ConcurrentMap<Class<? extends Exception>, Integer> exceptions = new ConcurrentHashMap<>();

        // https://stackoverflow.com/questions/4759570/finding-number-of-cores-in-java
        int cores = Runtime.getRuntime().availableProcessors();
        // https://stackoverflow.com/questions/21670451/how-to-send-multiple-asynchronous-requests-to-different-web-services
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        int numPlayers = playerInfoMap.size();

        List<Callable<Map.Entry<String, CharacterProtos.CharacterDecision>>> tasks = new ArrayList<>();
        for (Map.Entry<String, PlayerConnectInfo> playerInfo : playerInfoMap.entrySet()) {
            if (uow.getGameState().getPlayer(playerInfo.getKey()) == null) {
                // Don't contact them for a decision because they aren't in the game. Add a fake NONE decision to alert
                //  GameLogic to add them to the game
                tasks.add(() -> new AbstractMap.SimpleEntry<>(
                        playerInfo.getKey(),
                        CharacterProtos.CharacterDecision.newBuilder()
                                .setDecisionType(CharacterProtos.DecisionType.NONE)
                                .setIndex(-1)
                                .build()
                ));

                continue;
            }

            tasks.add(() -> {
                URL url;
                CharacterProtos.CharacterDecision decision = null;
                String playerName = playerInfo.getKey();
                HttpURLConnection http = null;
                try {
                    // https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
                    url = buildDecisionUrl(playerInfo.getValue());
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
                    http.setInstanceFollowRedirects(false);
                    // conservative estimate on how many players each core will handle in serial
                    http.setConnectTimeout(Integer.parseInt(Config.getProperty("millisBetweenTurns")) / 4);
                    http.setReadTimeout(Integer.parseInt(Config.getProperty("millisBetweenTurns")) / 4);

                    PlayerProtos.PlayerTurn turn = GameLogic.constructPlayerTurn(uow.getGameState(), playerName);
                    turn.writeTo(http.getOutputStream());

                    decision = CharacterProtos.CharacterDecision.parseFrom(http.getInputStream());
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
                numTotalDecisions.getAndIncrement();

                return new AbstractMap.SimpleEntry<>(playerName, decision);
            });
        }

        List<Future<Map.Entry<String, CharacterProtos.CharacterDecision>>> results;
        Map<String, CharacterProtos.CharacterDecision> map = new HashMap<>();
        try {
            results = pool.invokeAll(tasks);

            for (Future<Map.Entry<String, CharacterProtos.CharacterDecision>> future : results) {
                Map.Entry<String, CharacterProtos.CharacterDecision> result = future.get();
                if (result.getValue() != null) {
                    map.put(result.getKey(), result.getValue());
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            LOGGER.warning(String.format("Exception encountered while getting PlayerDecisions (returning no valid decisions): %s", e.getMessage()));
        }

        LOGGER.info(String.format("Successfully sent PlayerTurn to %d players with %d errors: %s.", numTotalDecisions.get() - errors.get(), errors.get(), exceptions));
        return map;
    }

    /**
     * Helper function to get URL for player servers' decisions
     */
    private URL buildDecisionUrl(PlayerConnectInfo playerConnectInfo) throws MalformedURLException {
        return new URL(
                Config.getProperty("playerServerProtocol") +
                        playerConnectInfo.getIpAddr() +
                        Config.getProperty("playerServerDecisionEndpoint")
        );
    }

    /**
     * Helper function to get URL to shutdown player servers
     */
    private URL buildShutdownUrl(PlayerConnectInfo playerConnectInfo) throws MalformedURLException {
        return new URL(
                Config.getProperty("playerServerProtocol") +
                        playerConnectInfo.getIpAddr() +
                        Config.getProperty("playerServerShutdownEndpoint")
        );
    }

    /**
     * Helper function to get URL to get player servers' health
     */
    private URL buildHealthUrl(PlayerConnectInfo playerConnectInfo) throws MalformedURLException {
        return new URL(
                Config.getProperty("playerServerProtocol") +
                        playerConnectInfo.getIpAddr() +
                        Config.getProperty("playerServerHealthEndpoint")
        );
    }
}
