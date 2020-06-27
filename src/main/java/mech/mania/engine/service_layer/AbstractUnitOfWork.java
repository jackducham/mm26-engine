package mech.mania.engine.service_layer;

import mech.mania.engine.domain.messages.Message;

import java.util.List;

public interface AbstractUnitOfWork {
    void addNewMessage(Message message);
    List<Message> collectNewMessages();
}
