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

/** This contains tests for decisions related to movements */
public class MovementTests {

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

}
