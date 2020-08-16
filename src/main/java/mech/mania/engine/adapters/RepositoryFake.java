package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Use regular data structures to store data (for now).
 */
public class RepositoryFake implements RepositoryAbstract {

    private static final Logger LOGGER = Logger.getLogger( RepositoryFake.class.getName() );

    private final Map<Integer, GameState> gameStates = new HashMap<>();
    private final Map<Integer, VisualizerProtos.GameChange> visualizerChanges = new HashMap<>();

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        LOGGER.fine("Logging GameState for turn " + turn + ", GameState: " + gameState.toString());
        gameStates.put(turn, gameState);
        LOGGER.fine(gameStates.size() + " GameStates stored currently");
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final VisualizerProtos.GameChange visualizerChange) {
        LOGGER.fine("Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + visualizerChange.toString());
        visualizerChanges.put(turn, visualizerChange);
        LOGGER.fine(visualizerChanges.size() + " VisualizerTurns stored currently");
        return 0;
    }

    @Override
    public void reset() {
        gameStates.clear();
        visualizerChanges.clear();
    }
}
