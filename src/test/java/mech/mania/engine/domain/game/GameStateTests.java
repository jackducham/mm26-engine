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
//    private GameStateController controller;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = new GameState();
//        controller = new GameStateController();
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


    /**
     * Helper function that creates a custom PlayerDecision from custom commands
     *
     * @param commands String[] of commands to use
     * @return PlayerDecision object
     */
    private PlayerDecision createDecisionsFromCommands(String[] commands) {
        PlayerDecision.Builder decision = PlayerDecision.newBuilder();

        // TODO: create more commands for convenience
        for (String command : commands) {
            switch (command) {
                case "move":
                    // decision.setMovement()
                    break;
            }
        }

        return decision.build();
    }
}
