package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.List;

/**
 * Use regular data structures to store data (for now).
 */
public class GameStateFakeDao implements GameStateDao {

    private GameState[] gameStates;
    private PlayerTurnProtos.PlayerTurn[] playerTurns;
    private List<PlayerDecisionProtos.PlayerDecision>[] playerDecisions;
    private VisualizerTurnProtos.VisualizerTurn[] visualizerTurns;

    @Override
    public int storeGameState(int turn, GameState gameState) {
        gameStates[turn] = gameState;
        return 0;
    }

    @Override
    public int storePlayerTurn(int turn, PlayerTurnProtos.PlayerTurn playerTurn) {
        playerTurns[turn] = playerTurn;
        return 0;
    }

    @Override
    public int storePlayerDecisions(int turn, List<PlayerDecisionProtos.PlayerDecision> playerDecision) {
        playerDecisions[turn] = playerDecision;
        return 0;
    }

    @Override
    public int storeVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        visualizerTurns[turn] = visualizerTurn;
        return 0;
    }
}
