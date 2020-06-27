package mech.mania.engine.service_layer;

import mech.mania.engine.domain.messages.Message;

import java.util.ArrayList;
import java.util.List;

public class UnitOfWork implements AbstractUnitOfWork {

    Queue<Message> messages = new LinkedList<>();

    @Override
    public void addNewMessage(Message message) {
        messages.add(message);
    }

    @Override
    public List<Message> collectNewMessages() {
        return messages;
    }
}
