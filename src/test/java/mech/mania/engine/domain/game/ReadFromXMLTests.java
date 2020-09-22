package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.ReadBoardFromXMLFile;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.board.TileIDNotFoundException;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

public class ReadFromXMLTests {

    private Board spBoard, mpBoard;
    private List<Monster> spMonsters, mpMonsters;
    private ReadBoardFromXMLFile spReader, mpReader;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        spReader = new ReadBoardFromXMLFile();
        try {
            spReader.updateBoardAndMonsters(
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_tileset.tsx",
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_sp_map.tmx",
                    "loadedBoard"
            );
        } catch (TileIDNotFoundException e) {
            System.err.print(e + "\n");
            fail();
        }

        mpReader = new ReadBoardFromXMLFile();
        try {
            mpReader.updateBoardAndMonsters(
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_tileset.tsx",
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_mp_map.tmx",
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
     * Tests that the single-player board is loaded correctly
     */
    @Test
    public void loadSPBoard(){
        spBoard = spReader.extractBoard();
        assertNotNull(spBoard);
        //check size of board
        assertEquals(30, spBoard.getGrid().length);
        assertEquals(30, spBoard.getGrid()[0].length);

        //check that tile walkability is loaded correctly
        assertEquals(Tile.TileType.BLANK, spBoard.getGrid()[2][2].getType());
        assertEquals(Tile.TileType.IMPASSIBLE, spBoard.getGrid()[11][14].getType());
    }

    /**
     * Tests that the multi-player board is loaded correctly
     */
    @Test
    public void loadMPBoard(){
        mpBoard = mpReader.extractBoard();
        assertNotNull(mpBoard);
        //check size of board
        assertEquals(50, mpBoard.getGrid().length);
        assertEquals(60, mpBoard.getGrid()[0].length);

        //check that tile walkability is loaded correctly
        assertEquals(Tile.TileType.BLANK, mpBoard.getGrid()[16][6].getType());
        assertEquals(Tile.TileType.IMPASSIBLE, mpBoard.getGrid()[10][20].getType());
    }

    /**
     * Tests that sprites are loaded correctly on the single-player board
     */
    @Test
    public void testSpriteParsingSPBoard(){
        spBoard = spReader.extractBoard();
        assertNotNull(spBoard);

        final String portalSprite = "mm26_tiles/magicportal.PNG";

        // Find portal
        assertTrue(spBoard.getPortals().size() > 0);
        Position portalPos = spBoard.getPortals().get(0);
        Tile portalTile = spBoard.getGrid()[portalPos.getX()][portalPos.getY()];

        // Assert correct TileType and aboveSprite
        assertEquals(Tile.TileType.PORTAL, portalTile.getType());
        assertEquals(portalSprite, portalTile.aboveSprite);

        // Assert that groundSprite isn't the portal sprite
        assertNotEquals(portalSprite, portalTile.groundSprite);
    }

    /**
     * Tests that sprites are loaded correctly on the multi-player board
     */
    @Test
    public void testSpriteParsingMPBoard(){
        mpBoard = mpReader.extractBoard();
        assertNotNull(mpBoard);

        final String portalSprite = "mm26_tiles/magicportal.PNG";

        // Find portal
        assertTrue(mpBoard.getPortals().size() > 0);
        Position portalPos = mpBoard.getPortals().get(0);
        Tile portalTile = mpBoard.getGrid()[portalPos.getX()][portalPos.getY()];

        // Assert correct TileType and aboveSprite
        assertEquals(Tile.TileType.PORTAL, portalTile.getType());
        assertEquals(portalSprite, portalTile.aboveSprite);

        // Assert that groundSprite isn't the portal sprite
        assertNotEquals(portalSprite, portalTile.groundSprite);
    }

    /**
     * Tests that the monsters are loaded correctly on the single-player board
     */
    @Test
    public void loadMonstersSPBoard(){
        spMonsters = spReader.extractMonsters();

        // It's hard to test with such a large map! We'll just check that some are loaded for now
        assertTrue(spMonsters.size() > 0);
    }

    /**
     * Tests that the monsters are loaded correctly on the multi-player board
     */
    @Test
    public void loadMonstersMPBoard(){
        mpMonsters = mpReader.extractMonsters();

        // It's hard to test with such a large map! We'll just check that some are loaded for now
        assertTrue(mpMonsters.size() > 0);
    }
}
