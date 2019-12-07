package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.List;
import java.util.UUID;

public interface GameStateDao {

    GameState getGameState();

    VisualizerTurnProtos.VisualizerTurn getVisualizerTurn();

    List<GameState> getAllGameStates();

    List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns();

    int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn);

    int storeGameState(GameState gameState);

    int storePlayerDecision(UUID player, PlayerDecisionProtos.PlayerDecision turn);

    int storePlayerTurn(PlayerTurnProtos.PlayerTurn playerTurn);
}
