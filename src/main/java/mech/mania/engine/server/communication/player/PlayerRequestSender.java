package mech.mania.engine.server.communication.player;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.model.PlayerInfo;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PlayerRequestSender {
    public static final int STATUS_OK = 200;

    private static final Logger LOGGER = Logger.getLogger( PlayerRequestSender.class.getName() );

    /**
     * Sends POST request to each player that we have saved currently.
     * @return if every single request was successful, returns true, else false.
     */
    public static Map<String, PlayerDecision> sendPlayerRequestsAndUpdateGameState() {
        Map<String, PlayerInfo> playerInfoMap = GameStateController.getPlayerInfoMap();
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

                PlayerTurn turn = GameStateController.constructPlayerTurn();
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

        LOGGER.info(String.format("Sent PlayerTurn to %d players with %d errors.", numPlayers.get(), errors.get()));
        return map;
    }
}

