package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;

import java.util.Map;
import java.util.UUID;

public class GameState {
    private Board pvpBoard;
    private Map<UUID, Board> playerIdToBoardMap;

    public Board getPvpBoard() {
        return pvpBoard;
    }

    public Board getBoard(String boardID){
        if(pvpBoard.getBoardID().equals(boardID)){return pvpBoard;}
        for(Board b : playerIdToBoardMap.values()) {
            if (b.getBoardID().equals(boardID)) {
                return b;
            }
        }
        return null;
    }
}