package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.RepositoryAbstract;
import mech.mania.engine.domain.messages.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.logging.Logger;

public class UnitOfWork extends UnitOfWorkAbstract {

    private static final Logger LOGGER = Logger.getLogger( UnitOfWork.class.getName() );

    public UnitOfWork(final RepositoryAbstract repository) {
        super(repository);
    }

    @Override
    public void addNewMessage(Message message) {
        LOGGER.fine("addNewMessage");
        super.addNewMessage(message);
    }

    @Override
    public void stopInfraServer() {
        //infraCtx.close();
        SpringApplication.exit(infraCtx, () -> 0);
    }

    @Override
    public void stopVisualizerServer() {
        LOGGER.warning("STOPPING VIS HANDLER");
        //visualizerCtx.close();
        SpringApplication.exit(visualizerCtx, () -> 0);
    }

    @Override
    public void stopAPIServer() {
        SpringApplication.exit(APICtx, () -> 0);
    }
}
