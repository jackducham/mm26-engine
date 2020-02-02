package mech.mania.engine.server.communication.player;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos.PlayerTurn;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerBinaryWebSocketHandler extends BinaryWebSocketHandler {

    /** List of endpoints to send messages to, mapped by player identification */
    private static Map<String, WebSocketSession> endpoints = new HashMap<>();

    /** Map between turn number and the list of playerDecisions that have been received */
    private static Map<Integer, List<PlayerDecision>> playerDecisions = new HashMap<>();

    /** Current turn number */
    private static int currentTurnNum = 0;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        GameLogger.log(GameLogger.LogLevel.DEBUG,
                "PLAYERWEBSOCKET",
                "New Websocket connection established.");

        // TODO: Send initial game state on new connection
        PlayerTurn turn = PlayerTurn.newBuilder()
                .setTurn(currentTurnNum)
                .setIncrement(1)
                .build();

        try {
            session.sendMessage(new BinaryMessage(turn.toByteArray()));
        } catch (IOException e) {
            GameLogger.log(GameLogger.LogLevel.ERROR,
                    "PLAYERWEBSOCKET",
                    "An IOException occurred when sending turn to endpoint. Error message:\n" +
                            e.getMessage());
        }
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        try {
            PlayerDecision decision = PlayerDecision.parseFrom(message.getPayload());

            String playerName = decision.getPlayerName();

            int turn = decision.getTurn();

            if (!playerDecisions.containsKey(turn)) {
                playerDecisions.put(turn, new ArrayList<>());
            }

            // add to database of received decisions
            playerDecisions.get(turn).add(decision);

            // if valid decision and was able to add to playerDecisions, then make sure we send back future PlayerTurns
            if (!endpoints.containsKey(playerName)) {
                endpoints.put(playerName, session);
            }

            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "PLAYERWEBSOCKET",
                    "Received Decision from player " + playerName + " for turn " + turn);

        } catch (InvalidProtocolBufferException e) {
            GameLogger.log(GameLogger.LogLevel.ERROR,
                    "PLAYERWEBSOCKET",
                    "Exception in receiving decision. Not accepting message. Error message:\n" +
                            e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // TODO: cleanup after connection closed
        GameLogger.log(GameLogger.LogLevel.ERROR,
                "PLAYERWEBSOCKET",
                "WebSocketSession closed for reason: " + status.getReason());
    }

    /**
     * Sends PlayerTurn protobuf Binary to all endpoints in {@code endpoints} list.
     * @param controller GameStateController to use to compute playerTurns
     */
    public static void sendTurnAllPlayers(GameStateController controller) {

        // keep the variable updated as much as possible
        currentTurnNum = controller.getCurrentTurn();

        GameLogger.log(GameLogger.LogLevel.DEBUG,
                "PLAYERWEBSOCKET",
                "Sending PlayerTurn to " + endpoints.size() + " endpoints");

        endpoints.forEach((player, endpoint) -> {
            if (endpoint.isOpen()) {
                try {
                    // get specific message for each player
                    PlayerTurn turn = controller.constructPlayerTurn(player, currentTurnNum + 1);

                    // send the message to that player
                    endpoint.sendMessage(new BinaryMessage(turn.toByteArray()));

                } catch (IOException e) {
                    GameLogger.log(GameLogger.LogLevel.ERROR,
                            "PLAYERWEBSOCKET",
                            "An IOException occurred when sending turn to player " + player +
                                    ". Error message:\n" +
                                    e.getMessage());
                }
            }
        });
    }

    /**
     * Reads message from each endpoint to get each PlayerDecision from each Player
     *
     * @param turn turn to get for
     * @return List of PlayerDecisions
     */
    public static List<PlayerDecision> getTurnAllPlayers(int turn) {
        if (!playerDecisions.containsKey(turn) || playerDecisions.get(turn).isEmpty()) {
            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "PLAYERWEBSOCKET",
                    "No PlayerDecisions found");
            return new ArrayList<>();
        }

        GameLogger.log(GameLogger.LogLevel.DEBUG,
                "PLAYERWEBSOCKET",
                playerDecisions.get(turn).size() + " PlayerDecisions found");
        return playerDecisions.get(turn);
    }

    /**
     * Closes all endpoints.
     */
    public static void destroy() {
        GameLogger.log(GameLogger.LogLevel.INFO,
                "PLAYERWEBSOCKET",
                "Closing all endpoints");
        endpoints.forEach((player, endpoint) -> {
            try {
                endpoint.close(new CloseStatus(1001, "Game ended."));
            } catch (IOException e) {
                GameLogger.log(GameLogger.LogLevel.ERROR,
                        "PLAYERWEBSOCKET",
                        "An IOException occurred when closing endpoint. Error message:\n" +
                                e.getMessage());
            }
        });

        // reset state
        endpoints.clear();
        playerDecisions.clear();
    }
}
