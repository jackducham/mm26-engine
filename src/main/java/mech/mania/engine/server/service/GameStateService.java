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
     * @return 0 if fail, 1 if success
     */
    public int storeGameState(final int turn, final GameState gameState) {
        // TODO
        return gameStateDao.storeGameState(turn, gameState);
    }

    /**
     * Store a VisualizerTurn
     * @param visualizerTurn VisualizerTurn to store
     * @return 0 if fail, 1 if success
     */
    public int storeVisualizerTurn(final int turn, final VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        // TODO
        return gameStateDao.storeVisualizerTurn(turn, visualizerTurn);
    }

    /**
     *
     * @param turn
     * @param date
     * @return
     */
    public int logTurnDate(final int turn, final Date date) {
        gameStateDao.logTurnDate(turn, date);
        return 0;
    }

    /**
     *
     * @return
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return gameStateDao.getVisualizerTurns();
    }
}
