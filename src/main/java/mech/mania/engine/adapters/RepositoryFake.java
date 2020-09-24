package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Use regular data structures to store data (for now).
 */
public class RepositoryFake implements RepositoryAbstract {

    private static final Logger LOGGER = Logger.getLogger( RepositoryFake.class.getName() );

    private int currentTurn;
    private final Map<Integer, GameState> gameStates = new HashMap<>();
    private final Map<Integer, VisualizerProtos.GameChange> visualizerChanges = new HashMap<>();
    private final Map<Integer, CharacterProtos.PlayerStatsBundle> playerStatsBundles = new HashMap<>();

    @Override
    public int storeCurrentTurn(int turn) {
        LOGGER.fine("Setting current turn to " + turn);
        currentTurn = turn;
        return 0;
    }

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        LOGGER.fine("Logging GameState for turn " + turn + ", GameState: " + gameState.toString());
        gameStates.put(turn, gameState);
        LOGGER.fine(gameStates.size() + " GameStates stored currently");
        return 0;
    }

    @Override
    public int storeGameChange(final int turn, final VisualizerProtos.GameChange gameChange) {
        LOGGER.fine("Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + gameChange.toString());
        visualizerChanges.put(turn, gameChange);
        LOGGER.fine(visualizerChanges.size() + " VisualizerTurns stored currently");
        return 0;
    }

    @Override
    public int storePlayerStatsBundle(int turn, CharacterProtos.PlayerStatsBundle playerStatsBundle) {
        LOGGER.fine("Logging PlayerStatsBundles for turn " + turn + ", PlayerStatsBundles: " + playerStatsBundle.toString());
        playerStatsBundles.put(turn, playerStatsBundle);
        LOGGER.fine(visualizerChanges.size() + " VisualizerTurns stored currently");
        return 0;
    }

    @Override
    public GameState getGameState(int turn) {
        LOGGER.warning("Attempting to get stored GameState from fake repository.");
        return gameStates.get(turn);
    }

    @Override
    public void reset() {
        currentTurn = 0;
        playerStatsBundles.clear();
        gameStates.clear();
        visualizerChanges.clear();
    }
}
