package mech.mania.engine.server.communication.player.config;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.communication.player.PlayerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Base64;


@Configuration
@EnableWebSocket
public class PlayerWebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myPlayerWebSocketHandler(), "/player")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myPlayerWebSocketHandler() {
        return new PlayerBinaryWebSocketHandler();
    }
}