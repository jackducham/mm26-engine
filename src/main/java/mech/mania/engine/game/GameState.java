package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;

import java.util.Map;

public class GameState {
    private Board pvpBoard;
    private Map<String, Board> playerIdToBoardMap;
}