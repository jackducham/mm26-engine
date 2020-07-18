package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.Services;

import java.util.Map;

public class SendPlayerRequestsAndUpdateGameState extends CommandHandler {
    public SendPlayerRequestsAndUpdateGameState(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {
        Map<String, PlayerDecision> playerDecisionMap =
            Services.getSuccessfulPlayerDecisions(uow);
        GameState updatedGameState =
            Services.updateGameState(uow.getGameState(), playerDecisionMap);
        uow.setGameState(updatedGameState);
    }
}
