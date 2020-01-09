package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface Database {

    int storeVisualizerTurn(final int turn, final VisualizerTurnProtos.VisualizerTurn visualizerTurn);

    int storeGameState(final int turn, final GameState gameState);

    int logTurnDate(final int turn, final Date date);

    List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns();

    int turnBeforeDate(final Date date);
}
