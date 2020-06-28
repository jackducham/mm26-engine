package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.model.PlayerInfo;
import mech.mania.engine.domain.model.VisualizerProtos.VisualizerChange;

import java.util.List;
import java.util.Map;


public interface AbstractRepository {

    int storeGameState(final int turn, final GameState gameState);

    int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange);

    void reset();
}
