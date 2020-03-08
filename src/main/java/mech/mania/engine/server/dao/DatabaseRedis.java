package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerInfo;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.Map;

/**
 * Uses Redis to store GameStates
 */
public class DatabaseRedis implements Database {
    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange) {
        return 0;
    }

    @Override
    public int getCurrentTurnNum() {
        return 0;
    }

    @Override
    public int updateCurrentTurnNum(int turn) {
        return 0;
    }

    @Override
    public GameState getCurrentGameState() {
        return null;
    }

    @Override
    public Map<String, PlayerInfo> getPlayerInfoMap() {
        return null;
    }

    @Override
    public PlayerExistence updatePlayerInfoMap(String playerName, String playerIp) {
        return null;
    }

    @Override
    public void reset() {

    }
}
