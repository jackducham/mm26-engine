package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.util.logging.Logger;

public abstract class EventHandler {
    UnitOfWorkAbstract uow;
    protected Logger LOGGER = Logger.getLogger(getClass().toString());

    public EventHandler(UnitOfWorkAbstract uow) {
        this.uow = uow;
    }

    public abstract void handle(Event event);
}
