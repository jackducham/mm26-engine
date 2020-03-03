package mech.mania.engine.server.communication.visualizer;

import mech.mania.engine.server.communication.player.PlayerRequestSender;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.io.IOException;
import java.util.logging.Logger;

public class VisualizerBinaryWebSocketHandler extends BinaryWebSocketHandler {

    private static final Logger LOGGER = Logger.getLogger( VisualizerBinaryWebSocketHandler.class.getName() );

    private static WebSocketSession session;

    /**
     * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
     * @param change the VisualizerTurn to send
     */
    public static void sendChange(VisualizerChange change) {
        BinaryMessage message = new BinaryMessage(change.toByteArray());
        if (session == null) {
            LOGGER.info("No endpoint to send to");
            return;
        }

        try {
            session.sendMessage(message);
        } catch (IOException e) {
            LOGGER.warning("An IOException occurred when sending turn to endpoint. Error message:\n" +
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
        LOGGER.info("Closing endpoint with Visualizer");
        if (session == null) {
            return;
        }

        try {
            session.close(new CloseStatus(1001, "Game ended."));
            session = null;
        } catch (IOException e) {
            LOGGER.warning("An IOException occurred when closing endpoint. Error message:\n" +
                            e.getMessage());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession newSession) {
        session = newSession;
        LOGGER.info("New WebSocket connection established");
        // TODO: Send initial game state on new connection
    }
}
