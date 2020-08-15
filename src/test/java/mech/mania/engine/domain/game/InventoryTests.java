package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Clothes;
import mech.mania.engine.domain.game.items.Item;
import mech.mania.engine.domain.game.items.Shoes;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.model.CharacterProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import mech.mania.engine.domain.model.PlayerProtos;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/** This contains tests for any overall board tests or helper functions */
public class InventoryTests {
    private GameState gameState;
    Tile p1Tile;
    Weapon defaultWeapon = Weapon.createDefaultWeapon();
    Clothes defaultClothes = Clothes.createDefaultClothes();
    Shoes defaultShoes = Shoes.createDefaultShoes();

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        gameState = GameState.createDefaultGameState();
        p1Tile = gameState.getBoard("pvp").getGrid()[0][4];
        p1Tile.addItem(defaultWeapon);
        p1Tile.addItem(defaultClothes);
        p1Tile.addItem(defaultShoes);
    }

    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {
    }

    /**
     * Test initial player addition and spawn location
     */
    @Test
    public void gameInit(){

    }

    public void pickupItem(int index) {
        // pick up weapon from tile
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.PICKUP);
        decision.setIndex(index);

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);
    }

    public void equipItem(int index) {
        // pick up weapon from tile
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.EQUIP);
        decision.setIndex(index);

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);
    }

    @Test
    public void pickupValidItemIndex() {
        pickupItem(0);
        assertEquals(defaultWeapon, gameState.getPlayer("player1").getInventory()[0]);
    }

    @Test
    public void pickupInvalidItemIndex() {
        pickupItem(-1);
        pickupItem(4);
        assertNull(gameState.getPlayer("player1").getInventory()[0]);
    }

    @Test
    public void itemIsRemovedFromTile() {
        pickupItem(0);
        List<Item> tileItems = p1Tile.getItems();

        for (Item item: tileItems) {
            assertNotEquals(defaultWeapon, item);
        }
        assertEquals(2, tileItems.size());
    }

    @Test
    public void equipWeaponValid() {
        pickupItem(0);
        equipItem(0);
        Player p1 = gameState.getPlayer("player1");
        assertEquals(defaultWeapon, p1.getWeapon());
    }

    @Test
    public void equipClothesValid() {
        pickupItem(1);
        equipItem(0);
        Player p1 = gameState.getPlayer("player1");
        assertEquals(defaultClothes, p1.getClothes());
    }

    @Test
    public void equipShoesValid() {
        pickupItem(2);
        equipItem(0);
        Player p1 = gameState.getPlayer("player1");
        assertEquals(defaultShoes, p1.getShoes());
    }

    @Test
    public void equipItemInvalid() {
        equipItem(0);
        Player p1 = gameState.getPlayer("player1");
        assertNull(p1.getShoes());
        assertNull(p1.getWeapon());
        assertNull(p1.getHat());
        assertNull(p1.getClothes());
    }

}