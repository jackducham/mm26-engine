package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.RepositoryAbstract;
import mech.mania.engine.adapters.RepositoryFake;
import org.springframework.boot.SpringApplication;

public class UnitOfWorkFake extends UnitOfWorkAbstract {
    /**
     * Constructor that sets an AbstractRepository
     */
    public UnitOfWorkFake() {
        super(new RepositoryFake());
    }

    @Override
    public void stopInfraServer() {
        LOGGER.info("Stopping infra server");
//        infraCtx.close();
        SpringApplication.exit(infraCtx, () -> 0);
    }


    @Override
    public void stopVisualizerServer() {
        LOGGER.info("Stopping visualizer server");
//        visualizerCtx.close();
        SpringApplication.exit(visualizerCtx, () -> 0);
    }

    @Override
    public void stopAPIServer() {
        LOGGER.info("Stopping API server");
        SpringApplication.exit(APICtx, () -> 0);
    }
}
