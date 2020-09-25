package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.characters.CharacterDecision;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.model.CharacterProtos;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PortalTests {
    private GameState gameState;

    @Before
    public void setup(){
        gameState = GameState.createDefaultGameState();
    }

    public void takePortal(Player p){
        CharacterDecision decision = new CharacterDecision(CharacterDecision.decisionTypes.PORTAL, null, 0);
        Map<String, CharacterProtos.CharacterDecision> decisionMap = new HashMap<>();
        decisionMap.put(p.getName(), decision.buildProtoClassCharacterDecision());

        GameLogic.doTurn(gameState, decisionMap);
    }

    @Test
    public void testPortal(){
        Player p1 = gameState.getPlayer("player1");
        Board p1Board = gameState.getBoard(p1.getPosition().getBoardID());

        p1.setPosition(p1Board.getPortals().get(0));
        takePortal(p1);
        assertEquals(gameState.getPvpBoard().getPortals().get(0), p1.getPosition());
    }
}
