package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Objects;

import mech.mania.engine.domain.model.PlayerProtos;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/** This contains tests for any overall board tests or helper functions */
public class AttackTests {
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
    public void gameInit() {

    }
}