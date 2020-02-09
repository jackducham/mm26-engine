package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;

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
    public int storeVisualizerTurn(final int turn, final VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return 0;
    }

    @Override
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return new ArrayList<>();
    }
}
