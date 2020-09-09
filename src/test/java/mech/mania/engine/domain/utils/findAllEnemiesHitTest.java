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

public class findAllEnemiesHitTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Before
    public void setup() {
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(14, 26, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(10, 9, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 5, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 14, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(1, 16, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(1, 21, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 25, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(0, 26, "pvp"));

        Weapon weapon1 = Weapon.createDefaultWeapon();
        Weapon weapon2 = Weapon.createStrongerDefaultWeapon();
        gameState.getPlayer("player1").setInventory(0, weapon1);
        gameState.getPlayer("player1").equipItem(0);
        gameState.getPlayer("player2").setInventory(0, weapon2);
        gameState.getPlayer("player2").equipItem(0);
    }

    @Test
    public void testFindAllEnemiesHitPlayer1() {
        List<Character> enemies = utils.findAllEnemiesHit(this.gameState, "player1", new Position(0, 5, "pvp"));
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(0, 5, "pvp"));
        assertEquals(ans.size(), enemies.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }

    @Test
    public void testFindAllEnemiesHitPlayer1Splash() {
        List<Character> enemies = utils.findAllEnemiesHit(this.gameState, "player1", new Position(0, 7, "pvp"));
        ArrayList<Position> ans = new ArrayList<>();
        assertEquals(ans.size(), enemies.size());
    }

    @Test
    public void testFindAllEnemiesHitPlayer2Splash() {
        List<Character> enemies = utils.findAllEnemiesHit(this.gameState, "player2", new Position(0, 25, "pvp"));
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(0, 25, "pvp"));
        ans.add(new Position(0, 26, "pvp"));
        ans.add(new Position(1, 21, "pvp"));
        assertEquals(ans.size(), enemies.size());

        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }
}