package mech.mania.engine;

import mech.mania.engine.domain.messages.*;
import mech.mania.engine.service_layer.handlers.*;
import mech.mania.engine.service_layer.AbstractUnitOfWork;
import mech.mania.engine.service_layer.MessageBus;
import mech.mania.engine.service_layer.handlers.CommandHandler;
import mech.mania.engine.service_layer.handlers.EventHandler;

import java.util.List;
import java.util.Map;

public class Bootstrap {
    public static MessageBus bootstrap() {
        return bootstrap();
    }

    public static MessageBus bootstrap(AbstractUnitOfWork uow) {
        // start ORM mappers

        // inject dependencies
        // events can happen asynchronously
        Map<Class<? extends Event>, List<EventHandler>> eventHandlers = Map.of(
                EventReceivePlayerDecision.class,   List.of(new StorePlayerDecision(uow)),
                EventNewPlayer.class,               List.of(new UpdatePlayer(uow)),
                EventEndGame.class,                 List.of(new EndGame(uow))
        );
        // commands must happen synchronously
        Map<Class<? extends Command>, CommandHandler> commandHandlers = Map.of(
                CommandStartGameLoop.class,         new StartGameLoop(uow),
                CommandStartInfraServer.class,      new StartInfraServer(uow),
                CommandStartVisualizerServer.class, new StartVisualizerServer(uow),
                CommandStoreGameState.class,        new StoreGameState(uow),
                CommandSendPlayerRequests.class,    new SendPlayerRequests(uow),
                CommandUpdateGameState.class,       new UpdateGameState(uow),
                CommandSendVisualizerChange.class,  new SendVisualizerChange(uow)
        );

        return new MessageBus(uow, eventHandlers, commandHandlers);
    }
}
