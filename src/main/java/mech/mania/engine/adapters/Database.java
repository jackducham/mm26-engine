package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.model.PlayerInfo;
import mech.mania.engine.domain.model.VisualizerProtos.VisualizerChange;

import java.util.List;
import java.util.Map;


public interface Database {

    enum PlayerExistence {
        PLAYER_EXISTS,
        PLAYER_DOES_NOT_EXIST
    }

    GameState getCurrentGameState();
    int storeGameState(final int turn, final GameState gameState);

    int getCurrentTurnNum();
    int updateCurrentTurnNum(int turn);

    Map<String, PlayerInfo> getPlayerInfoMap();
    PlayerExistence updatePlayerInfoMap(String playerName, String playerIp);

//    List<Message> getEvents();


    void reset();
}
