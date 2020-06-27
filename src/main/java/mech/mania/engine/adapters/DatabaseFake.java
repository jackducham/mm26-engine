package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.model.PlayerInfo;
import mech.mania.engine.domain.model.VisualizerProtos.VisualizerChange;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * Use regular data structures to store data (for now).
 */
public class DatabaseFake implements Database {
    
    private static final Logger LOGGER = Logger.getLogger( DatabaseFake.class.getName() );

    private final Map<Integer, GameState> gameStates = new HashMap<>();
    private final Map<Integer, VisualizerChange> visualizerChanges = new HashMap<>();

    private int currentTurnNum = 0;
    private GameState currentGameState = new GameState();
    private final Map<String, PlayerInfo> currentPlayerInfoMap = new ConcurrentHashMap<>();

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        this.currentGameState = gameState;
        LOGGER.fine("Logging GameState for turn " + turn + ", GameState: " + gameState.toString());
        gameStates.put(turn, gameState);
        LOGGER.fine(gameStates.size() + " GameStates stored currently");
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange) {
        LOGGER.fine("Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + visualizerChange.toString());
        visualizerChanges.put(turn, visualizerChange);
        LOGGER.fine(visualizerChanges.size() + " VisualizerTurns stored currently");
        return 0;
    }

    @Override
    public int getCurrentTurnNum() {
        return currentTurnNum;
    }

    @Override
    public int updateCurrentTurnNum(final int newTurn) {
        currentTurnNum = newTurn;
        return 1;
    }

    @Override
    public GameState getCurrentGameState() {
        return currentGameState == null ? new GameState() : currentGameState;
    }

    @Override
    public Map<String, PlayerInfo> getPlayerInfoMap() {
        return currentPlayerInfoMap;
    }

    @Override
    public PlayerExistence updatePlayerInfoMap(String playerName, String playerIp) {
        Map<String, PlayerInfo> playerInfoMap = getPlayerInfoMap();

        if (playerInfoMap.containsKey(playerName)) {
            playerInfoMap.put(playerName, new PlayerInfo(playerIp, playerInfoMap.get(playerName).getTurnJoined()));
            return PlayerExistence.PLAYER_EXISTS;
        }

        playerInfoMap.put(playerName, new PlayerInfo(playerIp, getCurrentTurnNum()));
        return PlayerExistence.PLAYER_DOES_NOT_EXIST;
    }

    @Override
    public void reset() {
        currentTurnNum = 0;
        currentGameState = null;
        currentPlayerInfoMap.clear();
        gameStates.clear();
        visualizerChanges.clear();
    }
}
