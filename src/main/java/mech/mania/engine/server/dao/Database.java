package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.Date;
import java.util.List;

public interface Database {

    int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange);

    int storeGameState(final int turn, final GameState gameState);

    int logTurnDate(final int turn, final Date date);

    List<VisualizerChange> getVisualizerChanges();

    int turnBeforeDate(final Date date);
}
