package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

public class EndGame extends EventHandler {
    public EndGame(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {
        // Set game over flag. Main will actually end game next turn
        uow.setGameOver(true);
    }
}
