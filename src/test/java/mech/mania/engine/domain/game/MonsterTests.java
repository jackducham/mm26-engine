package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.CharacterDecision;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MonsterTests {

    private GameState gameState;

    @Before
    public void setup(){
        gameState = new GameState();
    }

    @After
    public void cleanup(){

    }

    @Test
    public void testMonsterReturnToSpawn(){
        // Add default monster which spawns at (0, 0)
        Monster monster = Monster.createDefaultMonster(0, 0, 0, 0,
                0, 0,0, 0, new Position(0, 0, "pvp"));

        gameState.addNewMonster(monster);

        // Move monster to (0, 1)
        monster.setPosition(new Position(0, 1, "pvp"));

        // Assert that monster is not at spawn
        assertNotEquals(monster.getPosition(), monster.getSpawnPoint());

        // Run a turn
        HashMap<String, PlayerProtos.PlayerDecision> emptyDecisionMap = new HashMap<>();
        GameLogic.doTurn(gameState, emptyDecisionMap);

        // Assert that monster is now at spawn
        assertEquals(monster.getPosition(), monster.getSpawnPoint());
    }

    @Test
    public void testMonsterReturnToSpawnLongDistance(){
        // Add default monster which spawns at (0, 0)
        Monster monster = Monster.createDefaultMonster(0, 0, 0, 0,
                0, 0,0, 0, new Position(0, 0, "pvp"));

        gameState.addNewMonster(monster);

        // Move monster to (0, speed+1)
        monster.setPosition(new Position(0, monster.getSpeed()+1, "pvp"));

        // Assert that monster is not at spawn
        assertNotEquals(monster.getPosition(), monster.getSpawnPoint());

        // Run a turn
        GameLogic.doTurn(gameState, Collections.emptyMap());

        // Assert that monster is still not at spawn
        assertNotEquals(monster.getPosition(), monster.getSpawnPoint());

        // Run a turn
        GameLogic.doTurn(gameState, Collections.emptyMap());

        // Assert that monster is now at spawn
        assertEquals(monster.getPosition(), monster.getSpawnPoint());
    }

    @Test
    public void testMonsterTargetingOnePlayer(){
        // Add default monster which spawns at (0, 0)
        //  use high attackFactor to make sure monster deals damage to monster
        Monster monster = Monster.createDefaultMonster(0, 0, 100, 0,
                0, 0,0, 0, new Position(0, 0, "pvp"));
        gameState.addNewMonster(monster);

        // Add new player at (0, 1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 1, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", 1);

        // Assert that Monster wants to attack player1
        assertEquals(monster.makeDecision(gameState).getDecision(), CharacterDecision.decisionTypes.ATTACK);
        assertEquals(monster.makeDecision(gameState).getActionPosition(), player1.getPosition());

        // Assert that after 1 turn, player1 was damaged by the monster
        int initialPlayerHealth = player1.getCurrentHealth();
        GameLogic.doTurn(gameState, Collections.emptyMap());
        int finalPlayerHealth = player1.getCurrentHealth();

        // Assert greater than because exact damage is unknown
        assertTrue(initialPlayerHealth > finalPlayerHealth);
    }

    @Test
    public void testMonsterTargetingMultiplePlayers(){
        // Add default monster which spawns at (0, 0)
        //  use high attackFactor to make sure monster deals damage to monster
        Monster monster = Monster.createDefaultMonster(0, 0, 100, 0,
                0, 0,0, 0, new Position(0, 0, "pvp"));
        gameState.addNewMonster(monster);

        // Add new player at (0, 1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 1, "pvp"));

        // Add another player at (1, 0)
        gameState.addNewPlayer("player2");
        Player player2 = gameState.getPlayer("player2");
        player2.setPosition(new Position(1, 0, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", 1);

        // Add Player2 to monster's aggro table with more damage
        monster.applyDamage("player2", 2);

        // Assert that Monster wants to attack player2
        assertEquals(monster.makeDecision(gameState).getDecision(), CharacterDecision.decisionTypes.ATTACK);
        assertEquals(monster.makeDecision(gameState).getActionPosition(), player2.getPosition());

        // Assert that after 1 turn, player1 was not damaged by the monster
        //  and player2 was
        int initialPlayer1Health = player1.getCurrentHealth();
        int initialPlayer2Health = player2.getCurrentHealth();

        GameLogic.doTurn(gameState, Collections.emptyMap());

        int finalPlayer1Health = player1.getCurrentHealth();
        int finalPlayer2Health = player2.getCurrentHealth();

        assertEquals(initialPlayer1Health, finalPlayer1Health);

        // Assert greater than because exact damage is unknown
        assertTrue(initialPlayer2Health > finalPlayer2Health);
    }

    @Test
    public void testMonsterMovingTowardOnePlayer(){
        // Add default monster which spawns at (0, 0)
        //  use high attackFactor to make sure monster deals damage to monster
        Monster monster = Monster.createDefaultMonster(0, 0, 100, 0,
                0, 0,0, 0, new Position(0, 0, "pvp"));
        gameState.addNewMonster(monster);

        // Add new player at (0, range+1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, monster.getWeapon().getRange()+1, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", 1);

        // Assert that Monster wants to move towards player1
        assertEquals(monster.makeDecision(gameState).getDecision(), CharacterDecision.decisionTypes.MOVE);
        assertEquals(monster.makeDecision(gameState).getActionPosition(), player1.getPosition());

        // Assert that after 1 turn, player1 was not damaged by the monster
        //  and that the monster has moved to the player
        assertNotEquals(monster.getPosition(), player1.getPosition());

        int initialPlayerHealth = player1.getCurrentHealth();
        GameLogic.doTurn(gameState, Collections.emptyMap());
        int finalPlayerHealth = player1.getCurrentHealth();

        assertEquals(initialPlayerHealth, finalPlayerHealth);
        assertEquals(monster.getPosition(), player1.getPosition());
    }

    @Test
    public void testMonsterMovingTowardOnePlayerOverAnother(){
        // Add default monster which spawns at (0, 0)
        //  use high attackFactor to make sure monster deals damage to monster
        Monster monster = Monster.createDefaultMonster(0, 0, 100, 0,
                0, 0,0, 0, new Position(0, 0, "pvp"));
        gameState.addNewMonster(monster);

        // Add new player at (0, range+1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, monster.getWeapon().getRange()+1, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", 1);

        // Add another player at (range + 1, 0)
        gameState.addNewPlayer("player2");
        Player player2 = gameState.getPlayer("player2");
        player2.setPosition(new Position(monster.getWeapon().getRange()+1, 0, "pvp"));

        // Add Player2 to monster's aggro table above player1
        monster.applyDamage("player2", 2);

        // Assert that Monster wants to move towards player2
        assertEquals(CharacterDecision.decisionTypes.MOVE, monster.makeDecision(gameState).getDecision());
        assertEquals(player2.getPosition(), monster.makeDecision(gameState).getActionPosition());

        // Assert that after 1 turn, the monster has moved to player2
        assertNotEquals(monster.getPosition(), player1.getPosition());
        assertNotEquals(monster.getPosition(), player2.getPosition());

        GameLogic.doTurn(gameState, Collections.emptyMap());

        assertEquals(monster.getPosition(), player2.getPosition());
    }
}