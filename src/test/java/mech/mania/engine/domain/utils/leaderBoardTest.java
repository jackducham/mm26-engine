package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Player;
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

public class leaderBoardTest {

    @Test
    public void testLeaderBoardPlayer1() {
        GameState gameState1 = GameState.createDefaultGameState();
        gameState1.getMonster("DefaultMonster0").applyDamage("player1", 1000);
        gameState1.getMonster("DefaultMonster0").distributeRewards(gameState1);
        List<Player> leaderboard = utils.leaderBoard(gameState1);
        List<Player> ans = new ArrayList<>();
        ans.add(gameState1.getPlayer("player1"));
        ans.add(gameState1.getPlayer("player2"));
        assertEquals(leaderboard.size(), ans.size());
        for (int i = 0; i < leaderboard.size(); i++) {
            assertEquals(ans.get(i), leaderboard.get(i));
        }

        GameState gameState2 = GameState.createDefaultGameState();
        gameState2.getMonster("DefaultMonster1").applyDamage("player2", 1000);
        gameState2.getMonster("DefaultMonster1").distributeRewards(gameState2);
        List<Player> leaderboard2 = utils.leaderBoard(gameState2);
        List<Player> ans2 = new ArrayList<>();
        ans2.add(gameState2.getPlayer("player2"));
        ans2.add(gameState2.getPlayer("player1"));
        assertEquals(leaderboard2.size(), ans2.size());
        for (int i = 0; i < leaderboard2.size(); i++) {
            assertEquals(ans2.get(i), leaderboard2.get(i));
        }
    }
}