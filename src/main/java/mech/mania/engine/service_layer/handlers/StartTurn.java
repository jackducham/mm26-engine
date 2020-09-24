package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.*;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.util.logging.Logger;

public class StartTurn extends CommandHandler {

    private static final Logger LOGGER = Logger.getLogger( StartTurn.class.getName() );

    public StartTurn(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        if (uow.getGameOver()) {
            System.out.println("Game is over");

            // Game was ended last turn so don't add more commands.
            return;
        }
        // get arguments from command object
        int turn = ((CommandStartTurn) command).getTurn();
        LOGGER.info(String.format("Turn %s", turn));

        uow.setTurn(turn);
        uow.getGameState().setTurnNumber(turn);

        // these three abstractions aren't completely necessary, but allows for
        // asynchronous operations if necessary
        uow.addNewMessage(new CommandSendPlayerRequestsAndUpdateGameState());
        uow.addNewMessage(new CommandSendVisualizerChange());
        uow.addNewMessage(new EventStoreHistoryObjects());
    }
}
