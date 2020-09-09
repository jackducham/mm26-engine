package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.StatusModifier;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class canBeAttackedTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Before
    public void setup() {
        Weapon weapon1 = Weapon.createDefaultWeapon();

        gameState.getPlayer("player1").setInventory(0, weapon1);
        gameState.getPlayer("player1").equipItem(0);
    }

    @Test
    public void testCannotBeAttackedPlayer1() {
        boolean canBeAttacked = utils.canBeAttacked(this.gameState, "player1");
        assertFalse(canBeAttacked);
    }

    @Test
    public void testCanBeAttackedPlayer1() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 1, 0, 1, 0, 1, 5, 1, 0, 1, 0);
        TempStatusModifier defaultTempStatusModifier = new TempStatusModifier(0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 10, 5);
        Weapon weapon2 = new Weapon(defaultStatusModifier, 10, 10, 10, defaultTempStatusModifier);

        gameState.getPlayer("player2").setInventory(0, weapon2);
        gameState.getPlayer("player2").equipItem(0);

        boolean canBeAttacked = utils.canBeAttacked(this.gameState, "player1");
        assertTrue(canBeAttacked);
    }

    @Test
    public void testCannotBeAttackedPlayer2() {
        boolean canBeAttacked = utils.canBeAttacked(this.gameState, "player2");
        assertFalse(canBeAttacked);
    }

    @Test
    public void testCanBeAttackedPlayer2() {
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 24, "pvp"));
        boolean canBeAttacked = utils.canBeAttacked(this.gameState, "player2");
        assertTrue(canBeAttacked);
    }
}