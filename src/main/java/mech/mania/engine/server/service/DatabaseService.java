package mech.mania.engine.server.service;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.dao.Database;

import java.util.Date;
import java.util.List;

public class DatabaseService {
    private final Database database;

    public DatabaseService(Database database) {
        this.database = database;
    }

    /**
     * Store a GameState
     *
     * @param turn      turn to store for
     * @param gameState GameState to store
     * @return 1 if fail, 0 if success
     */
    public int storeGameState(final int turn, final GameState gameState) {
        return database.storeGameState(turn, gameState);
    }

    /**
     * Store a VisualizerTurn
     * @param turn turn to store for
     * @param visualizerTurn VisualizerTurn to store
     * @return 1 if fail, 0 if success
     */
    public int storeVisualizerTurn(final int turn, final VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return database.storeVisualizerTurn(turn, visualizerTurn);
    }

    /**
     * Log the date of the turn, for retrieving turn by date later
     * @param turn turn to log
     * @param date date of turn
     * @return 1 if fail, 0 if success
     */
    public int logTurnDate(final int turn, final Date date) {
        return database.logTurnDate(turn, date);
    }

    /**
     * Retrieve list of VisualizerTurns
     * @return List of VisualizerTurn
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return database.getVisualizerTurns();
    }
}
