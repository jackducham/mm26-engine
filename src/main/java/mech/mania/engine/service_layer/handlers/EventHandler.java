package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public abstract class EventHandler {
    AbstractUnitOfWork uow;
    public EventHandler(AbstractUnitOfWork uow) {
        this.uow = uow;
    }

    public abstract void handle(Event event);
}
