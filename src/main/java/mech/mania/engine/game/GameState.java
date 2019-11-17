package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.Map;

public class GameState {
    private Board pvpBoard;
    private Map<String, Board> playerIdToBoardMap;

    /**
     * Use internal state to construct a VisualizerTurn
     * @return
     */
    public VisualizerTurnProtos.VisualizerTurn constructVisualizerTurn() {
        // TODO
        return null;
    }
}