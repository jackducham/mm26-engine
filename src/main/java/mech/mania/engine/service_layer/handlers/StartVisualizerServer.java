package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.messages.CommandStartVisualizerServer;
import mech.mania.engine.service_layer.AbstractUnitOfWork;
import mech.mania.engine.service_layer.VisualizerWebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

public class StartVisualizerServer extends CommandHandler {
    public StartVisualizerServer(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        String port = ((CommandStartVisualizerServer) command).getPort();

        SpringApplication app = new SpringApplication(VisualizerWebSocket.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        ConfigurableApplicationContext ctx = app.run();

        // store application context to be able to stop server later
        uow.storeVisualizerCtx(ctx);
    }
}
