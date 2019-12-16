package mech.mania.engine.server.api;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.PlayerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.GameStateFakeDao;
import mech.mania.engine.server.service.GameStateService;

import java.util.Date;
import java.util.List;

/**
 * An API for getting information from the game state. Will handle storing GameState,
 * VisualizerTurns, and any other objects that need storing throughout.
 */
public class GameStateController {

    private final GameStateService gameStateService;

    /**
     * Construct a GameStateController using a Dao.
     */
    public GameStateController() {
        this.gameStateService = new GameStateService(new GameStateFakeDao());
    }

    /**
     * Log the date of the turn, for use later to retrieve GameStates by Date
     * @param turn turn number
     * @return 1 if fail, 0 if success
     */
    public int logTurnDate(final int turn, final Date date) {
        gameStateService.logTurnDate(turn, date);
        return 0;
    }

    /**
     * Store GameState asynchronously.
     * @param gameState GameState to store
     * @return 1 if fail, 0 if success
     */
    public int asyncStoreGameState(int turn, GameState gameState) {
        // TODO: insert Async stuff
        return storeGameState(turn, gameState);
    }

    /**
     * Stores a GameState in the database.
     * @param gameState GameState to store
     * @return 1 if fail, 0 if success
     */
    public int storeGameState(int turn, GameState gameState) {
        return gameStateService.storeGameState(turn, gameState);
    }

    /**
     * Store visualizer turn asynchronously.
     * @param visualizerTurn VisualizerTurn to store
     * @return 1 if fail, 0 if success
     */
    public int asyncStoreVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        // TODO: insert Async stuff
        return storeVisualizerTurn(turn, visualizerTurn);
    }

    /**
     * Stores a VisualizerTurn in the database.
     * @param visualizerTurn VisualizerTurn to store
     * @return 1 if fail, 0 if success
     */
    public int storeVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        return gameStateService.storeVisualizerTurn(turn, visualizerTurn);
    }

    /**
     * Get Player Decisions from all endpoints.
     */
    public List<PlayerDecisionProtos.PlayerDecision> getPlayerDecisions(int turn) {
        return PlayerBinaryWebSocketHandler.getTurnAllPlayers(turn);
    }

    /**
     * Return a new GameState given a list of PlayerDecisions.
     * @param gameState GameState to use to update
     * @param playerDecisions PlayerDecision objects to use to update
     * @return 1 if fail, 0 if success
     */
    public int updateGameState(GameState gameState, List<PlayerDecisionProtos.PlayerDecision> playerDecisions) {
        return gameState.update(playerDecisions);
    }

    /**
     * Given a gameState, use its internal state to create a VisualizerTurn to send
     * to players
     * @param gameState GameState to use to construct a VisualizerTurn
     * @return VisualizerTurn from the given GameState
     */
    public VisualizerTurnProtos.VisualizerTurn constructVisualizerTurn(GameState gameState) {
        // TODO: construct VisualizerTurn
        return VisualizerTurnProtos.VisualizerTurn.newBuilder()
                .build();
    }

    /**
     * Given a gameState, use its internal state to create a PlayerTurn to send
     * to players
     * @param gameState GameState to use to construct PlayerTurn
     * @return PlayerTurn from the given GameState
     */
    public PlayerTurnProtos.PlayerTurn constructPlayerTurn(GameState gameState) {
        // TODO: construct PlayerTurn
        return PlayerTurnProtos.PlayerTurn.newBuilder()
                .setIncrement(1)
                .build();
    }

    /**
     * Retrieve VisualizerTurns if necessary
     * @return List of VisualizerTurns
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return gameStateService.getVisualizerTurns();
    }
}
