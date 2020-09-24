package mech.mania.engine.service_layer;

import mech.mania.engine.adapters.RepositoryAbstract;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.GameChange;
import mech.mania.engine.domain.model.PlayerConnectInfo;
import mech.mania.engine.domain.model.VisualizerProtos;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public abstract class UnitOfWorkAbstract {

    protected static final Logger LOGGER = Logger.getLogger( UnitOfWorkAbstract.class.getName() );
    protected final RepositoryAbstract repository;

    protected final Queue<Message> messages = new LinkedList<>();
    protected final Map<String, PlayerConnectInfo> connectInfoMap = new ConcurrentHashMap<>();
    protected String visualizerConnectUrl = "";
    protected int turn = 0;
    protected boolean gameOver = false;

    protected GameState gameState = new GameState();
    protected VisualizerProtos.GameChange gameChange = new GameChange().buildProtoClass();

    protected ConfigurableApplicationContext infraCtx;
    protected ConfigurableApplicationContext visualizerCtx;
    protected ConfigurableApplicationContext APICtx;

    /**
     * Constructor that sets an AbstractRepository
     */
    public UnitOfWorkAbstract(RepositoryAbstract repository) {
        this.repository = repository;
    }

    /**
     * Use the repository to store visualizer changes
     * @param turn turn for the game state
     * @param gameChange the game change object
     */
    public void storeGameChange(int turn, VisualizerProtos.GameChange gameChange) {
        repository.storeGameChange(turn, gameChange);
    }

    /**
     * Use the repository to store game state
     * Also store game state in Visualizer WebSocket to be sent to new connections
     * @param turn turn for the game state
     * @param gameState game state object
     */
    public void storeGameState(int turn, GameState gameState) {
        repository.storeGameState(turn, gameState);
        visualizerCtx.getBean(VisualizerWebSocket.VisualizerBinaryWebSocketHandler.class).setLastGameState(gameState);
    }

    public void storePlayerStatsBundle(int turn, CharacterProtos.PlayerStatsBundle playerStatsBundle) {
        repository.storePlayerStatsBundle(turn, playerStatsBundle);
    }

    public void storeCurrentTurn(int turn){
        repository.storeCurrentTurn(turn);
    }

    /**
     * Use the repository to restore game state
     * @param turn turn to restore from
     */
    public void restoreTurn(int turn){
        GameState startState = repository.getGameState(turn);
        this.setTurn(turn);
        this.setGameState(startState);
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

    public void setGameChange(VisualizerProtos.GameChange gameChange) {
        this.gameChange = gameChange;
    }

    public VisualizerProtos.GameChange getGameChange() {
        return gameChange;
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
    public Map<String, PlayerConnectInfo> getPlayerConnectInfoMap() {
        return connectInfoMap;
    }

    /**
     * @return whether there was a player in the map in the beginning
     * (true if player existed, false if player did not exist and new
     * player was added)
     */
    public boolean updatePlayerConnectInfoMap(String playerName, String playerIp) {
        if (connectInfoMap.containsKey(playerName)) {
            connectInfoMap.put(playerName, new PlayerConnectInfo(playerIp));
            return true;
        }

        connectInfoMap.put(playerName, new PlayerConnectInfo(playerIp));
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
    public abstract void stopInfraServer();

    /**
     * Store a ConfigurableApplicationContext object in order to stop the VisualizerWebSocket
     * @param ctx
     */
    public void storeVisualizerCtx(ConfigurableApplicationContext ctx) {
        this.visualizerCtx = ctx;
    }

    /**
     * Get visualizer context to be able to send out GameChanges
     * @return The application context of the visualizer websocket
     */
    public ConfigurableApplicationContext getVisualizerCtx() {
        return visualizerCtx;
    }

    /**
     * Use the saved ConfigurableApplicationContext to stop the VisualizerWebSocket
     */
    public abstract void stopVisualizerServer();

    /**
     * Store a ConfigurableApplicationContext object in order to stop the API server WebSocket
     * @param ctx
     */
    public void storeAPICtx(ConfigurableApplicationContext ctx) {
        this.APICtx = ctx;
    }

    /**
     * Use the saved ConfigurableApplicationContext to stop the API server WebSocket
     */
    public abstract void stopAPIServer();

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

    public boolean setVisualizerConnectUrl(String visualizerConnectUrl) {
        this.visualizerConnectUrl = visualizerConnectUrl;
        return true;
    }

    public String getVisualizerConnectUrl() {
        return visualizerConnectUrl;
    }
}
