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
}