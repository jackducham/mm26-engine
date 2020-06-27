package mech.mania.engine.domain.game;

import mech.mania.engine.service_layer.GameStateController;
import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import org.junit.After;
import org.junit.Before;

import static junit.framework.TestCase.assertTrue;

public class GameStateTest {

    private GameState gameState;
    private GameStateController controller;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = new GameState();
        controller = new GameStateController();
    }

    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {
    }

//    /**
//     * Test to check if gameOver can be achieved
//     */
//    @Test
//    public void gameOverCondition() {
//        PlayerDecisionProtos.PlayerDecision decision = createDecisionsFromCommands(new String[]{
//                "move north 5",
//                "move east 3"
//        });
//
//        gameState = GameLogic.doTurn(gameState, List.of(decision));
//        assertTrue(controller.isGameOver(gameState));
//    }


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
