package mech.mania.engine;

import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.GameState;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

public class GameStateTests {

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
    private PlayerDecisionProtos.PlayerDecision createDecisionsFromCommands(String[] commands) {
        PlayerDecisionProtos.PlayerDecision.Builder decision = PlayerDecisionProtos.PlayerDecision.newBuilder();

        String randomName = UUID.randomUUID().toString();
        decision.setPlayerName(randomName);  // give player a random name

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