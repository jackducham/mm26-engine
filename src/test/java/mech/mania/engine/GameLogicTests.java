package mech.mania.engine;

import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.GameState;

import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.characters.CharacterProtos;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.characters.Position;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/** This contains tests for any overall board tests or helper functions */
public class GameLogicTests {
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

    @Test
    public void illegalPlayerMovePosition() {
        Character playerOnBoard = gameState.getPlayer("player1");
        Position testPosition = new Position(-1, -1, "id");
        assertEquals(0, GameLogic.moveCharacter(gameState, playerOnBoard, testPosition).size());
    }

    @Test
    public void illegalPlayerMoveSpeed() {
        // TODO ensure that the board size is sufficiently large for this test
        Character playerOnBoard = gameState.getPlayer("player1");
        Position testPosition = new Position(30, 30, "id");
        assertEquals(0, GameLogic.moveCharacter(gameState, playerOnBoard, testPosition).size());
    }

    @Test
    public void legalPlayerMove() {
        //TODO ensure that the board size is sufficiently large for this tesgt
        //TODO ensure that the player has sufficient base speed for this test
        Character playerOnBoard = gameState.getPlayer("player1");
        Position testPosition = new Position(1, 1, "id");
        assertEquals(2, GameLogic.moveCharacter(gameState, playerOnBoard, testPosition).size());
    }

    @Test
    public void illegalPlayerPortalPosition() {
        //TODO ensure a portal exists at (1, 1)
        Character playerOnBoard = (mech.mania.engine.game.characters.Character)gameState.getPlayer("player1");
        assertEquals(playerOnBoard.getPosition(), new Position(0, 0, "id"));
        assertFalse(GameLogic.canUsePortal(gameState, playerOnBoard));
    }

    @Test
    public void illegalPlayerPortalIndex() {
        //TODO ensure a portal exists at (0, 0) and there is only 1 portal
        Character playerOnBoard = gameState.getPlayer("player1");
        assertTrue(GameLogic.canUsePortal(gameState, playerOnBoard));
        assertFalse(GameLogic.usePortal(gameState, playerOnBoard, -2));
        assertFalse(GameLogic.usePortal(gameState, playerOnBoard, 1));
    }

    @Test
    public void legalPlayerHomePortal() {
        //TODO ensure a portal exists at (0, 0) and there is only 1 portal
        //TODO ensure there is another board to portal to on the PVP board
        Character playerOnBoard = gameState.getPlayer("player1");
        assertTrue(GameLogic.canUsePortal(gameState, playerOnBoard));
        assertTrue(GameLogic.usePortal(gameState, playerOnBoard, -1));
    }

    @Test
    public void legalPlayerPVPPortal() {
        //TODO ensure a portal exists at (0, 0) and there is only 1 portal
        //TODO ensure there is another board to portal to on the PVP board
        Character playerOnBoard = gameState.getPlayer("player1");
        assertTrue(GameLogic.canUsePortal(gameState, playerOnBoard));
        assertTrue(GameLogic.usePortal(gameState, playerOnBoard, 0));
    }

    @Test
    public void nullInvalidTile() {
        // TODO ensure there is a board that matches this requirement
        assertNull(GameLogic.getTileAtPosition(gameState, new Position(3, 3, "id")));
    }

    @Test
    public void validTileAtPosition() {
        // TODO ensure there is a board that matches this requirement
        assertEquals(
                    Tile.TileType.BLANK,
                    Objects.requireNonNull(
                                            GameLogic.getTileAtPosition(
                                                                        gameState,
                                                                        new Position(0, 0, "id")
                                            )).getType()
                    );
        assertEquals(
                Tile.TileType.IMPASSIBLE,
                Objects.requireNonNull(
                        GameLogic.getTileAtPosition(
                                gameState,
                                new Position(1, 0, "id")
                        )).getType()
        );
        assertEquals(
                Tile.TileType.PORTAL,
                Objects.requireNonNull(
                        GameLogic.getTileAtPosition(
                                gameState,
                                new Position(0, 1, "id")
                        )).getType()
        );
    }

    @Test
    public void voidTileAtPosition() {
        // TODO ensure there is a board that matches this requirement
        assertNull(GameLogic.getTileAtPosition(gameState, new Position(1, 1, "id")));
    }

    @Test
    public void invalidateOutOfBoundsPosition() {
        // TODO ensure there is a board that matches these requirements
        assertFalse(GameLogic.validatePosition(gameState, new Position(-1, 0, "id")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(0, -1, "id")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(5, 0, "id")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(0, 5, "id")));
    }
}
