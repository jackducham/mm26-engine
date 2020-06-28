package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.AbstractRepository;
import mech.mania.engine.domain.messages.Message;

import java.util.logging.Logger;

public class UnitOfWork extends AbstractUnitOfWork {

    private static final Logger LOGGER = Logger.getLogger( UnitOfWork.class.getName() );

    public UnitOfWork(final AbstractRepository repository) {
        super(repository);
    }

    @Override
    public void addNewMessage(Message message) {
        LOGGER.fine("addNewMessage");
        super.addNewMessage(message);
    }
}
