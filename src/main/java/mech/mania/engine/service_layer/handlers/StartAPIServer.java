package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.messages.CommandStartAPIServer;
import mech.mania.engine.service_layer.InfraRESTHandler;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;

public class StartAPIServer extends CommandHandler {
    public StartAPIServer(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        String port = ((CommandStartAPIServer) command).getPort();

        SpringApplication app = new SpringApplication(InfraRESTHandler.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", port));
        ConfigurableApplicationContext ctx = app.run();

        // store application context to be able to stop server later
        uow.storeAPICtx(ctx);
    }
}
