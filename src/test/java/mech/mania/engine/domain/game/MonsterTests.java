package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.CharacterDecision;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.model.CharacterProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.*;

public class MonsterTests {

    private GameState gameState;
    private Monster monster;
    private GameState gameState1;
    private Weapon weapon1;
    private Monster monster1;
    private Position monsterPos;

    @Before
    public void setup(){
        gameState = GameState.createDefaultGameState();

        // Add default monster which spawns at (0, 0)
        //  use high attackFactor to make sure monster deals damage to monster
        monster = Monster.createDefaultMonster(3, 0, 100, 0,
                0, 0,0, 0, 5, 0, new Position(0, 0, "pvp"));
        gameState.addNewMonster(monster);

        gameState1 = new GameState();

        monsterPos = new Position(10,10, "pvp");
        weapon1 = new Weapon(null,5,3,10,null, "");
        monster1 = new Monster("m1", "", 1, 1, 1, 1, 1, monsterPos,weapon1,10,null);
        gameState1.addNewMonster(monster1);
        gameState1.addNewPlayer("player1");
    }

    @After
    public void cleanup(){

    }

    @Test
    public void testMonsterAggroRange() {
        // Add new player at (0, 3)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 3, "pvp"));

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
    public void testNothingInRangeReturn() {
        monster.setPosition(new Position(0, 1, "pvp"));

        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 7, "pvp"));

        // Run a turn
        HashMap<String, CharacterProtos.CharacterDecision> emptyDecisionMap = new HashMap<>();
        GameLogic.doTurn(gameState, emptyDecisionMap);

        // Assert that monster is now at spawn
        assertEquals(monster.getPosition(), monster.getSpawnPoint());
    }

    @Test
    public void testDamagePriorityOverRange() {
        monster.setPosition(new Position(0, 0, "pvp"));

        // add player outside range
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 8, "pvp"));

        // add player within range
        gameState.addNewPlayer("player2");
        Player player2 = gameState.getPlayer("player2");
        player2.setPosition(new Position(0, 2, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", true, 1);

        // Assert that Monster wants to attack player1
        assertEquals(monster.makeDecision(gameState).getDecision(), CharacterDecision.decisionTypes.MOVE);
        assertEquals(monster.makeDecision(gameState).getActionPosition(), player1.getPosition());
    }

    @Test
    public void testMonsterReturnToSpawn(){
        // Remove all players
        gameState.getAllPlayers().clear();

        // Move monster to (0, 1)
        monster.setPosition(new Position(0, 1, "pvp"));

        // Assert that monster is not at spawn
        assertNotEquals(monster.getPosition(), monster.getSpawnPoint());

        assertEquals(CharacterDecision.decisionTypes.MOVE, monster.makeDecision(gameState).getDecision());

        // Run a turn
        HashMap<String, CharacterProtos.CharacterDecision> emptyDecisionMap = new HashMap<>();
        GameLogic.doTurn(gameState, emptyDecisionMap);

        // Assert that monster is now at spawn
        assertEquals(monster.getPosition(), monster.getSpawnPoint());
    }

    @Test
    public void testMonsterReturnToSpawnLongDistance(){
        // Remove all players
        gameState.getAllPlayers().clear();

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
        // Add new player at (0, 1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 1, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", true, 1);

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
        // Add new player at (0, 1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, 1, "pvp"));

        // Add another player at (1, 0)
        gameState.addNewPlayer("player2");
        Player player2 = gameState.getPlayer("player2");
        player2.setPosition(new Position(1, 0, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", true,1);

        // Add Player2 to monster's aggro table with more damage
        monster.applyDamage("player2", true,2);

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
        // Add new player at (0, range+1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, monster.getWeapon().getRange()+1, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", true,1);

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
        // Add new player at (0, range+1)
        gameState.addNewPlayer("player1");
        Player player1 = gameState.getPlayer("player1");
        player1.setPosition(new Position(0, monster.getWeapon().getRange()+1, "pvp"));

        // Add Player1 to monster's aggro table
        monster.applyDamage("player1", true,1);

        // Add another player at (range + 1, 0)
        gameState.addNewPlayer("player2");
        Player player2 = gameState.getPlayer("player2");
        player2.setPosition(new Position(monster.getWeapon().getRange()+1, 0, "pvp"));

        // Add Player2 to monster's aggro table above player1
        monster.applyDamage("player2", true,2);

        // Assert that Monster wants to move towards player2
        assertEquals(CharacterDecision.decisionTypes.MOVE, monster.makeDecision(gameState).getDecision());
        assertEquals(player2.getPosition(), monster.makeDecision(gameState).getActionPosition());

        // Assert that after 1 turn, the monster has moved to player2
        assertNotEquals(monster.getPosition(), player1.getPosition());
        assertNotEquals(monster.getPosition(), player2.getPosition());

        GameLogic.doTurn(gameState, Collections.emptyMap());

        assertEquals(monster.getPosition(), player2.getPosition());
    }


    public void testAttackPlayerInSplashRange(int x, int y) {
        Player player1 = gameState1.getPlayer("player1");
        player1.setPosition(new Position(x, y, "pvp"));
//        System.out.println(monster1.getAggroRange());
//        System.out.println(player1.getPosition());

        // Assert that Monster wants to attack player1
        assertEquals(CharacterDecision.decisionTypes.ATTACK, monster1.makeDecision(gameState1).getDecision());
        Position actionPosition = monster1.makeDecision(gameState1).getActionPosition();
        System.out.println(actionPosition);
        assertTrue(actionPosition.manhattanDistance(monster1.getPosition()) <= weapon1.getRange());

        // Assert that after 1 turn, player1 was damaged by the monster
        int initialPlayerHealth = player1.getCurrentHealth();
        GameLogic.doTurn(gameState1, Collections.emptyMap());
        int finalPlayerHealth = player1.getCurrentHealth();

        // Assert greater than because exact damage is unknown
        assertTrue(initialPlayerHealth > finalPlayerHealth);
    }

    @Test
    public void testAttackPlayerInSplashRangeY() {
        monster1.setPosition(monsterPos);
        testAttackPlayerInSplashRange(10,18);
        testAttackPlayerInSplashRange(10,2);
    }

    @Test
    public void testAttackPlayerInSplashRangeX() {
        monster1.setPosition(monsterPos);
        testAttackPlayerInSplashRange(16,10);
        testAttackPlayerInSplashRange(4,10);
    }

    @Test
    public void testAttackPlayerInSplashRangeQuad1() {
        monster1.setPosition(monsterPos);
        testAttackPlayerInSplashRange(12,14);
        testAttackPlayerInSplashRange(15,13);
    }

    @Test
    public void testAttackPlayerInSplashRangeQuad2() {
        monster1.setPosition(monsterPos);
        testAttackPlayerInSplashRange(5,11);
        testAttackPlayerInSplashRange(7,14);
    }

    @Test
    public void testAttackPlayerInSplashRangeQuad3() {
        monster1.setPosition(monsterPos);
        testAttackPlayerInSplashRange(5,7);
        testAttackPlayerInSplashRange(8,6);
    }

    @Test
    public void testAttackPlayerInSplashRangeQuad4() {
        monster1.setPosition(monsterPos);
        testAttackPlayerInSplashRange(15,8);
        testAttackPlayerInSplashRange(13,6);
    }
}