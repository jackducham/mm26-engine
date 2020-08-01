package mech.mania.engine.domain.game;

import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.UnitOfWorkFake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class GameStateTests {

    private GameState gameState;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = new GameState();
    }


    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {
    }


    @Test
    public void testHello() {
        UnitOfWorkAbstract uow = new UnitOfWorkFake();
    }
}
