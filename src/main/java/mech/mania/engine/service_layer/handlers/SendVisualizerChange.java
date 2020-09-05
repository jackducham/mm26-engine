package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.game.GameLogic;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.GameChange;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.VisualizerWebSocket;

public class SendVisualizerChange extends CommandHandler {
    public SendVisualizerChange(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {

        GameChange gameChange = GameLogic.constructGameChange(uow.getGameState());

        // sending VisualizerChange via websocket
        VisualizerWebSocket.VisualizerBinaryWebSocketHandler.sendChange(gameChange.buildProtoClass());

        uow.setGameChange(gameChange.buildProtoClass());
    }
}
