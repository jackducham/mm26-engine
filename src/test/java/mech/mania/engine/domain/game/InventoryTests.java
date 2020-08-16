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
import org.junit.Assert;
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
    Player p1;
    Weapon defaultWeapon = Weapon.createDefaultWeapon();
    Weapon strongerWeapon = Weapon.createStrongerDefaultWeapon();
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
        p1Tile.addItem(strongerWeapon);
        p1 = gameState.getPlayer("player1");
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
        assertEquals(4, p1Tile.getItems().size());
        for (Item item : p1.getInventory()) {
            assertNull(item);
        }
        assertNull(p1.getShoes());
        assertNull(p1.getWeapon());
        assertNull(p1.getHat());
        assertNull(p1.getClothes());
    }

    // ============================= DECISION FUNCTIONS ========================================
    public void pickupItem(int index) {
        // pick up item from tile
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.PICKUP);
        decision.setIndex(index);

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);
    }

    public void equipItem(int index) {
        // equip item from inventory
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.EQUIP);
        decision.setIndex(index);

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);
    }

    public void dropItem(int index) {
        // drop item from inventory
        PlayerProtos.PlayerDecision.Builder decision = PlayerProtos.PlayerDecision.newBuilder();
        decision.setDecisionType(CharacterProtos.DecisionType.DROP);
        decision.setIndex(index);

        // Execute decision
        HashMap<String, PlayerProtos.PlayerDecision> decisionMap = new HashMap<>();
        decisionMap.put("player1", decision.build());
        GameLogic.doTurn(gameState, decisionMap);
    }

    // ============================= PICKUP TESTS ========================================
    @Test
    public void pickupValidItemIndex() {
        pickupItem(0);
        assertEquals(defaultWeapon, gameState.getPlayer("player1").getInventory()[0]);
        assertEquals(3, p1Tile.getItems().size());
    }

    @Test
    public void pickupInvalidIndices() {
        pickupItem(-1);
        pickupItem(4);
        pickupItem(8);
        pickupItem(300);
        assertNull(gameState.getPlayer("player1").getInventory()[0]);
        assertEquals(4, p1Tile.getItems().size());
    }

    @Test
    public void itemIsRemovedFromTile() {
        pickupItem(0);
        List<Item> tileItems = p1Tile.getItems();

        for (Item item: tileItems) {
            assertNotEquals(defaultWeapon, item);
        }
        assertEquals(3, tileItems.size());
    }

    @Test
    public void pickupMultipleItems() {
        // index is always 0 because items shift forward when picked up
        pickupItem(0);
        pickupItem(0);
        pickupItem(0);
        pickupItem(0);
        assertEquals(0, p1Tile.getItems().size());

        for (int i = 0; i < 4; i++) {
            assertNotNull(p1.getInventory()[i]);
        }
    }

    // ============================= EQUIP FUNCTIONS ========================================
    @Test
    public void equipWeaponValid() {
        pickupItem(0);
        equipItem(0);
        assertEquals(defaultWeapon, p1.getWeapon());
    }

    @Test
    public void equipClothesValid() {
        pickupItem(1);
        equipItem(0);
        assertEquals(defaultClothes, p1.getClothes());
    }

    @Test
    public void equipShoesValid() {
        pickupItem(2);
        equipItem(0);
        assertEquals(defaultShoes, p1.getShoes());
    }

    @Test
    public void equipNullItem() {
        equipItem(0);
        assertNull(p1.getShoes());
        assertNull(p1.getWeapon());
        assertNull(p1.getHat());
        assertNull(p1.getClothes());
    }

    @Test
    public void equipInvalidIndices() {
        equipItem(-1);
        equipItem(16);
        equipItem(300);
        assertNull(p1.getShoes());
        assertNull(p1.getWeapon());
        assertNull(p1.getHat());
        assertNull(p1.getClothes());
    }

    @Test
    public void equipFromMultipleItems() {
        pickupItem(0);
        pickupItem(0);
        pickupItem(0);
        pickupItem(0);
        assertEquals(0, p1Tile.getItems().size());

        equipItem(0);
        assertEquals(defaultWeapon, p1.getWeapon());
        equipItem(1);
        assertEquals(defaultClothes, p1.getClothes());
    }

    @Test
    public void replaceValidWeapon() {
        // pick up first weapon - should be in inventory, decrease tile items
        pickupItem(0);
        assertEquals(defaultWeapon, p1.getInventory()[0]);
        assertEquals(3, p1Tile.getItems().size());
        assertNull(p1.getWeapon());

        // equip weapon - should empty inventory, and weapon should be equipped
        equipItem(0);
        assertNull(p1.getInventory()[0]);
        assertEquals(defaultWeapon, p1.getWeapon());

        // pick up stronger weapon - should be in inventory, decrease tile items, and old weapon should still be equipped
        pickupItem(2);
        assertEquals(strongerWeapon, p1.getInventory()[0]);
        assertEquals(2, p1Tile.getItems().size());
        assertEquals(defaultWeapon, p1.getWeapon());

        // equip stronger weapon - should switch out default and stronger weapon
        equipItem(0);
        assertEquals(defaultWeapon, p1.getInventory()[0]);
        assertEquals(strongerWeapon, p1.getWeapon());
        assertEquals(2, p1Tile.getItems().size());
    }

    @Test
    public void replaceInvalidWeapon() {
        // pick up first weapon - should be in inventory, decrease tile items
        pickupItem(0);
        equipItem(0);

        // shouldn't change anything
        equipItem(0);
        equipItem(-1);
        assertNull(p1.getInventory()[0]);
        assertEquals(defaultWeapon, p1.getWeapon());
    }

    @Test
    public void pickupMultipleItemsAndEquip() {
        // pick up first weapon - should be in inventory, decrease tile items
        pickupItem(0);
        pickupItem(0);
        pickupItem(0);
        pickupItem(0);

        equipItem(0);
        equipItem(1); // fails here
        assertEquals(defaultWeapon, p1.getWeapon());
        assertEquals(defaultClothes, p1.getClothes());
    }
}