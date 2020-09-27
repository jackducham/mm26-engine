package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.CharacterProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.*;

public class MagicEffectTests {

    private GameState gameState;
    private Player p1, p2;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = GameState.createDefaultGameState();

        // Two players are created by createDefaultGameState
        p1 = gameState.getPlayer("player1");
        p2 = gameState.getPlayer("player2");
    }

    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {

    }

    /**
     * Tests LINGERING_POTIONS hat effect
     */
    @Test
    public void lingeringPotionsEffect(){
        // Move player off their spawn
        p1.setPosition(new Position(0, 0, "player"));

        // gives player1 a hat, equips it, and gives them a potion.
        p1.pickupItem(new Hat(new StatusModifier(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                MagicEffect.LINGERING_POTIONS, ""));
        p1.equipItem(0);
        p1.pickupItem(Consumable.createDefaultConsumable());

        // Put the player down to 5 hp
        p1.applyDamage("player2", true, Player.BASE_MAX_HEALTH-5);


        // Check that the player starts with 5 hp
        int currentHP = p1.getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + p1.getMaxHealth());
        assertEquals(5, currentHP);

        // Create a decision to use the potion
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.EQUIP);
        decision.setIndex(0);


        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap); // Hit with effect

        // The player should now have a potion effect lasting a total of 10 rounds, healing 2 HP per round
        // do turn should have applied the effect once, leaving the turnsLeft at 9, and player1's HP at 7

        // Check that the player now has 7 HP
        currentHP = p1.getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + p1.getMaxHealth());
        assertEquals(7, currentHP);

        // Moves the game forward one turn without the player doing anything.
        decision.setDecisionType(CharacterProtos.DecisionType.NONE);
        GameLogic.doTurn(gameState, decisionMap);

        // Check that the player now has 9 HP
        currentHP = p1.getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + p1.getMaxHealth());
        assertEquals(9, currentHP);

        // Steps forward 5 more turns for a total of 7 turns of healing. Default consumable has a duration of 5,
        // so this should make the HP 19, as that was doubled to 10 turns.
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);

        currentHP = p1.getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + p1.getMaxHealth());
        assertEquals(19, currentHP);
    }

    @Test
    public void ShoesBoostEffect() {
        // gives player1 a hat and equips it.
        p1.pickupItem(new Hat(new StatusModifier(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                MagicEffect.SHOES_BOOST, ""));

        p1.pickupItem(Shoes.createDefaultShoes());
        p1.equipItem(0);
        p1.equipItem(0);

        // tests to see that the player's speed is 15 (base plus 2 * 5 from default shoes)
        assertEquals(10 + Player.BASE_SPEED, p1.getSpeed());
    }

    @Test
    public void WeaponBoostEffect() {
        // gives player1 a hat and equips it.
        p1.pickupItem(new Hat(new StatusModifier(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                MagicEffect.WEAPON_BOOST, ""));
        p1.pickupItem(Weapon.createStrongerDefaultWeapon());
        p1.equipItem(0);
        p1.equipItem(0);

        // tests to see that the player's attack is 7 (base of 0 plus 5 + 5*0.5 from stronger default weapon)
        assertEquals(7, p1.getAttack());
    }

    @Test
    public void ClothesBoostEffect() {
        // gives player1 a hat and equips it.
        p1.pickupItem(new Hat(new StatusModifier(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                MagicEffect.CLOTHES_BOOST, ""));
        p1.pickupItem(Clothes.createDefaultClothes());
        p1.equipItem(0);
        p1.equipItem(0);

        // tests to see that the player's speed is 16 (base of 0 plus 2 * 8 from default clothes)
        assertEquals(16, p1.getDefense());
    }

    @Test
    public void TripledOnHitEffect() {
        // gives player1 a hat and weapon.
        p1.pickupItem(new Hat(new StatusModifier(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                MagicEffect.TRIPLED_ON_HIT, ""));
        p1.pickupItem(Weapon.createStrongerDefaultWeapon());
        p1.equipItem(0);
        p1.equipItem(0);

        // Disable damage on p1 to focus on effects
        p1.getWeapon().setAttack(0);

        // Move p1 and p2 close enough for combat
        p1.setPosition(new Position(0, 0, "pvp"));
        p2.setPosition(new Position(1, 0, "pvp"));

        // Confirm that p1 is able to hit p2
        Map<Position, Integer> affectedPositions = GameLogic.returnAffectedPositions(gameState, p1, p2.getPosition());
        assertNotNull(affectedPositions);
        assertTrue(affectedPositions.containsKey(p2.getPosition()));

        // Create a decision to attack player2
        CharacterProtos.CharacterDecision.Builder decision = CharacterProtos.CharacterDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.ATTACK);
        decision.setTargetPosition(p2.getPosition().buildProtoClass());

        // Execute decision
        HashMap<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // player2 gets hit with three effects, each causing 5 damage per turn, plus 3 dmg because of 1 dmg min of attacks
        //assertEquals(0, p1.getAttack());
        assertEquals(Player.BASE_MAX_HEALTH-18, p2.getCurrentHealth());

        CharacterProtos.CharacterDecision.Builder emptyDecision = CharacterProtos.CharacterDecision.newBuilder();
        emptyDecision.setDecisionType(CharacterProtos.DecisionType.NONE);
        HashMap<String, CharacterProtos.CharacterDecision> emptyDecisionMap = new HashMap<>();
        emptyDecisionMap.put("player1", emptyDecision.build());
        emptyDecisionMap.put("player2", emptyDecision.build());
        GameLogic.doTurn(gameState, emptyDecisionMap);

        // player2's 3 effects tick for 5 dmg each
        assertEquals(Player.BASE_MAX_HEALTH-33, p2.getCurrentHealth());
    }

    @Test
    public void stackingBonusEffect() {
        // gives player1 a hat.
        p1.pickupItem(new Hat(new StatusModifier(1,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                MagicEffect.STACKING_BONUS, ""));
        p1.equipItem(0);

        //base speed plus 1 from the hat.
        assertEquals(1+Player.BASE_SPEED, p1.getSpeed());

        CharacterProtos.CharacterDecision.Builder emptyDecision = CharacterProtos.CharacterDecision.newBuilder();
        emptyDecision.setDecisionType(CharacterProtos.DecisionType.NONE);
        HashMap<String, CharacterProtos.CharacterDecision> emptyDecisionMap = new HashMap<>();
        emptyDecisionMap.put("player1", emptyDecision.build());
        emptyDecisionMap.put("player2", emptyDecision.build());
        GameLogic.doTurn(gameState, emptyDecisionMap);
        //base speed plus 1 from the hat plus one TSM.
        assertEquals(2+Player.BASE_SPEED, p1.getSpeed());

        GameLogic.doTurn(gameState, emptyDecisionMap);
        GameLogic.doTurn(gameState, emptyDecisionMap);
        //base speed plus one from the hat plus 3 TSMs.
        assertEquals(4+Player.BASE_SPEED, p1.getSpeed());

        GameLogic.doTurn(gameState, emptyDecisionMap);
        GameLogic.doTurn(gameState, emptyDecisionMap);
        GameLogic.doTurn(gameState, emptyDecisionMap);
        GameLogic.doTurn(gameState, emptyDecisionMap);
        GameLogic.doTurn(gameState, emptyDecisionMap);
        GameLogic.doTurn(gameState, emptyDecisionMap);

        //base speed plus one from hat plus 4 TSMs. (We've added more than 4, but the durations on the earliest
        // have begun to run out. This leaves us with a number of TSMs equal to the duration.
        assertEquals(5+Player.BASE_SPEED, p1.getSpeed());
    }
}
