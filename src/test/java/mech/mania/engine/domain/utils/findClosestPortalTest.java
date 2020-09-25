package mech.mania.engine.domain.utils;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.StatusModifier;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class findClosestPortalTest {
    private GameState gameState;

    @Before
    public void setup(){
        gameState = GameState.createDefaultGameState();
    }

    @Test
    public void testFindClosestPortalPlayer1() {
        Position portal = utils.findClosestPortal(gameState, gameState.getPlayer("player1").getPosition());
        assertEquals(portal, new Position(14, 1, "pvp"));
    }

    @Test
    public void testFindClosestPortalPlayer2() {
        Position portal = utils.findClosestPortal(gameState, gameState.getPlayer("player2").getPosition());
        assertEquals(portal, new Position(10, 14, "pvp"));
    }
}