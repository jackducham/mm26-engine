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
     * Tests MOVE decision (moves player from 0, 0 to 1, 0)
     */
    @Test
    public void usePotion(){
        // gives player1 a hat, equips it, and give them a potion.
        Player player1 = gameState.getPlayer("player1");
        player1.setInventory(1, new Hat(new StatusModifier(0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0),
                HatEffect.LINGERING_POTIONS));
        player1.equipItem(1);
        player1.setInventory(2, Consumable.createDefaultConsumable());
        player1.applyDamage("player2", 15);



        // Check that the player starts with 5 hp
        int currentHP = gameState.getPlayer("player1").getCurrentHealth();
        System.out.println("Current HP: " + currentHP);
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
        System.out.println("Current HP: " + currentHP);
        assertEquals(7, currentHP);
    }
}
