package mech.mania.engine.service_layer;

import mech.mania.engine.domain.model.VisualizerProtos;
import mech.mania.engine.entrypoints.Main;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
import java.util.concurrent.CopyOnWriteArrayList;
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
    public static class VisualizerBinaryWebSocketHandler extends BinaryWebSocketHandler implements ApplicationContextAware {
        private static final Logger LOGGER = Logger.getLogger( VisualizerBinaryWebSocketHandler.class.getName() );

        private static ApplicationContext context;

        private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

        @Override
        @Autowired
        public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
            context = applicationContext;
        }

        @Override
        public void afterConnectionEstablished(@NotNull WebSocketSession newSession) {
            sessions.add(newSession); // Add to list of connections
            LOGGER.info("New WebSocket connection established (id = " + newSession.getId() + ")");

            // Send initial game state on new connection
            MessageBus bus = context.getBean(Main.class).bus();
            BinaryMessage message = new BinaryMessage(bus.getUow().getGameState().buildProtoClass().toByteArray());

            try {
                newSession.sendMessage(message);
            } catch (IOException e) {
                LOGGER.warning("An IOException occurred when sending game state to visualizer (id = " +
                        newSession.getId() + "). Error message:\n" + e);
            }
        }

        /**
         * Sends VisualizerTurn protobuf binary to all endpoints in {@code endpoints} list.
         * @param change the VisualizerTurn to send
         */
        public void sendChange(VisualizerProtos.GameChange change) {
            BinaryMessage message = new BinaryMessage(change.toByteArray());

            if (sessions.isEmpty()) {
                LOGGER.info("No open visualizer connections to send to");
                return;
            }

            // Send to all open connections
            int successfulSends = 0;
            for(WebSocketSession session : sessions) {
                try {
                    session.sendMessage(message);
                    successfulSends++;
                } catch (IOException e) {
                    LOGGER.warning("An IOException occurred when sending turn to visualizer (id = " +
                            session.getId() + "). Error message:\n" + e.getMessage());
                }
            }

            LOGGER.info("Sent GameChange to " + successfulSends + " visualizer instances");
        }

        @Override
        public void handleBinaryMessage(@NotNull WebSocketSession session, @NotNull BinaryMessage message) {
            // TODO: Handle binary message - Visualizer shouldn't really send us any messages
        }

        @Override
        public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) {
            sessions.remove(session);
        }
    }
}
