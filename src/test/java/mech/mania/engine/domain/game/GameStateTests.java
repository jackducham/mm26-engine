package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.characters.*;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;
import mech.mania.engine.service_layer.UnitOfWorkFake;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameStateTests {

    private GameState gameState = GameState.createDefaultGameState();
    private Player p1;
    private Player p2;
    private Player p3;
    private Monster m1;
    private Monster m2;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        p1 = gameState.getPlayer("player1");
        p2 = gameState.getPlayer("player2");
        p3 = gameState.getPlayer("player3");
        m1 = gameState.getMonster("DefaultMonster0");
        m2 = gameState.getMonster("DefaultMonster1");
    }


    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {
    }


    @Test
    public void testHello() {
        UnitOfWorkAbstract uow = new UnitOfWorkFake();
    }

    @Test
    public void getInvalidBoard() {
        assertNull(gameState.getBoard("test"));
    }

    @Test
    public void getInvalidPlayer() {
        assertNull(gameState.getPlayer("test"));
    }

    @Test
    public void getInvalidMonster() {
        assertNull(gameState.getMonster("test"));
    }

    @Test
    public void getPlayers() {
        Map<String, Player> players = gameState.getAllPlayers();
        Map<String, Player> expected = new HashMap<>();
        expected.put("player1", p1);
        expected.put("player2", p2);
        expected.put("player3", p3);
        assertEquals(3, players.size());
        assertEquals(expected, players);
    }

    @Test
    public void getPlayersOnBoard() {
        List<Player> players = gameState.getPlayersOnBoard("pvp");
        List<Player> expected = new ArrayList<>(Arrays.asList(p1, p2));
        assertEquals(expected, players);
    }

    @Test
    public void getMonsters() {
        Map<String, Monster> monsters = gameState.getAllMonsters();
        assertEquals(2, monsters.size());
//        for (String key: monsters.keySet()) {
//            System.out.println(key);
//        }
    }

    @Test
    public void getMonstersOnBoard() {
        List<Monster> monsters = gameState.getMonstersOnBoard("pvp");
        assertEquals(1, monsters.size());
        assertEquals("pvp", monsters.get(0).getPosition().getBoardID());
    }

    @Test
    public void getCharacters() {
        Map<String, Character> characters = gameState.getAllCharacters();
        assertEquals(5, characters.size());
        for (String character: characters.keySet()) {
            System.out.println(character);
        }
    }

    @Test
    public void getCharactersOnBoard() {
        List<Character> characters = gameState.getCharactersOnBoard("pvp");
        assertEquals(3, characters.size());
        for (Character character: characters) {
            System.out.println(character.getName());
        }
    }
}
