package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.domain.messages.CommandStartInfraServer;
import mech.mania.engine.domain.messages.CommandStartVisualizerServer;
import mech.mania.engine.domain.messages.CommandStartGameLoop;
import mech.mania.engine.service_layer.MessageBus;

public class Main {

    public static void main(String[] args) {
        MessageBus bus = Bootstrap.bootstrap();

        String port = args[0];
        if (args.length == 0) {
            port = "8080";
        }

        bus.handle(new CommandStartInfraServer());
        bus.handle(new CommandStartVisualizerServer());

        bus.handle(new CommandStartGameLoop());
    }
}
