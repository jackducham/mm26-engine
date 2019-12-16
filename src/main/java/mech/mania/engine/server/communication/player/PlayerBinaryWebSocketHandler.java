package mech.mania.engine.server.communication.player;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerBinaryWebSocketHandler extends BinaryWebSocketHandler {

    private static List<PlayerBinaryWebSocketHandler> endpoints = new ArrayList<>();
    private static List<List<PlayerDecisionProtos.PlayerDecision>> playerDecisions = new ArrayList<>();
    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        endpoints.add(this);
        GameLogger.log(GameLogger.LogLevel.DEBUG,
                "PLAYERWEBSOCKET",
                "New WebSocket connection established");
        // TODO: Send initial game state on new connection
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // TODO: Handle binary message - Visualizer shouldn't really send us any messages
        try {
            PlayerDecisionProtos.PlayerDecision decision = PlayerDecisionProtos.PlayerDecision.parseFrom(message.getPayload());
            int turn = decision.getTurn();
            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "PLAYERWEBSOCKET",
                    "Received Decision from Player for turn " + turn);
            if (playerDecisions.size() < turn) {
                playerDecisions.add(new ArrayList<>());
            }
            playerDecisions.get(turn).add(decision);
        } catch (InvalidProtocolBufferException e) {
            GameLogger.log(GameLogger.LogLevel.ERROR,
                    "PLAYERWEBSOCKET",
                    "Invalid Protocol received from endpoint. Error message:\n" +
                            e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // TODO: cleanup after connection closed
        GameLogger.log(GameLogger.LogLevel.INFO,
                "PLAYERWEBSOCKET",
                "WebSocketSession closed for reason: " + status.getReason());
    }

    /**
     * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
     * @param turn the PlayerTurn to send
     */
    public static void sendTurnAllPlayers(PlayerTurnProtos.PlayerTurn turn) {
        BinaryMessage message = new BinaryMessage(turn.toByteArray());
        endpoints.forEach(endpoint -> {
            try {
                endpoint.session.sendMessage(message);
            } catch (IOException e) {
                GameLogger.log(GameLogger.LogLevel.ERROR,
                        "PLAYERWEBSOCKET",
                        "An IOException occurred when sending turn to endpoint. Error message:\n" +
                        e.getMessage());
            }
        });
    }

    /**
     * Reads message from each endpoint to get each PlayerDecision from each Player
     * @return List of PlayerDecisions
     */
    public static List<PlayerDecisionProtos.PlayerDecision> getTurnAllPlayers(int turn) {
        if (playerDecisions.size() <= turn) {
            return new ArrayList<>();
        }
        return playerDecisions.get(turn);
    }

    public static void destroy() {
        GameLogger.log(GameLogger.LogLevel.INFO,
                "PLAYERWEBSOCKET",
                "Closing all endpoints");
        endpoints.forEach(endpoint -> {
            try {
                endpoint.session.close();
            } catch (IOException e) {
                GameLogger.log(GameLogger.LogLevel.ERROR,
                        "PLAYERWEBSOCKET",
                        "An IOException occurred when closing endpoint. Error message:\n" +
                                e.getMessage());
            }
        });
    }
}
