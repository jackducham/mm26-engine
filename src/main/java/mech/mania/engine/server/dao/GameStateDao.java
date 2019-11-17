package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.List;
import java.util.UUID;

public interface GameStateDao {
    int insertGameState(UUID id, GameState gameState);

    default int insertGameState(GameState gameState) {
        UUID id = UUID.randomUUID();
        return insertGameState(id, gameState);
    }

    // Optional<GameState> getGameStateByUUID(UUID id);

    GameState getGameState();

    VisualizerTurnProtos.VisualizerTurn getVisualizerTurn();

    List<GameState> getAllGameStates();

    int storeGameState(GameState gameState);

    List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns();

    int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn);
}
