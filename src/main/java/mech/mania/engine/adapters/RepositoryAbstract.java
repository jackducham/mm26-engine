package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.VisualizerProtos;


public interface RepositoryAbstract {

    int storeGameState(final int turn, final GameState gameState);

    int storeVisualizerChange(final int turn, final VisualizerProtos.GameChange visualizerChange);

    void reset();
}
