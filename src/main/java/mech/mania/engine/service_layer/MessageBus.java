package mech.mania.engine.service_layer;

import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.messages.Command;
import mech.mania.engine.service_layer.handlers.CommandHandler;
import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.service_layer.handlers.EventHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

public class MessageBus {

    private final AbstractUnitOfWork uow;
    private final Map<Class<? extends Event>, List<EventHandler>> eventHandlers;
    private final Map<Class<? extends Command>, CommandHandler> commandHandlers;
    private final Queue<Message> messageQueue = new LinkedList<>();
    private final Logger logger = Logger.getLogger( getClass().getName() );

    public MessageBus(AbstractUnitOfWork uow,
                      Map<Class<? extends Event>, List<EventHandler>> eventHandlers,
                      Map<Class<? extends Command>, CommandHandler> commandHandlers) {
        this.uow = uow;
        this.eventHandlers = eventHandlers;
        this.commandHandlers = commandHandlers;
    }

    public void handle(Message newMessage) throws IllegalArgumentException {
        messageQueue.add(newMessage);
        while (!messageQueue.isEmpty()) {
            Message message = messageQueue.poll();
            System.out.println(String.format("handling %s", message.getClass().toString()));
            if (message instanceof Event) {
                handleEvent((Event) message);
            } else if (message instanceof Command) {
                handleCommand((Command) message);
            } else {
                throw new IllegalArgumentException(message + " is not an event or a command");
            }
        }
    }

    private void handleEvent(Event event) {
        for (EventHandler handler : eventHandlers.get(event.getClass())) {
            try {
                handler.handle(event);
                messageQueue.addAll(uow.collectNewMessages());
            } catch (Exception e) {
                logger.warning(String.format("Exception while handling event %s", event));
                throw e;
            }
        }
    }

    private void handleCommand(Command command) {
        try {
            CommandHandler handler = commandHandlers.get(command.getClass());
            handler.handle(command);
            messageQueue.addAll(uow.collectNewMessages());
        } catch (Exception e) {
            logger.warning(String.format("Exception while handling command %s", command));
            throw e;
        }
    }
}
