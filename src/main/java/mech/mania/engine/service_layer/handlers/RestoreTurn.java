package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.messages.CommandRestoreTurn;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public class RestoreTurn extends CommandHandler {
    public RestoreTurn(UnitOfWorkAbstract uow){
        super(uow);
    }

    @Override
    public void handle(Command command) {
        uow.restoreTurn(((CommandRestoreTurn)command).getTurn());
    }
}
