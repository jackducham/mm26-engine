package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.RepositoryAbstract;
import mech.mania.engine.adapters.RepositoryFake;
import mech.mania.engine.adapters.RepositoryLocalFile;
import org.springframework.boot.SpringApplication;

public class UnitOfWorkFake extends UnitOfWorkAbstract {
    /**
     * Constructor that sets an AbstractRepository
     */
    public UnitOfWorkFake() {
        super(new RepositoryLocalFile());
    }

    @Override
    public void stopInfraServer() {
        LOGGER.info("Stopping infra server");
        infraCtx.close();
    }

    @Override
    public void stopVisualizerServer() {
        LOGGER.info("Stopping visualizer server");
        visualizerCtx.close();
    }

    @Override
    public void stopAPIServer() {
        LOGGER.info("Stopping API server");
    }
}
