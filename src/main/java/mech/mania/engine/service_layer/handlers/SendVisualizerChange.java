package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.domain.model.VisualizerProtos;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.Services;
import mech.mania.engine.service_layer.VisualizerWebSocket;

public class SendVisualizerChange extends CommandHandler {
    public SendVisualizerChange(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Command command) {

        VisualizerProtos.GameChange visualizerChange = Services.constructVisualizerChange(uow.getGameState());

        // sending VisualizerChange via websocket
        VisualizerWebSocket.VisualizerBinaryWebSocketHandler.sendChange(visualizerChange);

        // storing VisualizerChange to database (AWS) for history fetching
        uow.getRepository().storeVisualizerChange(uow.getTurn(), visualizerChange);
    }
}
