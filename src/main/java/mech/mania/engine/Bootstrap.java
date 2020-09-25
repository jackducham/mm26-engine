package mech.mania.engine;

import mech.mania.engine.adapters.RepositoryAws;
import mech.mania.engine.domain.messages.*;
import mech.mania.engine.service_layer.MessageBus;
import mech.mania.engine.service_layer.UnitOfWork;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.handlers.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bootstrap {
    public static MessageBus bootstrap() {
        return bootstrap(new UnitOfWork(new RepositoryAws()));
    }

    public static MessageBus bootstrap(UnitOfWorkAbstract uow) {
        // start ORM mappers

        // inject dependencies
        // events can happen asynchronously
        Map<Class<? extends Event>, List<EventHandler>> eventHandlers = new HashMap<>();
        eventHandlers.put(EventReceivePlayerDecision.class,       Arrays.asList(new StorePlayerDecision(uow)));
        eventHandlers.put(EventNewPlayer.class,                   Arrays.asList(new UpdatePlayer(uow)));
        eventHandlers.put(EventEndGame.class,                     Arrays.asList(new EndGame(uow)));
        eventHandlers.put(EventStoreHistoryObjects.class,          Arrays.asList(new StoreHistoryObjects(uow)));

        // commands must happen synchronously
        Map<Class<? extends Command>, CommandHandler> commandHandlers = new HashMap<>();
        commandHandlers.put(CommandRestoreTurn.class,                           new RestoreTurn(uow));
        commandHandlers.put(CommandStartTurn.class,                             new StartTurn(uow));
        commandHandlers.put(CommandStartInfraServer.class,                      new StartInfraServer(uow));
        commandHandlers.put(CommandStopInfraServer.class,                       new StopInfraServer(uow));
        commandHandlers.put(CommandStartVisualizerServer.class,                 new StartVisualizerServer(uow));
        commandHandlers.put(CommandStopVisualizerServer.class,                  new StopVisualizerServer(uow));
        commandHandlers.put(CommandStartAPIServer.class,                        new StartAPIServer(uow));
        commandHandlers.put(CommandStopAPIServer.class,                         new StopAPIServer(uow));
        commandHandlers.put(CommandSendPlayerRequestsAndUpdateGameState.class,  new SendPlayerRequestsAndUpdateGameState(uow));
        commandHandlers.put(CommandUpdateGameState.class,                       new UpdateGameState(uow));
        commandHandlers.put(CommandSendVisualizerChange.class,                  new SendVisualizerChange(uow));

        return new MessageBus(uow, eventHandlers, commandHandlers);
    }
}
