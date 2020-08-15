package mech.mania.engine.service_layer;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.Config;
import mech.mania.engine.domain.game.GameLogic;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.PlayerConnectInfo;
import mech.mania.engine.domain.model.PlayerProtos.*;
import mech.mania.engine.domain.model.VisualizerProtos.*;

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

/**
 * A class that has utility functions that don't directly need access
 * to the domain model.
 */
public class Services {

    private static final Logger LOGGER = Logger.getLogger( Services.class.getName() );

    /**
     * Constructs a PlayerTurn for a specific player using a
     * GameState and a specific player's name
     * @return PlayerTurn a playerTurn specific for a player
     */
    public static PlayerTurn constructPlayerTurn(GameState gameState, String playerName) {
        return PlayerTurn.newBuilder()
                .setGameState(gameState.buildProtoClass())
                .setPlayerName(playerName)
                .build();
    }

    /**
     * Get player decisions from all players given a UnitOfWork
     * (containing a PlayerInfoMap) and return the successful requests.
     */
    public static Map<String, PlayerDecision> getSuccessfulPlayerDecisions(UnitOfWorkAbstract uow) {
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
                url = new URL("http://" + playerInfo.getValue().getIpAddr());
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

                PlayerTurn turn =
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
        }).filter(entry -> entry.getKey() != null && entry.getValue() != null)
                .collect(Collectors.toConcurrentMap(entry -> (String) entry.getKey(), entry -> (PlayerDecision) entry.getValue()));

        LOGGER.info(String.format("Successfully sent PlayerTurn to %d players with %d errors.", numPlayers.get() - errors.get(), errors.get()));
        return map;
    }

    /**
     * Returns an updated GameState object after updating it using
     * PlayerDecision objects from playerDecisionMap
     */
    public static GameState updateGameState(GameState currentGameState, Map<String, PlayerDecision> playerDecisionMap) {
        return GameLogic.doTurn(currentGameState, playerDecisionMap);
    }

    /**
     * Returns a VisualizerChange object that denotes how the gameState
     * has changed in relevant terms to the Visualizer team
     */
    public static VisualizerChange constructVisualizerChange(GameState gameState) {
        return gameState.stateChange.buildProtoClass();
    }
}
