package mech.mania.engine.server.service;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.GameStateDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameStateService {
    private final GameStateDao gameStateDao;

    public GameStateService(@Qualifier("fakeDao") GameStateDao gameStateDao) {
        this.gameStateDao = gameStateDao;
    }

    /**
     *
     * @return most recent GameState
     */
    public GameState getGameState() {
        // TODO
        return gameStateDao.getGameState();
    }

    /**
     *
     * @return most recent VisualizerTurn
     */
    public VisualizerTurnProtos.VisualizerTurn getVisualizerTurn() {
        // TODO
        return gameStateDao.getVisualizerTurn();
    }

    /**
     *
     * @return all currently stored GameState items
     */
    public List<GameState> getAllGameStates() {
        // TODO
        return gameStateDao.getAllGameStates();
    }

    /**
     *
     * @return all currently stored VisualizerTurn items
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns() {
        // TODO
        return gameStateDao.getAllVisualizerTurns();
    }

    /**
     *
     * @param gameState GameState to store
     * @return 0 if fail, 1 if success
     */
    public int storeGameState(GameState gameState) {
        // TODO
        return gameStateDao.storeGameState(gameState);
    }

    /**
     *
     * @param visualizerTurn VisualizerTurn to store
     * @return 0 if fail, 1 if success
     */
    public int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        // TODO
        return gameStateDao.storeVisualizerTurn(visualizerTurn);
    }
}
