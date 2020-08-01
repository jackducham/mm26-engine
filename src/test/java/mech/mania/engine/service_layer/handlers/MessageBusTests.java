package mech.mania.engine.service_layer.handlers;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.domain.messages.*;
import mech.mania.engine.service_layer.MessageBus;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.UnitOfWorkFake;
import org.junit.Test;

import java.util.Queue;

public class MessageBusTests {

    UnitOfWorkAbstract uow = new UnitOfWorkFake();
    MessageBus bus = Bootstrap.bootstrap(uow);


    @Test
    public void testStartGameSpawnsCorrectMessages() {
        bus.handle(new CommandStartTurn(1));

        Queue<Message> messages = uow.collectNewMessages();
        System.out.println(messages);

    }


    @Test
    public void testEndGameDoesntSpawnNewMessages() {
        bus.handle(new EventEndGame());

        Queue<Message> messages = uow.collectNewMessages();
        System.out.println(messages);

    }

    @Test
    public void testMessageBusQueueClearedAtGameEnd() {

    }
}
