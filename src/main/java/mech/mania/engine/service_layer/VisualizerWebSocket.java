package mech.mania.engine.service_layer;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.GameStateProtos;
import mech.mania.engine.domain.model.VisualizerProtos;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

@SpringBootApplication
@Configuration
@EnableWebSocket
@ComponentScan("mech.mania.engine.entrypoints")
public class VisualizerWebSocket {
    @Component
    public static class VisualizerWebSocketConfig implements WebSocketConfigurer {
        @Override
        public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(new VisualizerBinaryWebSocketHandler(), "/visualizer")
                    .addInterceptors(new HttpSessionHandshakeInterceptor());
        }
    }

    @Component
    public static class VisualizerBinaryWebSocketHandler extends BinaryWebSocketHandler {
        private static final Logger LOGGER = Logger.getLogger( VisualizerBinaryWebSocketHandler.class.getName() );

        private static GameStateProtos.GameState lastGameState;
        private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

        /**
         * Set the GameState (proto) that will be sent to all new connections
         * @param gameState: The GameState to be converted to a protobuf and stored
         */
        public void setLastGameState(GameState gameState){
            lastGameState = gameState.buildProtoClass();
        }

        @Override
        public void afterConnectionEstablished(@NotNull WebSocketSession newSession) {
            sessions.add(newSession); // Add to list of connections

            // Send initial proto on new connection
            VisualizerProtos.VisualizerInitial initMessage = VisualizerProtos.VisualizerInitial.newBuilder()
                    .setState(lastGameState)
                    .build();

            BinaryMessage message = new BinaryMessage(initMessage.toByteArray());
            try {
                newSession.sendMessage(message);
            } catch (IOException e) {
                LOGGER.warning("An IOException occurred when sending VisualizerInitial to visualizer (id = " +
                        newSession.getId() + "). Error message:\n" + e);
            }
        }

        /**
         * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
         * @param change the VisualizerTurn to send
         */
        public void sendChange(VisualizerProtos.VisualizerTurn change) {
            BinaryMessage message = new BinaryMessage(change.toByteArray());

            if (sessions.isEmpty()) {
                LOGGER.info("No open visualizer connections to send to");
                return;
            }

            // Send to all open connections
            // (attempting to do this multi-threaded causes protocol errors with the WebSocket connections)
            int successfulSends = 0;
            for(WebSocketSession session : sessions){
                try {
                    session.sendMessage(message);
                    successfulSends++;
                } catch(IOException e){
                    // pass
                }
            }

            LOGGER.info("Sent VisualizerTurn to " + successfulSends + " visualizer instances");
        }

        @Override
        public void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) {
            // Log any messages received as suspicious (possibly we should disconnect these sessions)
            LOGGER.warning("Received message from visualizer instance (id = " + session.getId() + ")");
        }

        @Override
        public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
            // Not logging this because sending the endgame signal engine causes this to be called
            // on all open visualizer connections
            sessions.remove(session);
        }
    }
}
