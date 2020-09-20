package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;
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

        Player p1 = gameState1.getPlayer("player1");
        Player p2 = gameState1.getPlayer("player2");
        Player p3 = gameState1.getPlayer("player3");

        // Set all players to level 5
        p1.addExperience(1000);
        p2.addExperience(1000);
        p3.addExperience(1000);
        p1.updateLevel();
        p2.updateLevel();
        p3.updateLevel();

        // Create a single monster with low level and kill it w/ player2
        gameState1.addNewMonster(new Monster("monster", "", 0, 0, 0, 0, 1,
                new Position(14, 26, "pvp"), Weapon.createDefaultWeapon(), new ArrayList<>()));
        gameState1.getMonster("monster").applyDamage("player2", true,1000);
        gameState1.getMonster("monster").distributeRewards(gameState1);
        List<Player> leaderboard = utils.leaderBoard(gameState1);
        List<Player> ans = new ArrayList<>();
        ans.add(gameState1.getPlayer("player2"));
        ans.add(gameState1.getPlayer("player1"));
        ans.add(gameState1.getPlayer("player3"));
        assertEquals(leaderboard.size(), ans.size());
        for (int i = 0; i < leaderboard.size(); i++) {
            assertEquals(ans.get(i), leaderboard.get(i));
        }

        // Create a single monster with equal level and kill it w/ player3
        // equal level monsters give more XP, so player3 should be in the lead
        gameState1.addNewMonster(new Monster("monster1", "",  0, 0, 0, 0, 5,
                new Position(14, 26, "pvp"), Weapon.createDefaultWeapon(), new ArrayList<>()));
        gameState1.getMonster("monster1").applyDamage("player3", true,1000);
        gameState1.getMonster("monster1").distributeRewards(gameState1);
        List<Player> leaderboard2 = utils.leaderBoard(gameState1);
        List<Player> ans2 = new ArrayList<>();
        ans2.add(gameState1.getPlayer("player3"));
        ans2.add(gameState1.getPlayer("player2"));
        ans2.add(gameState1.getPlayer("player1"));
        assertEquals(leaderboard2.size(), ans2.size());

        for(int i = 0; i < 3; i++){
            System.out.println(leaderboard2.get(i).getName() + ": " + leaderboard2.get(i).getTotalExperience());
        }

        for (int i = 0; i < leaderboard2.size(); i++) {
            System.out.println("Answer: " + ans2.get(i).getName() + " | Actual: " + leaderboard2.get(i).getName());
            assertEquals(ans2.get(i), leaderboard2.get(i));
        }
    }
}