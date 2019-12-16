package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.Map;

public class GameState {
    private Board pvpBoard;
    private Map<String, Board> playerIdToBoardMap;
    private int number = 0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}