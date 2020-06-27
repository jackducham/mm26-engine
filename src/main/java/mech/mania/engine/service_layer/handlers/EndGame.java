package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public class EndGame extends EventHandler {
    public EndGame(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {

    }
}
