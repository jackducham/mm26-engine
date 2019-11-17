package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Uses Redis to store GameStates
 */
@Repository("redis")
public class GameStateRedisDao implements GameStateDao {
    @Override
    public int insertGameState(UUID id, GameState gameState) {
        return 0;
    }

    @Override
    public GameState getGameState() {
        return null;
    }

    @Override
    public VisualizerTurnProtos.VisualizerTurn getVisualizerTurn() {
        return null;
    }

    @Override
    public List<GameState> getAllGameStates() {
        return null;
    }

    @Override
    public int storeGameState(GameState gameState) {
        return 0;
    }

    @Override
    public List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns() {
        return null;
    }

    @Override
    public int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return 0;
    }
}
