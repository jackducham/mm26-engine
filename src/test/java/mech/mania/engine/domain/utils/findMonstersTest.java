package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class findMonstersTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Before
    public void setup() {
        gameState.addNewMonster(0, 0, 0, 0, 1, 0, 0, 0, new Position(14, 26, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 3, 0, 0, 0, new Position(10, 9, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 2, 0, 0, 0, new Position(0, 5, "pvp"));
        gameState.addNewMonster(0, 0, 0, 0, 4, 0, 0, 0, new Position(14, 24, "pvp"));
    }

    @Test
    public void testFindMonstersPlayer1() {
        List<Monster> enemies = utils.findMonsters(this.gameState, "player1");
        ArrayList<Position> ans = new ArrayList<>();
        ans.add(new Position(14, 24, "pvp"));
        ans.add(new Position(10, 9, "pvp"));
        ans.add(new Position(0, 5, "pvp"));
        ans.add(new Position(14, 26, "pvp"));
        ans.add(new Position(14, 25, "pvp"));
        assertEquals(enemies.size(), ans.size());
        for (int i = 0; i < enemies.size(); i++) {
            assertEquals(enemies.get(i).getPosition(), ans.get(i));
        }
    }
}