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
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_sample_tileset.tsx",
                    "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_sp_map.tmx",
                    "loadedBoard"
            );
        } catch (TileIDNotFoundException e) {
            System.err.print(e);
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
     * Tests that the monsters are loaded correctly
     */
    @Test
    public void loadMonsters(){
        loadedMonsters = boardReader.extractMonsters();
        assertEquals(1, loadedMonsters.size());
        assertEquals("0", loadedMonsters.get(0).getName());
        assertEquals(1, loadedMonsters.get(0).getSpeed());
        assertEquals(0, loadedMonsters.get(0).getLevel());
        assertEquals(1, loadedMonsters.get(0).getMaxHealth());
        assertEquals(0, loadedMonsters.get(0).getDefense());
        assertEquals(1, loadedMonsters.get(0).getAttack());
        assertEquals(0, loadedMonsters.get(0).getWeapon().getRange());
        assertEquals(0, loadedMonsters.get(0).getWeapon().getAttack());
        assertEquals(0, loadedMonsters.get(0).getWeapon().getSplashRadius());
        assertEquals(0, loadedMonsters.get(0).getWeapon().getOnHitEffect().getFlatAttackChange());
    }
}
