package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.Config;
import mech.mania.engine.domain.messages.CommandStartGameTurn;
import mech.mania.engine.domain.messages.CommandStartInfraServer;
import mech.mania.engine.domain.messages.CommandStartVisualizerServer;
import mech.mania.engine.service_layer.MessageBus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    // default bootstrap argument is DatabaseAws (see Bootstrap.java)
    public static MessageBus bus = Bootstrap.bootstrap();

    @Bean
    public MessageBus bus() {
        return bus;
    }

    public static void main(String[] args) {
        // setup servers
        bus.handle(new CommandStartInfraServer(Config.getInfraPort()));
        bus.handle(new CommandStartVisualizerServer(Config.getVisualizerPort()));

        // start the first turn
        bus.handle(new CommandStartGameTurn(1));
    }
}
