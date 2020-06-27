package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public class SendPlayerRequests extends CommandHandler {
    public SendPlayerRequests(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {

    }
}
