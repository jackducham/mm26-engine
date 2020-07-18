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

import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

/** This contains tests for decisions related to attacks */
public class AttackTests {

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

}
