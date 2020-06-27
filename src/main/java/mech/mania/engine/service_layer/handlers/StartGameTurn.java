package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public class StartGameTurn extends CommandHandler {
    public StartGameTurn(AbstractUnitOfWork uow) {
        super(uow);
    }

    int turn = 0;
    GameState gameState = new GameState();

    @Override
    public void handle(Command command) {
        turn = ((CommandStartTurn) command).getTurn();
        // TODO: figure out how to loop turns
        // need to exit this function before the messages are executed

        // print turn
        uow.addNewMessage(new CommandStoreGameState(gameState));
        uow.addNewMessage(new CommandSendPlayerRequests());
        uow.addNewMessage(new CommandSendVisualizerChange());

        turn++;
        uow.addNewMessage(new CommandStartTurn(turn));
    }
}
