package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public class StopVisualizerServer extends EventHandler {
    public StopVisualizerServer(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {
        uow.stopVisualizerServer();
    }
}
