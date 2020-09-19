package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/** This contains tests for any overall board tests or helper functions */
public class GameLogicTests {
    private GameState gameState;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = GameState.createDefaultGameState();
    }

    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {
    }

    /**
     * Test initial player addition and spawn location
     */
    @Test
    public void gameInit(){
        assertNotNull(gameState.getPlayer("player1"));

        // Check that player1 is at 0, 0 on their board
        Position initPos = new Position(0, 4, "pvp");
        assertTrue(gameState.getPlayer("player1").getPosition().equals(initPos));
    }

    @Test
    public void manhattanDistanceTests() {
        Position x0y0 = new Position(0, 0, "id");
        Position x10y10 = new Position(10, 10, "id");
        Position x5y10 = new Position(5, 10, "id");
        Position x5y11 = new Position(5, 11, "id");
        assertEquals(20, x0y0.manhattanDistance(x10y10));
        assertEquals(15, x0y0.manhattanDistance(x5y10));
        assertEquals(16, x0y0.manhattanDistance(x5y11));
        assertEquals(0, x10y10.manhattanDistance(x10y10));
        assertEquals(5, x5y10.manhattanDistance(x10y10));
        assertEquals(1, x5y10.manhattanDistance(x5y11));
    }

    @Test
    public void illegalPlayerPortalPosition() {
        Character playerOnBoard = gameState.getPlayer("player1");
        assertTrue(playerOnBoard.getPosition().equals(new Position(0, 4, "pvp")));
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
                                new Position(10, 14, "pvp")
                        )).getType()
        );
    }

    @Test
    public void voidTileAtPosition() {
        assertNull(GameLogic.getTileAtPosition(gameState, new Position(29, 29, "pvp")));
    }

    @Test
    public void invalidateOutOfBoundsPosition() {
        assertFalse(GameLogic.validatePosition(gameState, new Position(-1, 0, "pvp")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(0, -1, "pvp")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(31, 0, "pvp")));
        assertFalse(GameLogic.validatePosition(gameState, new Position(0, 31, "pvp")));
    }
}
