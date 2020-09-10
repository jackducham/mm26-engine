package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public class StopVisualizerServer extends CommandHandler {
    public StopVisualizerServer(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        uow.stopVisualizerServer();
    }
}
