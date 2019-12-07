package mech.mania.engine.server.api;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.GameStateFakeDao;
import mech.mania.engine.server.service.GameStateService;

import java.util.ArrayList;
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
     *
     * @param gameState
     * @return
     */
    public int asyncStoreGameState(GameState gameState) {
        // TODO: insert Async stuff
        return storeGameState(gameState);
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
     *
     * @param visualizerTurn
     * @return
     */
    public int asyncStoreVisualizerTurn(VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        // TODO: insert Async stuff
        return storeVisualizerTurn(visualizerTurn);
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
     * @param playerTurn
     * @return
     */
    public int asyncStorePlayerTurn(PlayerTurnProtos.PlayerTurn playerTurn) {
        // TODO: insert Async stuff
        return storePlayerTurn(playerTurn);
    }

    /**
     *
     */
    public int storePlayerTurn(PlayerTurnProtos.PlayerTurn playerTurn) {
        return gameStateService.storePlayerTurn(playerTurn);
    }

    /**
     * Get Player Decisions from all endpoints
     */
    public List<PlayerDecisionProtos.PlayerDecision> getPlayerDecisions() {
        return new ArrayList<>();
    }

    /**
     * Return a new GameState given a list of PlayerDecisions
     * @param gameState
     * @param playerDecisions
     * @return
     */
    public GameState updateGameState(GameState gameState, List<PlayerDecisionProtos.PlayerDecision> playerDecisions) {
        return new GameState();
    }

    /**
     * Given a gameState, use its internal state to create a VisualizerTurn to send
     * to players
     * @param gameState
     * @return
     */
    public VisualizerTurnProtos.VisualizerTurn constructVisualizerTurn(GameState gameState) {
        return VisualizerTurnProtos.VisualizerTurn.getDefaultInstance();
    }

    /**
     * Given a gameState, use its internal state to create a PlayerTurn to send
     * to players
     * @param gameState
     * @return
     */
    public PlayerTurnProtos.PlayerTurn constructPlayerTurn(GameState gameState) {
        return PlayerTurnProtos.PlayerTurn.getDefaultInstance();
    }

}
