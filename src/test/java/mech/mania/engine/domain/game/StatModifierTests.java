package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.max;
import static junit.framework.TestCase.assertEquals;

/** This contains tests for decisions related to attacks */
public class StatModifierTests {
    private GameState gameState;
    private Player p1, p2;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = GameState.createDefaultGameState();
        p1 = gameState.getPlayer("player1");
        p2 = gameState.getPlayer("player2");
    }

    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {

    }

    /**
     * Helper function to pass a turn with no actions
     */
    public void passTurn(){
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.MOVE);

        Map<String, PlayerProtos.PlayerDecision> map = new HashMap<>();
        map.put("player1", decision.build());
        map.put("player2", decision.build());

        GameLogic.doTurn(gameState, map);
    }

    /**
     * Test TempStatusModifiers on players as a buff
     */
    @Test
    public void testTempStatusModifiersBuff() {
        // Create status effect
        TempStatusModifier effect = new TempStatusModifier(
                5, 0.5,
                5, 0.5,
                5, 0.5,
                5, 0.5,
                5, 0.5,
                0, 1, 0);

        // Record current stats
        int speed = p1.getSpeed();
        int maxHealth = p1.getMaxHealth();
        int experience = p1.getExperience();
        int attack = p1.getAttack();
        int defense = p1.getDefense();

        // Add status effect to player1 from player2
        p1.applyEffect(effect, p2.getName(), true);

        // Record expected stats
        int expectedSpeed = (int)((speed + 5) * (1.5));
        int expectedMaxHealth = (int)((maxHealth + 5) * (1.5));
        int expectedExperience = experience; // Experience modifiers modify experience gained, not current XP
        // getAttack returns a min of 1 even though baseAttack is 0
        //  so here we use 0 as the original attack
        int expectedAttack = (int)(5 * (1.5));
        int expectedDefense = (int)((defense + 5) * (1.5));

        // Pass a single turn and expect the effect to be in action
        passTurn();

        assertEquals(expectedSpeed, p1.getSpeed());
        assertEquals(expectedMaxHealth, p1.getMaxHealth());
        assertEquals(expectedExperience, p1.getExperience());
        assertEquals(expectedAttack, p1.getAttack());
        assertEquals(expectedDefense, p1.getDefense());

        // Pass another turn and expect the effect to have worn off
        passTurn();

        assertEquals(speed, p1.getSpeed());
        assertEquals(maxHealth, p1.getMaxHealth());
        assertEquals(experience, p1.getExperience());
        assertEquals(attack, p1.getAttack());
        assertEquals(defense, p1.getDefense());
    }

    /**
     * Test TempStatusModifiers on players as a debuff
     */
    @Test
    public void testTempStatusModifiersDebuff() {
        // Create status effect
        TempStatusModifier effect = new TempStatusModifier(
                -1, -0.5,
                -1, -0.5,
                -1, -0.5,
                -1, -0.5,
                -1, -0.5,
                0, 1, 0);

        // Record current stats
        int speed = p1.getSpeed();
        int maxHealth = p1.getMaxHealth();
        int experience = p1.getExperience();
        int attack = p1.getAttack();
        int defense = p1.getDefense();

        // Add status effect to player1 from player2
        p1.applyEffect(effect, p2.getName(), true);

        // Record expected stats
        int expectedSpeed = max(1, (int)((speed - 1) * (0.5)));
        int expectedMaxHealth = max(1, (int)((maxHealth - 1) * (0.5)));
        int expectedExperience = experience; // Experience modifiers modify experience gained, not current XP
        int expectedAttack = max(1, (int)((attack - 1) * (0.5)));
        int expectedDefense = (int)((defense - 1) * (0.5));

        // Pass a single turn and expect the effect to be in action
        passTurn();

        assertEquals(expectedSpeed, p1.getSpeed());
        assertEquals(expectedMaxHealth, p1.getMaxHealth());
        assertEquals(expectedExperience, p1.getExperience());
        assertEquals(expectedAttack, p1.getAttack());
        assertEquals(expectedDefense, p1.getDefense());

        // Pass another turn and expect the effect to have worn off
        passTurn();

        assertEquals(speed, p1.getSpeed());
        assertEquals(maxHealth, p1.getMaxHealth());
        assertEquals(experience, p1.getExperience());
        assertEquals(attack, p1.getAttack());
        assertEquals(defense, p1.getDefense());
    }
}