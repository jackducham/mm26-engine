package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.RepositoryAbstract;
import mech.mania.engine.adapters.RepositoryFake;

public class UnitOfWorkFake extends UnitOfWorkAbstract {
    /**
     * Constructor that sets an AbstractRepository
     */
    public UnitOfWorkFake() {
        super(new RepositoryFake());
    }


}
