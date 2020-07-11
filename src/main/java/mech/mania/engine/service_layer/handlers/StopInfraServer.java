package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public class StopInfraServer extends EventHandler {
    public StopInfraServer(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {
        uow.stopInfraServer();
    }
}
