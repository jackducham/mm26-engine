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

    /**
     * Update the GameState using PlayerDecision objects
     * @param playerDecisions List of PlayerDecisions to use to update
     * @return 1 if fail, 0 if success
     */
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

    /**
     * Checks the validity of parameters given by player
     * @param increment value to check
     * @return whether the parameter is valid or not
     */
    private boolean isValid(int increment) {
        // TODO: change to GameState specific stuff
        return increment < 1e5 && increment > 0;
    }

    @Override
    public String toString() {
        return String.format("GameState{number=%d}", number);
    }
}