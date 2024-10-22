package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Shoes;
import mech.mania.engine.domain.game.items.StatusModifier;
import mech.mania.engine.domain.model.CharacterProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/** This contains tests for decisions related to movements */
public class MovementTests {

    private GameState gameState;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = new GameState();

        // Add player1
        gameState.addNewPlayer("player1");

        // Make home board simple
        Board newHome = new Board(20, 20);
        newHome.addPortal(new Position(5, 10, "player1"));
        gameState.getAllBoards().put("player1", newHome);

        gameState.getPlayer("player1").setPosition(new Position(0, 0, "player1"));
        gameState.getPlayer("player1").setShoes(
                new Shoes(new StatusModifier(5, 0, 0, 0,
                        0, 0, 0, 0, 0,
                        0, 0), "")
        );
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
    public void movePlayerToAdjacentEastPosition(){
        final int final_x = 1;
        final int final_y = 0;

        // Move player1 to 1, 0
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(final_x, final_y, "player1");
        assertEquals(expectedPos, finalPos);
    }

    /**
     * Tests MOVE decision (moves player from 1, 0 to 0, 0)
     */
    @Test
    public void movePlayerToAdjacentWestPosition(){
        // Set player position to (1, 0)
        gameState.getPlayer("player1").getPosition().setX(1);

        final int final_x = 0;
        final int final_y = 0;

        // Move player1 to 1, 0
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(final_x, final_y, "player1");
        assertEquals(expectedPos, finalPos);
    }

    /**
     * Tests MOVE decision (moves player from 0, 0 to 0, 1)
     */
    @Test
    public void movePlayerToAdjacentSouthPosition(){
        final int final_x = 0;
        final int final_y = 1;

        // Move player1 to 1, 0
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(final_x, final_y, "player1");
        assertEquals(expectedPos, finalPos);
    }

    /**
     * Tests MOVE decision (moves player from 0, 1 to 0, 0)
     */
    @Test
    public void movePlayerToAdjacentNorthPosition(){
        // Set player position to (0, 1)
        gameState.getPlayer("player1").getPosition().setY(1);

        final int final_x = 0;
        final int final_y = 0;

        // Move player1 to 1, 0
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(final_x, final_y, "player1");
        assertEquals(expectedPos, finalPos);
    }

    /**
     * Tests MOVE decision (moves player from 0, 0 to 0, 2)
     */
    @Test
    public void movePlayerStraightSouth(){
        final int final_x = 0;
        final int final_y = 2;

        // Move player1 to 0, 2
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(final_x, final_y, "player1");
        assertEquals(expectedPos, finalPos);
    }

    /**
     * Tests MOVE decision (moves player from 0, 0 to 2, 0)
     */
    @Test
    public void movePlayerStraightEast(){
        final int final_x = 2;
        final int final_y = 0;

        // Move player1 to 1, 0
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(final_x, final_y, "player1");
        assertEquals(expectedPos, finalPos);
    }

    /**
     * Tests MOVE decision (attempts to move player from 0, 0 to 15, 0 but fails due to speed check)
     */
    @Test
    public void movePlayerMoreThanSpeed(){
        final int final_x = 15;
        final int final_y = 0;

        // Attempt to move player1 to 15, 0
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        newPos.setX(final_x).setY(final_y).setBoardId("player1");
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // Check that player has been moved
        Position finalPos = gameState.getPlayer("player1").getPosition();
        System.out.println("Final Position: (" + finalPos.getX() + ", " + finalPos.getY() + ", " + finalPos.getBoardID() + ")");
        Position expectedPos = new Position(0, 0, "player1");
        assertEquals(expectedPos, finalPos);
    }
}
