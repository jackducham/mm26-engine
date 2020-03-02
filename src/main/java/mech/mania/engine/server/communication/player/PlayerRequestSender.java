package mech.mania.engine.server.communication.player;

import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.model.PlayerInfo;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PlayerRequestSender {
    public static final int STATUS_OK = 200;

    private static final Logger LOGGER = Logger.getLogger( PlayerRequestSender.class.getName() );

    /**
     * Sends POST request to each player that we have saved currently.
     * @return if every single request was successful, returns true, else false.
     */
    public static List<PlayerDecision> sendPlayerRequestsAndUpdateGameState() {
        Map<String, PlayerInfo> playerInfoMap = GameStateController.getPlayerInfoMap();
        if (playerInfoMap == null || playerInfoMap.isEmpty()) {
            return new ArrayList<>();
        }

        return playerInfoMap.entrySet().parallelStream().map(playerInfo -> {
            URL url = null;
            PlayerDecision decision = null;

            try {
                // https://stackoverflow.com/questions/3324717/sending-http-post-request-in-java
                url = new URL(playerInfo.getValue().getIpAddr());
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST");
                http.setDoOutput(true);

                String playerName = playerInfo.getKey();
                PlayerTurn turn = GameStateController.constructPlayerTurn(playerName);

                OutputStream os = http.getOutputStream();
                os.write(turn.toByteArray());
                os.flush();
                os.close();

                int responseCode = http.getResponseCode();
                if (responseCode == STATUS_OK) {
                    InputStream isr = http.getInputStream();

                    // https://www.baeldung.com/convert-input-stream-to-array-of-bytes
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = isr.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }

                    isr.close();

                    decision = PlayerDecision.parseFrom(buffer.toByteArray());

                } else {
                    LOGGER.warning("Non-OK Status Code from player \"" + playerInfo.getKey() + "\" @ " + url);
                }

            } catch (MalformedURLException e) {
                LOGGER.warning(String.format("MalformedURLException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
            } catch (ProtocolException e) {
                LOGGER.warning(String.format("ProtocolException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
            } catch (IOException e) {
                LOGGER.warning(String.format("IOException: could not connect to player \"%s\" at url %s: %s",
                        playerInfo.getKey(), playerInfo.getValue().getIpAddr(), e.getMessage()));
            }

            return decision;

        }).collect(Collectors.toList());
    }
}

