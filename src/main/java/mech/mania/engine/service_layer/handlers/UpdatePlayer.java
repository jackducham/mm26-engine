package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public class UpdatePlayer extends EventHandler {
    public UpdatePlayer(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {

    }
}
