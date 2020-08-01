package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public class StorePlayerDecision extends EventHandler {
    public StorePlayerDecision(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {

    }
}
