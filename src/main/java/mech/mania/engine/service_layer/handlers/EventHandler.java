package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public abstract class EventHandler {
    UnitOfWorkAbstract uow;
    public EventHandler(UnitOfWorkAbstract uow) {
        this.uow = uow;
    }

    public abstract void handle(Event event);
}
