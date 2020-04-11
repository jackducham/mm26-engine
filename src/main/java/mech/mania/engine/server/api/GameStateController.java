package mech.mania.engine.server.api;

import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.GameState;
import mech.mania.engine.server.communication.player.PlayerRequestSender;
import mech.mania.engine.server.communication.player.model.PlayerInfo;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerTurn;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;
import mech.mania.engine.server.dao.DatabaseFake;
import mech.mania.engine.server.dao.Database;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * An API for getting information from the game state. Will handle storing GameState,
 * VisualizerTurns, and any other objects that need storing throughout.
 */
public class GameStateController {

    private static final Logger LOGGER = Logger.getLogger( GameStateController.class.getName() );
    private static final Database DATABASE = new DatabaseFake();



    // ---------------------------- FUNCTIONS CALLED BY MAIN ------------------------------------
    /**
     * Resets state in the database
     * @return 1 if successful, 0 if failure
     */
    public static int resetState() {
        DATABASE.reset();
        return 1;
    }

    /**
     * Cleans up Visualizer connection (only Websocket connection)
     * TODO: tell players to close their own servers?
     * @return 1 if successful, 0 if failure
     */
    public static int cleanUpConnections() {
        VisualizerBinaryWebSocketHandler.destroy();
        return 1;
    }

    /**
     * Constructs from constructVisualizerChange, and sends it
     * @return 1 if successful, 0 if failure
     */
    public static int sendVisualizerChange() {
        VisualizerChange change = constructVisualizerChange();
        VisualizerBinaryWebSocketHandler.sendChange(change);
        return 1;
    }

    /**
     * Store GameState asynchronously.
     * @return 1 if fail, 0 if success
     */
    public static int asyncStoreCurrentGameState() {
        new Thread(() -> {
            if (DATABASE.storeGameState(getCurrentTurnNum(), getCurrentGameState()) != 0) {
                LOGGER.warning("asyncStoreGameState returned non-zero status");
            }
            LOGGER.fine("asyncStoreGameState thread closed.");
        }).start();
        return 0;
    }

    /**
     * Gets player decisions by sending requests and updates the current GameState
     * @return updated GameState after getting PlayerDecisions and updating
     */
    public static GameState sendPlayerRequestsAndUpdateGameState() {
        List<PlayerDecision> decisions = PlayerRequestSender.sendPlayerRequestsAndUpdateGameState();
        GameState updatedGameState = GameLogic.doTurn(getCurrentGameState(), decisions);
        DATABASE.storeGameState(DATABASE.getCurrentTurnNum(), updatedGameState);
        return updatedGameState;
    }




    // -------------------------- HELPER FUNCTIONS -----------------------------------------------
    /**
     * Gets the current GameState from the database
     * @return current GameState
     */
    public static GameState getCurrentGameState() {
        return DATABASE.getCurrentGameState();
    }

    /**
     * Gets the current playerInfoMap
     * @return Map between player name and PlayerInfo object
     */
    public static Map<String, PlayerInfo> getPlayerInfoMap() {
        return DATABASE.getPlayerInfoMap();
    }

    /**
     * Increments the turn number in the database
     * @return 1 if successful, 0 if failure
     */
    public static int incrementTurn() {
        return updateTurnNum(getCurrentTurnNum() + 1);
    }

    public static int updateTurnNum(int newTurn) {
        return DATABASE.updateCurrentTurnNum(newTurn);
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
                .build();
    }

    /**
     * Add a player IP by the player name and IP.
     * @param playerName name of player to add
     * @param playerIp IP of player to add
     * @return Whether or not the player existed in list of players previously.
     */
    public static Database.PlayerExistence addPlayerIp(String playerName, String playerIp) {
        return DATABASE.updatePlayerInfoMap(playerName, playerIp);
    }

    /**
     * Gets the current turn number
     * @return current turn number
     */
    public static int getCurrentTurnNum() {
        return DATABASE.getCurrentTurnNum();
    }
}
