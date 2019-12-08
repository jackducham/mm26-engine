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

//    /**
//     *
//     * @return most recent GameState
//     */
//    public GameState getGameState() {
//        // TODO
//        return gameStateDao.getGameState();
//    }
//
//    /**
//     *
//     * @return most recent VisualizerTurn
//     */
//    public VisualizerTurnProtos.VisualizerTurn getVisualizerTurn() {
//        // TODO
//        return gameStateDao.getVisualizerTurn();
//    }
//
//    /**
//     *
//     * @return all currently stored GameState items
//     */
//    public List<GameState> getAllGameStates() {
//        // TODO
//        return gameStateDao.getAllGameStates();
//    }
//
//    /**
//     *
//     * @return all currently stored VisualizerTurn items
//     */
//    public List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns() {
//        // TODO
//        return gameStateDao.getAllVisualizerTurns();
//    }

    /**
     * Store a GameState
     * @param gameState GameState to store
     * @return 0 if fail, 1 if success
     */
    public int storeGameState(int turn, GameState gameState) {
        // TODO
        return gameStateDao.storeGameState(turn, gameState);
    }

    /**
     * Store a VisualizerTurn
     * @param visualizerTurn VisualizerTurn to store
     * @return 0 if fail, 1 if success
     */
    public int storeVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        // TODO
        return gameStateDao.storeVisualizerTurn(turn, visualizerTurn);
    }

    /**
     * Store an individual PlayerTurn
     * @param playerTurn
     * @return
     */
    public int storePlayerTurn(int turn, PlayerTurnProtos.PlayerTurn playerTurn) {
        return gameStateDao.storePlayerTurn(turn, playerTurn);
    }

    /**
     * Store a List of PlayerDecision objects by turn
     * @param playerDecisions
     * @return
     */
    public int storePlayerDecisions(int turn, List<PlayerDecisionProtos.PlayerDecision> playerDecisions) {
        return gameStateDao.storePlayerDecisions(turn, playerDecisions);
    }
}
