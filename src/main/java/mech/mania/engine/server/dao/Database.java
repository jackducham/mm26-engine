package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;

import java.util.Date;
import java.util.List;

public interface Database {

    int storeVisualizerTurn(final int turn, final VisualizerTurn visualizerTurn);

    int storeGameState(final int turn, final GameState gameState);

    int logTurnDate(final int turn, final Date date);

    List<VisualizerTurn> getVisualizerTurns();

    int turnBeforeDate(final Date date);
}
