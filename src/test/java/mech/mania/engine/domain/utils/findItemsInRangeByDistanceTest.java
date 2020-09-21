package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.Item;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class findItemsInRangeByDistanceTest {
    private GameState gameState = GameState.createDefaultGameState();

    @Test
    public void testItemsInRange() {
        Weapon weapon1 = Weapon.createDefaultWeapon();
        gameState.getBoard("pvp").getGrid()[0][3].getItems().add(weapon1);

        Weapon weapon2 = Weapon.createStrongerDefaultWeapon();
        gameState.getBoard("pvp").getGrid()[0][14].getItems().add(weapon2);

        Weapon weapon3 = Weapon.createStrongerDefaultWeapon();
        gameState.getBoard("pvp").getGrid()[0][15].getItems().add(weapon3);

        List<AbstractMap.SimpleEntry<Item, Position>> itemsInRange = utils.findItemsInRangeByDistance(gameState, gameState.getPlayer("player1").getPosition(), "player1", 100);
        List<Item> ans = new ArrayList<>();
        ans.add(weapon1);
        ans.add(weapon2);
        assertEquals(itemsInRange.size(), ans.size());
        for (int i = 0; i < itemsInRange.size(); i++) {
            assertEquals(itemsInRange.get(i).getKey(), ans.get(i));
        }
    }
}
