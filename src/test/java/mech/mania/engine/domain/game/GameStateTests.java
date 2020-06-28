package mech.mania.engine.domain.game;

import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import org.junit.After;
import org.junit.Before;

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
