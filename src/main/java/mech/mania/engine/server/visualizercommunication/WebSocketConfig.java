package mech.mania.MM26GameEngine.server.visualizercommunication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/visualizer")
                .addInterceptors(new HttpSessionHandshakeInterceptor()); //TODO: set correct endpoint
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new VisualizerBinaryWebSocketHandler();
    }

}
