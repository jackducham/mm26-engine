package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.game.GameLogic;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.GameChange;
import mech.mania.engine.domain.model.VisualizerProtos;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.VisualizerWebSocket;

public class SendVisualizerChange extends CommandHandler {
    public SendVisualizerChange(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {

        GameChange gameChange = GameLogic.constructGameChange(uow.getGameState());

        VisualizerProtos.VisualizerTurn change = VisualizerProtos.VisualizerTurn.newBuilder()
                .setState(uow.getGameState().buildProtoClass())
                .setChange(gameChange.buildProtoClass())
                .build();

        // sending VisualizerTurn via websocket
        uow.getVisualizerCtx().getBean(VisualizerWebSocket.VisualizerBinaryWebSocketHandler.class)
                .sendChange(change);

        uow.setGameChange(gameChange.buildProtoClass());
    }
}
