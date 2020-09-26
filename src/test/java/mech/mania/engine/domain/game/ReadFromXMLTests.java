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

import java.util.ArrayList;
import java.util.List;

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
                    "spBoard"
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
                    "mpBoard"
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

        // Check that monsters have fields they need
        spMonsters = spReader.extractMonsters();
        assertNotEquals(0, spMonsters.get(0).getAggroRange());
        assertNotEquals("", spMonsters.get(0).getSprite());

        // Check that board conversion to protos works
        Board newBoard = new Board(spBoard.buildProtoClass());
        boolean success = true;
        for(int x = 0; x < spBoard.getGrid().length; x++){
            for(int y = 0; y < spBoard.getGrid()[x].length; y++){
                success = success && (spBoard.getGrid()[x][y].groundSprite.equals(newBoard.getGrid()[x][y].groundSprite));
            }
        }
        assertTrue(success);
    }

    @Test
    public void testAddBoardToGameState(){
        GameState gameState = GameState.createDefaultGameState();
        gameState.addBoardFromXML(spReader);
        assertTrue(gameState.getAllBoards().containsKey("spBoard"));
        assertTrue(gameState.getMonstersOnBoard("spBoard").size() != 0);
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

        // Check that board conversion to protos works
        Board newBoard = new Board(mpBoard.buildProtoClass());
        assertEquals(mpBoard.getGrid().length, newBoard.getGrid().length);
        assertEquals(mpBoard.getGrid()[0].length, newBoard.getGrid()[0].length);

        for(int x = 0; x < mpBoard.getGrid().length; x++){
            for(int y = 0; y < mpBoard.getGrid()[x].length; y++){
                assertEquals("Incorrect translation at " + x + ", " + y + "", mpBoard.getGrid()[x][y].groundSprite, newBoard.getGrid()[x][y].groundSprite);
            }
        }
    }

    /**
     * Tests that portal sprites are loaded correctly on the single-player board
     */
    @Test
    public void testPortalSpriteParsingSPBoard(){
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
     * Tests that all tiles have at least one of ground_sprite and above_sprite set in SP Board
     */
    @Test
    public void testSpriteLayersSPBoard(){
        // Testing SP board
        spBoard = spReader.extractBoard();
        assertNotNull(spBoard);

        for(int x = 0; x < spBoard.getGrid().length; x++){
            for(int y = 0; y < spBoard.getGrid()[x].length; y++){
                Tile tile = spBoard.getGrid()[x][y];

                // Check that non-VOID tiles have a sprite
                assertFalse(
                        String.format("Empty sprites at (%d, %d)", x, y),

                        tile.getType() != Tile.TileType.VOID
                        && tile.groundSprite.equals("")
                        && tile.aboveSprite.equals("")
                );
            }
        }
    }

    /**
     * Tests that all tiles have at least one of ground_sprite and above_sprite set in MP Board
     */
    @Test
    public void testSpriteLayersMPBoard(){
        // Testing SP board
        mpBoard = mpReader.extractBoard();
        assertNotNull(mpBoard);

        for(int x = 0; x < mpBoard.getGrid().length; x++){
            for(int y = 0; y < mpBoard.getGrid()[x].length; y++){
                Tile tile = mpBoard.getGrid()[x][y];

                // Check that non-VOID tiles have a sprite
                assertFalse(
                        String.format("Empty sprites at (%d, %d)", x, y),

                        tile.getType() != Tile.TileType.VOID
                                && tile.groundSprite.equals("")
                                && tile.aboveSprite.equals("")
                );
            }
        }
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
        for(Monster m: spMonsters){
            assertTrue(m.getLevel() > 0);
        }
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

    @Test
    public void testCopyConstructor(){
        spBoard = spReader.extractBoard();
        Board spBoard2 = new Board(spBoard);

        for(int x = 0; x < spBoard2.getGrid().length; x++){
            for(int y = 0; y < spBoard2.getGrid()[x].length; y++){
                assertNotSame(spBoard2.getGrid()[x][y], spBoard.getGrid()[x][y]);
            }
        }

        for(int i = 0; i < spBoard2.getPortals().size(); i++){
            assertNotSame(spBoard2.getPortals().get(i), spBoard.getPortals().get(i));
        }
    }
}
