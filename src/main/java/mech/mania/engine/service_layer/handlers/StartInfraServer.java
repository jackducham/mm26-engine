package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.CommandStartInfraServer;
import mech.mania.engine.service_layer.InfraRESTHandler;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

public class StartInfraServer extends CommandHandler {
    public StartInfraServer(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        String port = ((CommandStartInfraServer) command).getPort();

        SpringApplication app = new SpringApplication(InfraRESTHandler.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        ConfigurableApplicationContext ctx = app.run();

        // store application context to be able to stop server later
        uow.storeInfraCtx(ctx);
    }
}
