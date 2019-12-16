package mech.mania.engine.server.communication.visualizer;

import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisualizerBinaryWebSocketHandler extends BinaryWebSocketHandler {

    private static VisualizerBinaryWebSocketHandler endpoint;
    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        endpoint = this;
        GameLogger.log(GameLogger.LogLevel.DEBUG,
                "VISUALIZERWEBSOCKET",
                "New WebSocket connection established");
        // TODO: Send initial game state on new connection
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        // TODO: Handle binary message - Visualizer shouldn't really send us any messages
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        // TODO: cleanup after connection closed
    }

    /**
     * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
     * @param turn the VisualizerTurn to send
     */
    public static void sendTurn(VisualizerTurn turn) {
        BinaryMessage message = new BinaryMessage(turn.toByteArray());
        if (endpoint == null) {
            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "VISUALIZERWEBSOCKET",
                    "No endpoint to send to");
            return;
        }

        try {
            endpoint.session.sendMessage(message);
        } catch (IOException e) {
            GameLogger.log(GameLogger.LogLevel.DEBUG,
                    "VISUALIZERWEBSOCKET",
                    "An IOException occurred when sending turn to endpoint. Error message:\n" +
                    e.getMessage());
        }
    }

    public static void destroy() {
        GameLogger.log(GameLogger.LogLevel.INFO,
                "VISUALIZERWEBSOCKET",
                "Closing endpoint");
        if (endpoint == null) { return; }

        try {
            endpoint.session.close();
        } catch (IOException e) {
            GameLogger.log(GameLogger.LogLevel.ERROR,
                    "VISUALIZERWEBSOCKET",
                    "An IOException occurred when closing endpoint. Error message:\n" +
                            e.getMessage());
        }
    }
}
