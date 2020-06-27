package mech.mania.engine.domain.communication.player;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.service_layer.GameStateController;
import mech.mania.engine.domain.model.PlayerInfo;
import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.domain.model.PlayerProtos.PlayerTurn;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PlayerRequestSender {
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

        Map<String, Integer> errors = new HashMap<>();
        AtomicInteger numPlayers = new AtomicInteger();

        ConcurrentMap<String, Optional<PlayerDecision>> map = playerInfoMap.entrySet().parallelStream().map(playerInfo -> {
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

                PlayerTurn turn = GameStateController.constructPlayerTurn(playerName);
                turn.writeTo(http.getOutputStream());

                decision = PlayerDecision.parseFrom(http.getInputStream());
            } catch (InvalidProtocolBufferException e) {
                LOGGER.warning(String.format("InvalidProtocolBufferException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                errors.put(playerName, errors.getOrDefault(playerName, 0) + 1);
            } catch (ProtocolException e) {
                LOGGER.warning(String.format("ProtocolException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                errors.put(playerName, errors.getOrDefault(playerName, 0) + 1);
            } catch (IOException e) {
                LOGGER.warning(String.format("IOException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
                errors.put(playerName, errors.getOrDefault(playerName, 0) + 1);
            } finally {
                http.disconnect();
            }
            numPlayers.getAndIncrement();
            return new AbstractMap.SimpleEntry<>(playerName, Optional.ofNullable(decision));
        }).collect(Collectors.toConcurrentMap(
                AbstractMap.SimpleEntry::getKey,
                AbstractMap.SimpleEntry::getValue));

        // filter out any null values
        Map<String, PlayerDecision> filtered = map.entrySet()
                .stream()
                .filter(stringOptionalPlayerDecision -> stringOptionalPlayerDecision.getValue().isPresent())
                .collect(Collectors.toMap(
                        k -> (String) k.getKey(),
                        v -> (PlayerDecision) v.getValue().get()));

        int sumErrorCount = errors.values().stream().reduce(Integer::sum).orElse(0);
        LOGGER.info(String.format("Successfully sent PlayerTurn to %d players with %d errors.",
                filtered.size(), sumErrorCount));
        return filtered;
    }
}
