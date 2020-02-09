package mech.mania.engine.server.communication.visualizer;

import mech.mania.engine.logging.GameLogger;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;

public class VisualizerBinaryWebSocketHandler extends BinaryWebSocketHandler {

    private static WebSocketSession session;

    /**
     * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
     * @param turn the VisualizerTurn to send
     */
    public static void sendTurn(VisualizerTurn turn) {
        BinaryMessage message = new BinaryMessage(turn.toByteArray());
        if (session == null) {
            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "VISUALIZERWEBSOCKET",
                    "No endpoint to send to");
            return;
        }

        try {
            session.sendMessage(message);
        } catch (IOException e) {
            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "VISUALIZERWEBSOCKET",
                    "An IOException occurred when sending turn to endpoint. Error message:\n" +
                            e.getMessage());
        }
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
        // TODO: Handle binary message - Visualizer shouldn't really send us any messages
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // TODO: cleanup after connection closed
    }

    public static void destroy() {
        GameLogger.log(GameLogger.LogLevel.INFO,
                "VISUALIZERWEBSOCKET",
                "Closing endpoint");
        if (session == null) {
            return;
        }

        try {
            session.close(new CloseStatus(1001, "Game ended."));
            session = null;
        } catch (IOException e) {
            GameLogger.log(GameLogger.LogLevel.ERROR,
                    "VISUALIZERWEBSOCKET",
                    "An IOException occurred when closing endpoint. Error message:\n" +
                            e.getMessage());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession newSession) {
        session = newSession;
        GameLogger.log(GameLogger.LogLevel.DEBUG,
                "VISUALIZERWEBSOCKET",
                "New WebSocket connection established");
        // TODO: Send initial game state on new connection
    }
}
