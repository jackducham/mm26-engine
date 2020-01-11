package mech.mania.engine.server.api;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.PlayerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import mech.mania.engine.server.dao.DatabaseFake;
import mech.mania.engine.server.service.DatabaseService;

import java.util.Date;
import java.util.List;

/**
 * An API for getting information from the game state. Will handle storing GameState,
 * VisualizerTurns, and any other objects that need storing throughout.
 */
public class GameStateController {

    private final DatabaseService databaseService;
    private int currentTurnNum;

    /**
     * Construct a GameStateController using a Dao.
     */
    public GameStateController() {
        this.databaseService = new DatabaseService(new DatabaseFake());
    }

    /**
     * Log the date of the turn, for use later to retrieve GameStates by Date
     *
     * @param turn turn number
     * @param date date to store
     * @return 1 if fail, 0 if success
     */
    public int logTurnDate(final int turn, final Date date) {
        currentTurnNum = turn;
        return databaseService.logTurnDate(turn, date);
    }

    /**
     * Store GameState asynchronously.
     * @param turn turn to store for
     * @param gameState GameState to store
     * @return 1 if fail, 0 if success
     */
    public int asyncStoreGameState(int turn, GameState gameState) {
        // TODO: insert Async stuff
        new Thread(() -> storeGameState(turn, gameState)).start();
        return 0;
    }

    /**
     * Stores a GameState in the database.
     * @param turn turn to store for
     * @param gameState GameState to store
     * @return 1 if fail, 0 if success
     */
    public int storeGameState(int turn, GameState gameState) {
        return databaseService.storeGameState(turn, gameState);
    }

//    /**
//     * Store visualizer turn asynchronously.
//     * @param visualizerTurn VisualizerTurn to store
//     * @return 1 if fail, 0 if success
//     */
//    public int asyncStoreVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
//        // TODO: insert Async stuff
//        new Thread(() -> storeVisualizerTurn(turn, visualizerTurn)).start();
//        return 0;
//    }
//
//    /**
//     * Stores a VisualizerTurn in the database.
//     * @param visualizerTurn VisualizerTurn to store
//     * @return 1 if fail, 0 if success
//     */
//    public int storeVisualizerTurn(int turn, VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
//        return databaseService.storeVisualizerTurn(turn, visualizerTurn);
//    }

    /**
     * Get Player Decisions from all endpoints.
     * @param turn turn to get PlayerDecision for
     * @return List of PlayerDecisions received for that turn
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
     * @param playerName name of the player to get the playerTurn for
     * @param turnNumber turn the playerTurn is for
     * @return PlayerTurn from the given GameState
     */
    public PlayerTurnProtos.PlayerTurn constructPlayerTurn(String playerName, int turnNumber) {
        // TODO: construct PlayerTurn by looking up information specific for this player
        return PlayerTurnProtos.PlayerTurn.newBuilder()
                .setPlayerName(playerName)
                .setTurn(turnNumber)
                .setIncrement(1)
                .build();
    }

    /**
     * Retrieve VisualizerTurns if necessary
     * @return List of VisualizerTurns
     */
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return databaseService.getVisualizerTurns();
    }

    /**
     * Check whether the game is over (currently checks for whether the number is at 10
     * @param gameState GameState to check
     * @return whether the game is over
     */
    public boolean isGameOver(GameState gameState) {
        return gameState.getNumber() > 500;
    }

    /**
     * Gets the current turn number
     * @return current turn number
     */
    public int getCurrentTurn() {
        return currentTurnNum;
    }
}
