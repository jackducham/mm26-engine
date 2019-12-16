package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.List;
import java.util.Map;

public class GameState {
    private Board pvpBoard;
    private Map<String, Board> playerIdToBoardMap;
    private int number = 0;

    public int update(List<PlayerDecisionProtos.PlayerDecision> playerDecisions) {
        for (PlayerDecisionProtos.PlayerDecision playerDecision : playerDecisions) {
            int increment = playerDecision.getIncrement();
            if (isValid(increment)) {
                number += increment;
            } else {
                return 1;
            }
        }
        return 0;
    }

    private boolean isValid(int increment) {
        // TODO: change to GameState specific stuff
        return increment < 1e5;
    }
}