package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.util.logging.Logger;

public abstract class CommandHandler {
    UnitOfWorkAbstract uow;
    protected Logger LOGGER = Logger.getLogger(getClass().toString());

    public CommandHandler(UnitOfWorkAbstract uow) {
        this.uow = uow;
    }

    public abstract void handle(Command command);
}
