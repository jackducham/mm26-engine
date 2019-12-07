package mech.mania.engine.server.service;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.GameStateDao;

import java.util.List;

public class GameStateService {
    private final GameStateDao gameStateDao;

    public GameStateService(GameStateDao gameStateDao) {
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

    /**
     *
     * @param playerDecision
     * @return
     */
    public int storePlayerDecision(PlayerDecisionProtos.PlayerDecision playerDecision) {
        return gameStateDao.storePlayerDecision(playerDecision.getPlayerUuid(), playerDecision);
    }

    /**
     *
     * @param playerDecision
     * @return
     */
    public int storePlayerTurn(PlayerTurnProtos.PlayerTurn playerDecision) {
        return gameStateDao.storePlayerTurn(playerDecision.getPlayerUuid(), playerDecision);
    }
}
