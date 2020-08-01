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
        gameState = GameState.createDefaultGameState();
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
        Character playerOnBoard = gameState.getPlayer("player1");
        Position testPosition = new Position(31, 31, "pvp");
        assertEquals(0, GameLogic.moveCharacter(gameState, playerOnBoard, testPosition).size());
    }

    @Test
    public void legalPlayerMove() {
        Character playerOnBoard = gameState.getPlayer("player1");
        Position testPosition = new Position(1, 3, "pvp");
        assertEquals(2, GameLogic.moveCharacter(gameState, playerOnBoard, testPosition).size());
    }

    @Test
    public void illegalPlayerPortalPosition() {
        Character playerOnBoard = gameState.getPlayer("player1");
        assertEquals(playerOnBoard.getPosition(), new Position(0, 0, "pvp"));
        assertFalse(GameLogic.canUsePortal(gameState, playerOnBoard));
    }

    @Test
    public void illegalPlayerPortalIndex() {
        Character playerOnBoard = gameState.getPlayer("player1");
        playerOnBoard.setPosition(new Position(10, 14, "pvp"));
        assertTrue(GameLogic.canUsePortal(gameState, playerOnBoard));
        assertFalse(GameLogic.usePortal(gameState, playerOnBoard, -2));
        assertFalse(GameLogic.usePortal(gameState, playerOnBoard, 2));
    }

    @Test
    public void legalPlayerHomePortal() {
        Character playerOnBoard = gameState.getPlayer("player1");
        playerOnBoard.setPosition(new Position(10, 14, "pvp"));
        assertTrue(GameLogic.canUsePortal(gameState, playerOnBoard));
        assertTrue(GameLogic.usePortal(gameState, playerOnBoard, -1));
    }

    @Test
    public void legalPlayerPVPPortal() {
        Character playerOnBoard = gameState.getPlayer("player1");
        playerOnBoard.setPosition(new Position(5, 10, "player1"));
        assertTrue(GameLogic.canUsePortal(gameState, playerOnBoard));
        assertTrue(GameLogic.usePortal(gameState, playerOnBoard, 0));
    }

    @Test
    public void nullInvalidTile() {
        assertNull(GameLogic.getTileAtPosition(gameState, new Position(31, 31, "pvp")));
    }

    @Test
    public void validTileAtPosition() {
        assertEquals(
                    Tile.TileType.BLANK,
                    Objects.requireNonNull(
                                            GameLogic.getTileAtPosition(
                                                                        gameState,
                                                                        new Position(0, 0, "pvp")
                                            )).getType()
                    );
        assertEquals(
                Tile.TileType.IMPASSIBLE,
                Objects.requireNonNull(
                        GameLogic.getTileAtPosition(
                                gameState,
                                new Position(1, 20, "pvp")
                        )).getType()
        );
        assertEquals(
                Tile.TileType.PORTAL,
                Objects.requireNonNull(
                        GameLogic.getTileAtPosition(
                                gameState,
                                new Position(0, 1, "pvp")
                        )).getType()
        );
    }

    @Test
    public void voidTileAtPosition() {
        // TODO ensure there is a board that matches this requirement
        assertNull(GameLogic.getTileAtPosition(gameState, new Position(1, 1, "pvp")));
    }

    @Test
    public void invalidateOutOfBoundsPosition() {
        assertFalse(GameLogic.validatePosition(gameState, new Position(-1, 0, "pvp")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(0, -1, "pvp")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(31, 0, "pvp")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(0, 31, "pvp")));
    }
}
