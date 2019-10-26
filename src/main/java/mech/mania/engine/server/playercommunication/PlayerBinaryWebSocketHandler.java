package mech.mania.engine.server.playercommunication;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerBinaryWebSocketHandler extends BinaryWebSocketHandler {
    private static List<PlayerBinaryWebSocketHandler> endpoints = new ArrayList<>();

    private WebSocketSession session;

    @Override
    public void afterConnectionEstablished(WebSocketSession session){
        this.session = session;
        endpoints.add(this);
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
     * @param turn the PlayerTurn to send
     */
    public static void sendTurn(PlayerTurnProtos.PlayerTurn turn) {
        BinaryMessage message = new BinaryMessage(turn.toByteArray());
        endpoints.forEach(endpoint -> {
            try {
                endpoint.session.sendMessage(message);
            } catch (IOException e){
                System.err.println("An IOException occurred when sending turn to endpoint. Error message:\n" +
                        e.getMessage());
            }
        });
    }
}
