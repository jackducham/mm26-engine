package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Uses Redis to store GameStates
 */
public class DatabaseRedis implements Database {
    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        return 0;
    }

    @Override
    public int logTurnDate(int turn, Date date) {
        return 0;
    }

    @Override
    public int turnBeforeDate(final Date date) {
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange) {
        return 0;
    }
}
