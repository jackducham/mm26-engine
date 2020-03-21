package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerInfo;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.Map;


public interface Database {

    enum PlayerExistence {
        PLAYER_EXISTS,
        PLAYER_DOES_NOT_EXIST
    }

    int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange);

    GameState getCurrentGameState();
    int storeGameState(final int turn, final GameState gameState);

    int getCurrentTurnNum();
    int updateCurrentTurnNum(int turn);

    Map<String, PlayerInfo> getPlayerInfoMap();
    PlayerExistence updatePlayerInfoMap(String playerName, String playerIp);

    void reset();
}
