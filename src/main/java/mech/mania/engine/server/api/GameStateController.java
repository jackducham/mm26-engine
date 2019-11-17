package mech.mania.engine.server.api;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.GameStateFakeDao;
import mech.mania.engine.server.service.GameStateService;

import java.util.List;

/**
 * An API for getting information from the game state. Will handle storing GameState,
 * VisualizerTurns, and any other objects that need storing throughout.
 */
public class GameStateController {

    private final GameStateService gameStateService;

    public GameStateController() {
        this.gameStateService = new GameStateService(new GameStateFakeDao());
    }

//    public GameStateController(GameStateService gameStateService) {
//        this.gameStateService = gameStateService;
//    }

    /**
     * Gets the most recent GameState
     * @return most recent GameState
     */
    public GameState getMostRecentGameState() {
        return gameStateService.getGameState();
    }

    /**
     * Gets the most recent VisualizerTurn
     * @return most recent VisualizerTurn
     */
    public VisualizerTurnProtos.VisualizerTurn getMostRecentVisualizerTurn() {
        return gameStateService.getVisualizerTurn();
    }

    /**
     * Gets all GameState objects in a List
     * @return all currently stored GameState items
     */
    public List<GameState> getAllGameStates() {
        return gameStateService.getAllGameStates();
    }

    /**
     * Gets all VisualizerTurn objects in a list
     * @return all currently stored VisualizerTurn items
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns() {
        return gameStateService.getAllVisualizerTurns();
    }

    /**
     * Stores a GameState in the database
     * @param gameState GameState to store
     * @return 0 if fail, 1 if success
     */
    public int storeGameState(GameState gameState) {
        return gameStateService.storeGameState(gameState);
    }

    /**
     * Stores a VisualizerTurn in the database
     * @param visualizerTurn VisualizerTurn to store
     * @return 0 if fail, 1 if success
     */
    public int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return gameStateService.storeVisualizerTurn(visualizerTurn);
    }

    /**
     *
     */
    public int storePlayerTurn(PlayerTurnProtos.PlayerTurn playerTurn) {
        return gameStateService.storePlayerTurn(playerTurn);
    }
}
