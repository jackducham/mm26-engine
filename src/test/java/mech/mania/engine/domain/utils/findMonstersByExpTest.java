package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class findMonstersByExpTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Before
    public void setup() {
        gameState.addNewMonster(new Monster("monster1", 0, 0, 0, 0, 1, new Position(14, 26, "pvp"), Weapon.createDefaultWeapon(), new ArrayList<>()));
        gameState.addNewMonster(new Monster("monster2", 0, 0, 0, 0, 2, new Position(10, 9, "pvp"), Weapon.createDefaultWeapon(), new ArrayList<>()));
        gameState.addNewMonster(new Monster("monster3", 0, 0, 0, 0, 3, new Position(0, 5, "pvp"), Weapon.createDefaultWeapon(), new ArrayList<>()));
        gameState.addNewMonster(new Monster("monster4", 0, 0, 0, 0, 4, new Position(14, 24, "pvp"), Weapon.createDefaultWeapon(), new ArrayList<>()));
   }

    @Test
    public void testFindMonstersPlayer1() {
        List<Monster> enemies = utils.findMonstersByExp(this.gameState, gameState.getPlayer("player1").getPosition());
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(14, 24, "pvp"));
        ans.add(new Position(0, 5, "pvp"));
        ans.add(new Position(10, 9, "pvp"));
        ans.add(new Position(14, 26, "pvp"));
        ans.add(new Position(14, 25, "pvp")); // 10 exp points
        assertEquals(enemies.size(), ans.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }
}