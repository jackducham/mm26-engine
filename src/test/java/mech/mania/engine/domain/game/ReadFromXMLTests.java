package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.ReadBoardFromXMLFile;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.board.TileIDNotFoundException;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ReadFromXMLTests {

    private Board loadedBoard;
    private List<Monster> loadedMonsters;
    private ReadBoardFromXMLFile boardReader;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        boardReader = new ReadBoardFromXMLFile();
        try {
            boardReader.updateBoardAndMonsters("src/test/java/mech/mania/engine/domain/game/mm26_sample_tileset.tsx", "src/test/java/mech/mania/engine/domain/game/mm26_sample_map.tmx", "loadedBoard");
        } catch (TileIDNotFoundException e) {
            System.err.print(e);
        }
    }

    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {

    }

    /**
     * Tests that the board is loaded correctly
     */
    @Test
    public void loadBoard(){
        loadedBoard = boardReader.extractBoard();
        assertTrue(loadedBoard != null);
        assertEquals(30, loadedBoard.getGrid().length);
        assertEquals(30, loadedBoard.getGrid()[0].length);

        assertEquals(Tile.TileType.BLANK, loadedBoard.getGrid()[2][2].getType());
    }

    /**
     * Tests that the monsters are loaded correctly
     */
    @Test
    public void loadMonsters(){
        loadedMonsters = boardReader.extractMonsters();
    }
}
