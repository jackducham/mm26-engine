package mech.mania.engine.server.api;

import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.PlayerRequestSender;
import mech.mania.engine.server.communication.player.model.PlayerInfo;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;
import mech.mania.engine.server.dao.DatabaseFake;
import mech.mania.engine.server.service.DatabaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * An API for getting information from the game state. Will handle storing GameState,
 * VisualizerTurns, and any other objects that need storing throughout.
 */
public class GameStateController {

    public enum PlayerExistence {
        PLAYER_EXISTS,
        PLAYER_DOES_NOT_EXIST
    }

    private static final DatabaseService databaseService = new DatabaseService(new DatabaseFake());
    private static int currentTurnNum;
    private static GameState currentGameState;
    private static Map<String, PlayerInfo> playerInfoMap;

    public static GameState getCurrentGameState() {
        if (currentGameState == null) {
            currentGameState = new GameState();
        }
        return currentGameState;
    }


    /**
     * Log the date of the turn, for use later to retrieve GameStates by Date
     *
     * @param turn turn number
     * @param date date to store
     * @return 1 if fail, 0 if success
     */
    public static int logTurnDate(final int turn, final Date date) {
        currentTurnNum = turn;
        return databaseService.logTurnDate(turn, date);
    }


    /**
     * Store GameState asynchronously.
     * @param turn turn to store for
     * @return 1 if fail, 0 if success
     */
    public static int asyncStoreGameState(int turn) {
        new Thread(() -> databaseService.storeGameState(turn, getCurrentGameState())).start();
        return 0;
    }


    /**
     * Given a gameState, use its internal state to create a VisualizerTurn to send
     * to players
     * @return VisualizerTurn from the given GameState
     */
    public static VisualizerChange constructVisualizerChange() {
        GameState gameState = getCurrentGameState();
        // TODO: construct VisualizerTurn
        return VisualizerChange.newBuilder()
                .build();
    }


    /**
     * Given a gameState, use its internal state to create a PlayerTurn to send
     * to players
     * @param playerName name of the player to get the playerTurn for
     * @return PlayerTurn from the given GameState
     */
    public static PlayerTurn constructPlayerTurn(String playerName) {
        // TODO: construct PlayerTurn by looking up information specific for this player
        return PlayerTurn.newBuilder()
                .setPlayerName(playerName)
                .setIncrement(1)
                .build();
    }

    public static Map<String, PlayerInfo> getPlayerInfoMap() {
        return playerInfoMap;
    }

    public static GameState sendPlayerRequestsAndUpdateGameState() {
        List<PlayerDecision> decisions = PlayerRequestSender.sendPlayerRequestsAndUpdateGameState();
        GameState updatedGameState = GameLogic.doTurn(getCurrentGameState(), decisions);
        currentGameState = updatedGameState;
        return updatedGameState;
    }

    /**
     * Add a player IP by the player name and IP.
     * @param playerName name of player to add
     * @param playerIp IP of player to add
     * @return Whether or not the player existed in list of players previously.
     */
    public static PlayerExistence addPlayerIp(String playerName, String playerIp) {
        Date currentDate = new Date();

        if (playerInfoMap.containsKey(playerName)) {
            playerInfoMap.put(playerName, new PlayerInfo(playerIp, currentDate, currentTurnNum));
            return PlayerExistence.PLAYER_EXISTS;
        }

        playerInfoMap.put(playerName, new PlayerInfo(playerIp, currentDate, currentTurnNum));
        return PlayerExistence.PLAYER_DOES_NOT_EXIST;
    }

    /**
     * Gets the current turn number
     * @return current turn number
     */
    public static int getCurrentTurn() {
        return currentTurnNum;
    }
}
