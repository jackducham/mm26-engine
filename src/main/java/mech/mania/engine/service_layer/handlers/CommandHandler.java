package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public abstract class CommandHandler {
    AbstractUnitOfWork uow;
    public CommandHandler(AbstractUnitOfWork uow) {
        this.uow = uow;
    }

    public abstract void handle(Command command);
}
