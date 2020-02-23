package mech.mania.engine.server.communication.player;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PlayerRequestSender {
    public enum PLAYER_EXISTENCE {
        PLAYER_EXISTS,
        PLAYER_DOES_NOT_EXIST
    }

    private static final Logger LOGGER = Logger.getLogger( PlayerRequestSender.class.getName() );

    private static Map<String, String> playerIps;

    /**
     * Sends POST request to each player that we have saved currently. If any errors occur, they will
     * be returned as a List of Exceptions (so check if length of list is 0)
     * @return List of Exceptions in the case that exceptions occur.
     */
    public static List<Exception> sendPlayerRequests() {
        List<Exception> errors = new ArrayList<>();

        for (Map.Entry<String, String> playerIp : playerIps.entrySet()) {
            URL url = null;
            try {
                url = new URL(playerIp.getValue());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

            } catch (MalformedURLException e) {
                errors.add(new MalformedURLException(String.format("MalformedURLException: could not connect to player \"%s\" at url %s: %s", playerIp.getKey(), playerIp.getValue(), e.getMessage())));
            } catch (ProtocolException e) {
                errors.add(new ProtocolException(String.format("ProtocolException: could not connect to player \"%s\" at url %s: %s", playerIp.getKey(), playerIp.getValue(), e.getMessage())));
            } catch (IOException e) {
                errors.add(new IOException(String.format("IOException: could not connect to player \"%s\" at url %s: %s", playerIp.getKey(), playerIp.getValue(), e.getMessage())));
            }
        }

        return errors;
    }

    /**
     * Add a player IP by the player name and IP.
     * @param playerName name of player to add
     * @param playerIp IP of player to add
     * @return Whether or not the player existed in list of players previously.
     */
    public static PLAYER_EXISTENCE addPlayerIp(String playerName, String playerIp) {
        if (playerIps.containsKey(playerName)) {
            playerIps.put(playerName, playerIp);
            return PLAYER_EXISTENCE.PLAYER_EXISTS;
        }

        playerIps.put(playerName, playerIp);
        return PLAYER_EXISTENCE.PLAYER_DOES_NOT_EXIST;
    }
}

