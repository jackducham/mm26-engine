package mech.mania.engine.service_layer.handlers;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

import mech.mania.engine.domain.messages.*;
import mech.mania.engine.service_layer.AbstractUnitOfWork;

public class StartGameTurn extends CommandHandler {

    private static final Logger LOGGER = Logger.getLogger( StartGameTurn.class.getName() );

    public StartGameTurn(AbstractUnitOfWork uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {

        if (uow.getGameOver()) {
            System.out.println("game is over");
            return;
        }

        // get arguments from command object
        int turn = ((CommandStartGameTurn) command).getTurn();
        int millisBetweenTurns = ((CommandStartGameTurn) command).getMillisBetweenTurns();

        LOGGER.info(String.format("Turn %s", turn));

        uow.setTurn(turn);

        Instant turnStartTime = Instant.now();

        // these three abstractions aren't completely necessary, but allows for
        // asynchronous operations if necessary
        uow.addNewMessage(new CommandStoreGameState(turn, uow.getGameState()));
        uow.addNewMessage(new CommandSendPlayerRequestsAndUpdateGameState());
        uow.addNewMessage(new CommandSendVisualizerChange());

        // have the next turn start after waiting millisBetweenTurns
        // after this turn began (make sure time between turns is
        // actually as advertised)
        Instant nextTurnStart = turnStartTime.plusMillis(millisBetweenTurns);
        uow.addNewMessage(new CommandWaitUntilTime(nextTurnStart));
        uow.addNewMessage(new CommandStartGameTurn(turn + 1));
    }
}
