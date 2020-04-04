package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.model.GameStateProtos;

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

    public GameState(GameStateProtos.GameState gameStateProto) {
        this.pvpBoard = new Board(gameStateProto.getPvpBoard());
        playerBoards = new ArrayList<>(gameStateProto.getPlayerBoardsCount());
        for (int i = 0; i < gameStateProto.getPlayerBoardsCount(); i++) {
            playerBoards.set(i, new Board(gameStateProto.getPlayerBoards(i)));
        }
    }
}