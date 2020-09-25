package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.model.CharacterProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/** This contains tests for decisions related to attacks */
public class AttackTests {
    private GameState gameState;
    private Player p1;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = GameState.createDefaultGameState();
        p1 = gameState.getPlayer("player1");
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

    public void doTurn(int index) {
        // pick up item from tile
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.NONE);
        decision.setIndex(index);

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);
    }

    @Test
    public void updateLevel() {
        p1.addExperience(10);
        doTurn(0);
        assertEquals(1, p1.getLevel());
        assertEquals(10, p1.getExperience());

        p1.addExperience(90);
        doTurn(0);
        assertEquals(2, p1.getLevel());
        assertEquals(0, p1.getExperience());

        p1.addExperience(100);
        doTurn(0);
        assertEquals(2, p1.getLevel());
        assertEquals(100, p1.getExperience());

        p1.addExperience(150);
        doTurn(0);
        assertEquals(3, p1.getLevel());
        assertEquals(50, p1.getExperience());

        p1.addExperience(700);
        doTurn(0);
        assertEquals(5, p1.getLevel());
        assertEquals(50, p1.getExperience());
    }

}