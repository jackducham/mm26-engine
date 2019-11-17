package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.List;
import java.util.UUID;

public interface GameStateDao {


    // Optional<GameState> getGameStateByUUID(UUID id);

    GameState getGameState();

    VisualizerTurnProtos.VisualizerTurn getVisualizerTurn();

    List<GameState> getAllGameStates();

    List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns();

    int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn);

    int storeGameState(UUID id, GameState gameState);

    int storePlayerTurn(UUID player, PlayerTurnProtos.PlayerTurn turn);
}
