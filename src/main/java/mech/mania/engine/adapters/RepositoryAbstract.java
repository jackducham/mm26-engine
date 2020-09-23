package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.util.logging.Logger;


public interface RepositoryAbstract {

    Logger LOGGER = Logger.getLogger("Repository");

    int storeCurrentTurn(final int turn);

    int storeGameState(final int turn, final GameState gameState);

    int storeGameChange(final int turn, final VisualizerProtos.GameChange gameChange);

    int storePlayerStatsBundle(final int turn, final CharacterProtos.PlayerStatsBundle playerStatsBundle);

    GameState getGameState(final int turn);

    void reset();
}
