package mech.mania.engine.server.communication.visualizer;

import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisualizerBinaryWebSocketHandler extends BinaryWebSocketHandler {
    // will there ever be more than one endpoint to visualizer?
    // private static List<VisualizerBinaryWebSocketHandler> endpoints = new ArrayList<>();
    private static VisualizerBinaryWebSocketHandler endpoint;
    private static GameStateController controller = new GameStateController();

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        // endpoints.add(this);
        endpoint = this;
        //TODO: Send initial game state on new connection
    }

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
        //TODO: Handle binary message - Visualizer shouldn't really send us any messages
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        //TODO: cleanup after connection closed
    }

    /**
     * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
     * @param turn the VisualizerTurn to send
     */
    public static void sendTurn(VisualizerTurn turn) {
        BinaryMessage message = new BinaryMessage(turn.toByteArray());
        // endpoint = getMostRecentVisualizerEndpoint();
        // endpoints.forEach(endpoint -> {
            try {
                endpoint.session.sendMessage(message);
            } catch (IOException e){
                System.err.println("An IOException occurred when sending turn to endpoint. Error message:\n" +
                        e.getMessage());
            }
        // });
    }
}
