package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class findEnemiesInRangeTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Before
    public void setup() {
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(14, 26, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(10, 9, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 5, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 14, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(1, 16, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(1, 21, "pvp"));

        Weapon weapon1 = Weapon.createDefaultWeapon();
        Weapon weapon2 = Weapon.createStrongerDefaultWeapon();
        gameState.getPlayer("player1").setInventory(0, weapon1);
        gameState.getPlayer("player1").equipItem(0);
        gameState.getPlayer("player2").setInventory(0, weapon2);
        gameState.getPlayer("player2").equipItem(0);
    }

    @Test
    public void testFindEnemiesInRangePlayer1() {
        List<Character> enemies = utils.findEnemiesInRange(this.gameState, "player1");
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(0, 5, "pvp"));
        assertEquals(enemies.size(), ans.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }

    @Test
    public void testFindEnemiesInRangePlayer2() {
        List<Character> enemies = utils.findEnemiesInRange(this.gameState, "player2");
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(1, 21, "pvp"));
        ans.add(new Position(1, 16, "pvp"));
        ans.add(new Position(0, 14, "pvp"));
        assertEquals(enemies.size(), ans.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }
}