package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class HatEffectTests {

    private GameState gameState;
    private Player p1;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = GameState.createDefaultGameState();
        p1 = gameState.getPlayer("player1");
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
        // gives player1 a hat, equips it, and gives them a potion.
        Player player1 = gameState.getPlayer("player1");
        player1.setInventory(1, new Hat(new StatusModifier(0,
                0, 0, 1, 0, 0,
                0, 0, 0, 0, 0),
                HatEffect.LINGERING_POTIONS));
        player1.equipItem(1);
        player1.setInventory(2, Consumable.createDefaultConsumable());

        // deals 15 damage to the player.
        player1.applyDamage("player2", 15);


        // Check that the player starts with 5 hp
        int currentHP = gameState.getPlayer("player1").getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + gameState.getPlayer("player1").getMaxHealth());
        assertEquals(5, currentHP);

        // Create a decision to use the potion
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.EQUIP);
        decision.setIndex(2);


        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        // The player should now have a potion effect lasting a total of 10 rounds, healing 2 HP per round
        // do turn should have applied the effect once, leaving the turnsLeft at 9, and player1's HP at 7

        // Check that the player now has 7 HP
        currentHP = gameState.getPlayer("player1").getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + gameState.getPlayer("player1").getMaxHealth());
        assertEquals(7, currentHP);

        // Moves the game forward one turn without the player doing anything.
        decision.setDecisionType(CharacterProtos.DecisionType.NONE);
        GameLogic.doTurn(gameState, decisionMap);

        // Check that the player now has 9 HP
        currentHP = gameState.getPlayer("player1").getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + gameState.getPlayer("player1").getMaxHealth());
        assertEquals(9, currentHP);

        // Steps forward 5 more turns for a total of 7 turns of healing. Default consumable has a duration of 5,
        // so this should make the HP 19, as that was doubled to 10 turns.
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);

        currentHP = gameState.getPlayer("player1").getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + gameState.getPlayer("player1").getMaxHealth());
        assertEquals(19, currentHP);

        // Two more turns, and the new HP should have capped at the max of 20
        GameLogic.doTurn(gameState, decisionMap);
        GameLogic.doTurn(gameState, decisionMap);

        currentHP = gameState.getPlayer("player1").getCurrentHealth();
        System.out.println("Current HP: " + currentHP + "/" + gameState.getPlayer("player1").getMaxHealth());
        assertEquals(20, currentHP);
    }

    @Test
    public void ShoesBoostEffect() {
        // gives player1 a hat and equips it.
        Player player1 = gameState.getPlayer("player1");
        player1.setInventory(1, new Hat(new StatusModifier(0,
                1, 0, 1, 0, 0,
                0, 0, 0, 0, 0),
                HatEffect.SHOES_BOOST));
        player1.setInventory(2, Shoes.createDefaultShoes());
        player1.equipItem(1);
        player1.equipItem(2);

        // tests to see that the player's speed is 15 (base of 5 plus 2 * 5 from default shoes)
        assertEquals(15, p1.getSpeed());
    }

    @Test
    public void WeaponBoostEffect() {
        // gives player1 a hat and equips it.
        Player player1 = gameState.getPlayer("player1");
        player1.setInventory(1, new Hat(new StatusModifier(0,
                1, 0, 1, 0, 1,
                0, 1, 0, 1, 0),
                HatEffect.WEAPON_BOOST));
        player1.setInventory(2, Weapon.createStrongerDefaultWeapon());
        player1.equipItem(1);
        player1.equipItem(2);

        // tests to see that the player's speed is 15 (base of 0 plus 5 + 5 * 0.5 from stronger default weapon)
        assertEquals(7, p1.getAttack());
    }

    @Test
    public void ClothesBoostEffect() {
        // gives player1 a hat and equips it.
        Player player1 = gameState.getPlayer("player1");
        player1.setInventory(1, new Hat(new StatusModifier(0,
                1, 0, 1, 0, 1,
                0, 1, 0, 1, 0),
                HatEffect.CLOTHES_BOOST));
        player1.setInventory(2, Clothes.createDefaultClothes());
        player1.equipItem(1);
        player1.equipItem(2);

        // tests to see that the player's speed is 15 (base of 0 plus 2 * 8 from default clothes)
        assertEquals(16, p1.getDefense());
    }

    @Test
    public void TripledOnHitEffect() {
        // gives player1 a hat and weapon.
        Player player1 = gameState.getPlayer("player1");
        player1.setInventory(1, new Hat(new StatusModifier(0,
                1, 0, 1, 0, 1,
                0, 1, 0, 1, 0),
                HatEffect.TRIPLED_ON_HIT));
        player1.setInventory(2, Weapon.createStrongerDefaultWeapon());
        player1.equipItem(1);
        player1.equipItem(2);

        // Create a decision to attack player2
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.ATTACK);

        CharacterProtos.Position.Builder newPos = CharacterProtos.Position.newBuilder();
        Position p2Position = gameState.getPlayer("player2").getPosition();
        newPos.setX(p2Position.getX()).setY(p2Position.getY()).setBoardId(p2Position.getBoardID());
        decision.setTargetPosition(newPos.build());

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);

        Player player2 = gameState.getPlayer("player2");
        // player2 starts with 20 health and gets hit with three effects, each causing 5 damage per turn, leaving 5 hp.
        assertEquals(20, player2.getCurrentHealth());

        PlayerProtos.PlayerDecision.Builder emptyDecision = PlayerProtos.PlayerDecision.newBuilder();
        emptyDecision.setDecisionType(CharacterProtos.DecisionType.NONE);
        HashMap<String, PlayerProtos.PlayerDecision> emptyDecisionMap = new HashMap<>();
        emptyDecisionMap.put("player1", emptyDecision.build());
        emptyDecisionMap.put("player2", emptyDecision.build());
        GameLogic.doTurn(gameState, emptyDecisionMap);

        // player2 starts with 20 health and gets hit with three effects, each causing 5 damage per turn, leaving 5 hp.
        assertEquals(5, player2.getCurrentHealth());
    }
}
