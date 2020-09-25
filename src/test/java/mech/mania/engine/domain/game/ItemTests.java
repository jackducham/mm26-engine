package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.items.Clothes;
import mech.mania.engine.domain.game.items.Item;
import mech.mania.engine.domain.game.items.Weapon;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ItemTests {
    private GameState gameState;

    @Before
    public void setup(){
        gameState = GameState.createDefaultGameState();
    }

    @Test
    public void testItemTimeout(){
        Clothes c = new Clothes(null, "");
        Tile t = gameState.getPvpBoard().getGrid()[0][0];
        t.addItem(c);

        for(int i = 0; i < Item.ITEM_LIFETIME + 1; i++){
            GameLogic.doTurn(gameState, Collections.emptyMap());
        }

        assertEquals(0, t.getItems().size());
    }
}
