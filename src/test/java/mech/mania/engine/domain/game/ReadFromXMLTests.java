package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.ReadBoardFromXMLFile;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.board.TileIDNotFoundException;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.model.ApiProtos;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.PlayerProtos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

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
            boardReader.updateBoardAndMonsters(
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_tileset.tsx",
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_sp_map.tmx",
                    "loadedBoard"
            );
        } catch (TileIDNotFoundException e) {
            System.err.print(e + "\n");
            fail();
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
        assertNotNull(loadedBoard);
        //check size of board
        assertEquals(30, loadedBoard.getGrid().length);
        assertEquals(30, loadedBoard.getGrid()[0].length);

        //check that tile walkability is loaded correctly
        assertEquals(Tile.TileType.BLANK, loadedBoard.getGrid()[2][2].getType());
        assertEquals(Tile.TileType.IMPASSIBLE, loadedBoard.getGrid()[11][14].getType());
    }

    /**
     * Tests that sprites are loaded correctly
     */
    @Test
    public void testSpriteParsing(){
        loadedBoard = boardReader.extractBoard();
        assertNotNull(loadedBoard);

        final String portalSprite = "mm26_tiles/magicportal.PNG";

        // Find portal
        assertTrue(loadedBoard.getPortals().size() > 0);
        Position portalPos = loadedBoard.getPortals().get(0);
        Tile portalTile = loadedBoard.getGrid()[portalPos.getX()][portalPos.getY()];

        // Assert correct TileType and aboveSprite
        assertEquals(Tile.TileType.PORTAL, portalTile.getType());
        assertEquals(portalSprite, portalTile.aboveSprite);

        // Assert that groundSprite isn't the portal sprite
        assertNotEquals(portalSprite, portalTile.groundSprite);
    }

    /**
     * Tests that the monsters are loaded correctly
     */
    @Test
    public void loadMonsters(){
        loadedMonsters = boardReader.extractMonsters();

        // It's hard to test with such a large map! We'll just check that some are loaded for now
        assertTrue(loadedMonsters.size() > 0);
    }
}
