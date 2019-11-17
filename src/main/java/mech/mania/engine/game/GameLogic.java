package mech.mania.engine.game;

import mech.mania.engine.server.communication.player.model.PlayerDecision;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn;

import java.util.List;

/**
 * A class to execute the game logic.
 */
public class GameLogic {
    /**
     * Executes the logic of one turn given a starting {@link GameState} and a list of {@link PlayerDecision}s.
     * @param gameState The initial game state.
     * @param decisions A list of player decisions.
     * @return the resulting {@link GameState}.
     */
    public static GameState doTurn(GameState gameState, List<PlayerDecision> decisions){
        VisualizerTurn.Builder visualizerTurn = VisualizerTurn.newBuilder();
        // TODO: do turn logic
        // TODO: update visualizerTurn
        VisualizerBinaryWebSocketHandler.sendTurn(visualizerTurn.build());
        return null;
    }
}
