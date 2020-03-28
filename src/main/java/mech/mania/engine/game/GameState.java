package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class GameState {
    private int turnNumber;
    private Board pvpBoard;
    private ArrayList<Board> playerBoards;

    public Board getPvpBoard() {
        return pvpBoard;
    }

    public Board getBoard(String boardID){
        if(pvpBoard.getBoardID().equals(boardID)){return pvpBoard;}
        for(Board b : playerBoards) {
            if (b.getBoardID().equals(boardID)) {
                return b;
            }
        }
        return null;
    }
}