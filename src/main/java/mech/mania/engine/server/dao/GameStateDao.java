package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.List;
import java.util.UUID;

public interface GameStateDao {

//    GameState getGameState();
//
//    VisualizerTurnProtos.VisualizerTurn getVisualizerTurn();
//
//    List<GameState> getAllGameStates();
//
//    List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns();

    int storeVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn);

    int storeGameState(int turn, GameState gameState);

    int storePlayerTurn(int turn, PlayerTurnProtos.PlayerTurn playerDecision);

    int storePlayerDecisions(int turn, List<PlayerDecisionProtos.PlayerDecision> playerDecisions);
}
