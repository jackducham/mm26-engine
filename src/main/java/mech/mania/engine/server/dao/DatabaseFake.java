package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.infra.InfraRESTHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.*;
import java.util.logging.Logger;

/**
 * Use regular data structures to store data (for now).
 */
public class DatabaseFake implements Database {
    
    private static final Logger LOGGER = Logger.getLogger( DatabaseFake.class.getName() );

    private Map<Integer, GameState> gameStates = new HashMap<>();
    private Map<Integer, VisualizerChange> visualizerChanges = new HashMap<>();
    private Map<Integer, Date> dates = new HashMap<>();

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        LOGGER.fine("Logging GameState for turn " + turn + ", GameState: " + gameState.toString());
        gameStates.put(turn, gameState);
        LOGGER.fine(gameStates.size() + " GameStates stored currently");
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange) {
        LOGGER.fine("Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + visualizerChange.toString());
        visualizerChanges.put(turn, visualizerChange);
        LOGGER.fine(visualizerChanges.size() + " VisualizerTurns stored currently");
        return 0;
    }

    @Override
    public int logTurnDate(int turn, Date date) {
        LOGGER.fine("Logging date for turn " + turn + ", date: " + date.toString());
        dates.put(turn, date);
        LOGGER.fine(dates.size() + " dates stored currently");
        return 0;
    }

    @Override
    public int turnBeforeDate(final Date date) {

        return 0;
    }

}
