package mech.mania.engine.domain.game.board;

import mech.mania.engine.domain.game.characters.Monster;

import java.util.List;
import java.util.logging.Logger;

public class BoardFactory {
    private static final Logger LOGGER = Logger.getLogger( BoardFactory.class.getName() );

    private static final ReadBoardFromXMLFile initHomeBoardReader = loadBoardReader(
            "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_tileset.tsx",
            "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_sp_map.tmx",
            "fake"
    );

    private static final ReadBoardFromXMLFile initPvpBoardReader = loadBoardReader(
                "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_tileset.tsx",
                "src/main/java/mech/mania/engine/domain/model/mm26_map/mm26_mp_map.tmx",
                "pvp"
    );

    /**
     * Creates a home board for a new player. Used by GameState when adding new players.
     * @param id the id of the player. required to correctly create the home board portal
     * @return a finished home board reader with default settings
     */
    public static ReadBoardFromXMLFile createHomeBoardReader(String id) {
        Board board = initHomeBoardReader.extractBoard();
        List<Monster> monsterList = initHomeBoardReader.extractMonsters();
        initHomeBoardReader.setBoardName(id);

        // Replace all positions with new id string
        for(int i = 0; i < board.getPortals().size(); i++){
            board.getPortals().get(i).setBoardID(id);
        }
        for(int i = 0; i < monsterList.size(); i++){
            monsterList.get(i).getPosition().setBoardID(id);
        }

        return initHomeBoardReader;
    }

    public static ReadBoardFromXMLFile createPvpBoardReader(){
        return initPvpBoardReader;
    }

    private static ReadBoardFromXMLFile loadBoardReader(String tileSetFileName, String mapDataFileName, String boardName){
        ReadBoardFromXMLFile boardReader = new ReadBoardFromXMLFile();
        try {
            boardReader.updateBoardAndMonsters(tileSetFileName, mapDataFileName, boardName);
        } catch (TileIDNotFoundException e) {
            LOGGER.warning("Exception while parsing board \"" + boardName + "\" XML: " + e);
        }
        return boardReader;
    }
}
