package mech.mania.engine.server.api;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.service.GameStateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * An API for getting information from the game state. Will handle storing GameState,
 * VisualizerTurns, and any other objects that need storing throughout.
 */
public class GameStateController {

    private final GameStateService gameStateService;

    @Autowired
    public GameStateController(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    /**
     *
     * @return most recent GameState
     */
    GameState getMostRecentGameState() {
        return gameStateService.getGameState();
    }

    /**
     *
     * @return most recent VisualizerTurn
     */
    VisualizerTurnProtos.VisualizerTurn getMostRecentVisualizerTurn() {
        return gameStateService.getVisualizerTurn();
    }

    /**
     *
     * @return all currently stored GameState items
     */
    List<GameState> getAllGameStates() {
        return gameStateService.getAllGameStates();
    }

    /**
     *
     * @return all currently stored VisualizerTurn items
     */
    List<VisualizerTurnProtos.VisualizerTurn> getAllVisualizerTurns() {
        return gameStateService.getAllVisualizerTurns();
    }

    /**
     *
     * @param gameState GameState to store
     * @return 0 if fail, 1 if success
     */
    int storeGameState(GameState gameState) {
        return gameStateService.storeGameState(gameState);
    }

    /**
     *
     * @param visualizerTurn VisualizerTurn to store
     * @return 0 if fail, 1 if success
     */
    int storeVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return gameStateService.storeVisualizerTurn(visualizerTurn);
    }
}
