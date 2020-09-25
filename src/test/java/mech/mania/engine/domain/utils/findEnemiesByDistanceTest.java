package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class findEnemiesByDistanceTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Before
    public void setup() {
        gameState.addNewMonster(new Monster("monster1", "", 0, 0, 0, 0, 0,
                new Position(0, 21, "pvp"), Weapon.createDefaultWeapon(), 0));
        gameState.addNewMonster(new Monster("monster2", "", 0, 0, 0, 0, 0,
                new Position(0, 11, "pvp"), Weapon.createDefaultWeapon(), 0));
        gameState.addNewMonster(new Monster("monster3", "", 0, 0, 0, 0, 0,
                new Position(0, 1, "pvp"), Weapon.createDefaultWeapon(), 0));
        gameState.addNewMonster(new Monster("monster4", "", 0, 0, 0, 0, 0,
                new Position(0, 2, "pvp"), Weapon.createDefaultWeapon(), 0));
    }

    @Test
    public void testFindEnemiesPlayer1() {
        List<Character> enemies = utils.findEnemiesByDistance(this.gameState, gameState.getPlayer("player1").getPosition(), "player1");
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(0, 2, "pvp"));
        ans.add(new Position(0, 1, "pvp"));
        ans.add(new Position(0, 11, "pvp"));
        ans.add(new Position(0, 21, "pvp"));
        ans.add(new Position(0, 24, "pvp"));
        ans.add(new Position(14, 25, "pvp"));
        assertEquals(enemies.size(), ans.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }

    @Test
    public void testFindEnemiesPlayer2() {
        List<Character> enemies = utils.findEnemiesByDistance(this.gameState, gameState.getPlayer("player2").getPosition(), "player2");
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(0, 21, "pvp"));
        ans.add(new Position(0, 11, "pvp"));
        ans.add(new Position(14, 25, "pvp"));
        ans.add(new Position(0, 4, "pvp"));
        ans.add(new Position(0, 2, "pvp"));
        ans.add(new Position(0, 1, "pvp"));
        assertEquals(enemies.size(), ans.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }
}
