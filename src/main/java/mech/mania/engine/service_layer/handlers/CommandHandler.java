package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public abstract class CommandHandler {
    UnitOfWorkAbstract uow;
    public CommandHandler(UnitOfWorkAbstract uow) {
        this.uow = uow;
    }

    public abstract void handle(Command command);
}
