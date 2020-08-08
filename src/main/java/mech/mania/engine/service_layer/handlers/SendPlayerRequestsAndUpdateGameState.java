package mech.mania.engine.service_layer.handlers;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.Config;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.PlayerConnectInfo;
import mech.mania.engine.domain.model.PlayerProtos;
import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.service_layer.Services;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SendPlayerRequestsAndUpdateGameState extends CommandHandler {
    public SendPlayerRequestsAndUpdateGameState(UnitOfWorkAbstract uow) {
        super(uow);
    }
    private static final Logger LOGGER = Logger.getLogger(SendPlayerRequestsAndUpdateGameState.class.getName());

    @Override
    public void handle(Command command) {
        Map<String, PlayerDecision> playerDecisionMap = getSuccessfulPlayerDecisions(uow);
        GameState updatedGameState =
            Services.updateGameState(uow.getGameState(), playerDecisionMap);
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
        ConcurrentMap<String, PlayerDecision> map = playerInfoMap.entrySet().parallelStream().map(playerInfo -> {

            URL url;
            PlayerDecision decision = null;
            HttpURLConnection http = null;
            String playerName = playerInfo.getKey();
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
                http.setConnectTimeout(Config.getMillisBetweenTurns());

                PlayerProtos.PlayerTurn turn =
                        Services.constructPlayerTurn(uow.getGameState(), playerName);
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
                LOGGER.warning(String.format("IOException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                errors.getAndIncrement();
            } finally {
                http.disconnect();
            }
            numPlayers.getAndIncrement();
            return new AbstractMap.SimpleEntry<>(playerName, decision);
        }).collect(Collectors.toConcurrentMap(entry -> (String) entry.getKey(), entry -> (PlayerDecision) entry.getValue()));

        LOGGER.info(String.format("Successfully sent PlayerTurn to %d players with %d errors.", numPlayers.get() - errors.get(), errors.get()));
        return map;
    }
}
