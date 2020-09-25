package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ReviveTests {
    private GameState gameState;

    @Before
    public void setup(){
        gameState = GameState.createDefaultGameState();
    }

    private void passTurn(){
        GameLogic.doTurn(gameState, Collections.emptyMap());
    }

    @Test
    public void testPlayerRevive(){
        Player p1 = gameState.getPlayer("player1");

        // Should start out alive
        assertFalse(p1.isDead());

        // After processing this turn, p1 should be dead
        p1.applyDamage("player2", true, p1.getMaxHealth());
        passTurn();
        assertTrue(p1.isDead());

        // For the next Player.REVIVE_TICKS turns, p1 should be dead
        for(int t = 0; t < Player.REVIVE_TICKS; t++){
            assertTrue(p1.isDead());
            passTurn();
        }

        // p1 should now be alive
        assertFalse(p1.isDead());
    }

    @Test
    public void testMonsterRevive(){
        Monster m = new Monster("fake", "", 0, 0, 0, 0, 0,
                new Position(0, 0, "pvp"), null, 0, new ArrayList<>());

        gameState.addNewMonster(m);

        // Should start out alive
        assertFalse(m.isDead());

        // After processing this turn, m should be dead
        m.applyDamage("player1", true, m.getMaxHealth());
        passTurn();
        assertTrue(m.isDead());

        // For the next Monster.REVIVE_TICKS turns, m should be dead
        for(int t = 0; t < Monster.REVIVE_TICKS; t++){
            assertTrue(m.isDead());
            passTurn();
        }

        // m should now be alive
        assertFalse(m.isDead());
    }
}
