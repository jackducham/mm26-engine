package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.VisualizerProtos.GameChange;

/**
 * Uses AWS to store GameStates
 */
public class RepositoryAws implements RepositoryAbstract {
    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final GameChange visualizerChange) {
        return 0;
    }

    @Override
    public void reset() {

    }
}
