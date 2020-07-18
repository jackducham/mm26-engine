package mech.mania.engine;

import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.characters.*;
import mech.mania.engine.game.items.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.socket.messaging.StompSubProtocolHandler;

import static org.junit.Assert.assertEquals;

public class CharacterTests {
    private static Player playerX;
    private static Player playerY;
    private static Monster monsterA;
    private static Monster monsterB;
    private static Position spawnPointX = new Position(0,0,"id");
    private static Position spawnPointY = new Position(5,5,"id");
    private static Position spawnPointA = new Position(10,10,"id");
    private static Position spawnPointB = new Position(5,10,"id");



    @Before
    public void setUp() throws Exception {
        playerX = new Player("X", 3, 10, 0, 10, spawnPointX);
        playerY = new Player("Y", 5, 15, 5, 10, spawnPointY);

//        StatusModifier weaponStatsA = new StatusModifier();
//        TempStatusModifier weaponTempEffectsA = new TempStatusModifier();
//        Weapon weaponA = new Weapon(weaponStatsA, 5, 5, weaponTempEffectsA);
//
//        StatusModifier weaponStatsB = new StatusModifier(int speedChange, double percentSpeedChange, int healthChange, double percentHealthChange,
//        int experienceChange, double percentExperienceChange, int attackChange, double percentAttackChange,
//        int defenseChange, double percentDefenseChange, int regenPerTurn);
//        TempStatusModifier weaponTempEffectsB = new TempStatusModifier();
//        Weapon weaponB = new Weapon(weaponStatsB, 5, 5, weaponTempEffectsB);
//
//        monsterA = new Monster("A", 5, 10, 10, 10, 100, spawnPointA, weaponA, null);
//        monsterB = new Monster("B", 5, 5, 5, 5, 500, spawnPointB, weaponB, null);
    }

//    public Monster createMonster(String name, int baseSpeed, int baseMaxHealth, int baseATK, int baseDefense,
//                                 int experience, Position spawnPoint, Weapon) {
//        Monster monster = new Monster();
//        return monster;
//    }

    @Test
    public void getBaseSpeed() {
        assertEquals(3, playerX.getSpeed());
        assertEquals(5, playerY.getSpeed());
    }

    @Test
    public void getBaseMaxHealth() {
        assertEquals(10, playerX.getMaxHealth());
        assertEquals(15, playerY.getMaxHealth());
    }

    @Test
    public void getBase() {
        assertEquals(3, playerX.getSpeed());
        assertEquals(5, playerY.getSpeed());
    }
}
