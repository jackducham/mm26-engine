package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.RepositoryAbstract;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.model.PlayerInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public abstract class UnitOfWorkAbstract {

    private static final Logger LOGGER = Logger.getLogger( UnitOfWorkAbstract.class.getName() );
    private final Queue<Message> messages = new LinkedList<>();
    private final RepositoryAbstract repository;
    private final Map<String, PlayerInfo> currentPlayerInfoMap = new ConcurrentHashMap<>();
    private GameState gameState;
    private int turn;
    private ConfigurableApplicationContext infraCtx;
    private ConfigurableApplicationContext visualizerCtx;
    private boolean gameOver;

    /**
     * Constructor that sets an AbstractRepository
     */
    public UnitOfWorkAbstract(RepositoryAbstract repository) {
        this.repository = repository;
    }

    /**
     * Get the repository in order to use repository actions
     */
    public RepositoryAbstract getRepository() {
        return repository;
    }

    /**
     * Add a new message to execute after this function finishes
     */
    public void addNewMessage(Message message) {
        messages.add(message);
    }

    /**
     * Collect any new messages that were accumulated during the processing of the previous message
     */
    public Queue<Message> collectNewMessages() {
        Queue<Message> toSend = new LinkedList<>();
        while (!messages.isEmpty()) {
            toSend.offer(messages.poll());
        }
        return toSend;
    }

    /**
     * Set the current GameState
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Get the current GameState
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Get the current turn number
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Set the turn number stored within this UnitOfWork object
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }

    /**
     * Get the PlayerInfoMap object
     */
    public Map<String, PlayerInfo> getPlayerInfoMap() {
        return currentPlayerInfoMap;
    }

    /**
     * @return whether there was a player in the map in the beginning
     * (true if player existed, false if player did not exist and new
     * player was added)
     */
    public boolean updatePlayerInfoMap(String playerName, String playerIp) {
        Map<String, PlayerInfo> playerInfoMap = getPlayerInfoMap();

        if (playerInfoMap.containsKey(playerName)) {
            playerInfoMap.put(playerName, new PlayerInfo(playerIp, playerInfoMap.get(playerName).getTurnJoined()));
            return true;
        }

        playerInfoMap.put(playerName, new PlayerInfo(playerIp, getTurn()));
        return false;
    }

    /**
     * Store a ConfigurableApplicationContext object in order to stop the InfraRESTServer
     * @param ctx
     */
    public void storeInfraCtx(ConfigurableApplicationContext ctx) {
        this.infraCtx = ctx;
    }

    /**
     * Use the saved ConfigurableApplicationContext to stop the InfraRESTServer
     */
    public void stopInfraServer() {
        SpringApplication.exit(infraCtx, () -> 0);
    }

    /**
     * Store a ConfigurableApplicationContext object in order to stop the VisualizerWebSocket
     * @param ctx
     */
    public void storeVisualizerCtx(ConfigurableApplicationContext ctx) {
        this.visualizerCtx = ctx;
    }

    /**
     * Use the saved ConfigurableApplicationContext to stop the VisualizerWebSocket
     */
    public void stopVisualizerServer() {
        SpringApplication.exit(visualizerCtx, () -> 0);
    }

    /**
     * Sets the game over status for this UoW.
     * @param gameOver
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Sets the game over status for this UoW.
     * @return boolean gameOver
     */
    public boolean getGameOver() {
        return gameOver;
    }
}
