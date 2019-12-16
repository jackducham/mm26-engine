package mech.mania.engine.server.service;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.GameStateDao;

import java.util.Date;
import java.util.List;

public class GameStateService {
    private final GameStateDao gameStateDao;

    public GameStateService(GameStateDao gameStateDao) {
        this.gameStateDao = gameStateDao;
    }

    /**
     * Store a GameState
     * @param gameState GameState to store
     * @return 1 if fail, 0 if success
     */
    public int storeGameState(final int turn, final GameState gameState) {
        return gameStateDao.storeGameState(turn, gameState);
    }

    /**
     * Store a VisualizerTurn
     * @param visualizerTurn VisualizerTurn to store
     * @return 1 if fail, 0 if success
     */
    public int storeVisualizerTurn(final int turn, final VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return gameStateDao.storeVisualizerTurn(turn, visualizerTurn);
    }

    /**
     * Log the date of the turn, for retrieving turn by date later
     * @param turn turn to log
     * @param date date of turn
     * @return 1 if fail, 0 if success
     */
    public int logTurnDate(final int turn, final Date date) {
        return gameStateDao.logTurnDate(turn, date);
    }

    /**
     * Retrieve list of VisualizerTurns
     * @return List of VisualizerTurn
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return gameStateDao.getVisualizerTurns();
    }
}
