package mech.mania.engine;

import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.GameState;

import mech.mania.engine.game.characters.CharacterProtos;
import mech.mania.engine.game.characters.Position;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;
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

        // Add player1
        gameState.addNewPlayer("player1");
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
     * Test initial player addition and spawn location
     */
    @Test
    public void gameInit(){
        assertTrue(gameState.getPlayer("player1") != null);

        // Check that player1 is at 0, 0 on their board
        Position initPos = new Position(0, 0, "player1");
        assertTrue(gameState.getPlayer("player1").getPosition().equals(initPos));
    }

    /**
     * Tests MOVE decision (moves player from 0, 0 to 1, 0)
     */
    @Test
    public void movePlayer(){
        // Move player1 to 1, 0
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(1).setY(0).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        Position expectedPos = new Position(1, 0, "player1");
        assertTrue(finalPos.equals(expectedPos));
    }



    /**
     * Helper function that creates a custom PlayerDecision from custom commands
     *
     * @param commands String[] of commands to use
     * @return PlayerDecision object
     */
    private PlayerProtos.PlayerDecision createDecisionsFromCommands(String[] commands) {
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();

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

    @Test
    public void manhattanDistanceTests() {
        Position x0y0 = new Position(0, 0, "id");
        Position x10y10 = new Position(10, 10, "id");
        Position x5y10 = new Position(5, 10, "id");
        Position x5y11 = new Position(5, 11, "id");
        assertEquals(20, GameLogic.calculateManhattanDistance(x0y0, x10y10));
        assertEquals(15, GameLogic.calculateManhattanDistance(x0y0, x5y10));
        assertEquals(16, GameLogic.calculateManhattanDistance(x0y0, x5y11));
        assertEquals(0, GameLogic.calculateManhattanDistance(x10y10, x10y10));
        assertEquals(5, GameLogic.calculateManhattanDistance(x5y10, x10y10));
        assertEquals(1, GameLogic.calculateManhattanDistance(x5y10, x5y11));
    }

}
