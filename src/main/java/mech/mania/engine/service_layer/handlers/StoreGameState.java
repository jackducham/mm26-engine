package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public class StoreGameState extends CommandHandler {
    public StoreGameState(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {

    }
}
