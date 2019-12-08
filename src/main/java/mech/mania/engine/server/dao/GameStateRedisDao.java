package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
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
    public int storeGameState(GameState gameState) {
        return 0;
    }

    @Override
    public int storePlayerDecision(UUID player, PlayerDecisionProtos.PlayerDecision turn) {
        return 0;
    }

    @Override
    public int storePlayerTurns(int turn, List<PlayerTurnProtos.PlayerTurn> playerTurn) {
        return 0;
    }

    @Override
    public int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return 0;
    }
}
