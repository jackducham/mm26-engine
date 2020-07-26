package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.BoardProtos;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.characters.*;
import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.model.GameStateProtos;
import mech.mania.engine.game.model.VisualizerChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static mech.mania.engine.game.board.Board.createDefaultBoard;
import static mech.mania.engine.game.board.Board.createHomeBoard;

public class GameState {
    private long turnNumber;
    private Map<String, Board> boardNames;
    private Map<String, Player> playerNames;
    private Map<String, Monster> monsterNames;
    public VisualizerChange stateChange;

    /**
     * Sets up a default GameState. Will eventually be expanded to set up the entire pvp board.
     */
    public GameState() {
        turnNumber = 0;
        boardNames = new HashMap<>();
        playerNames = new HashMap<>();
        monsterNames = new HashMap<>();
        stateChange = new VisualizerChange();

        // @TODO: Create pvp board
    }



    /**
     * Gets the pvp board.
     * @return the pvp board
     */
    public Board getPvpBoard() {
        return boardNames.get("pvp");
    }

    /**
     * Getter for a specific player's board.
     * @param boardId id of player whose board is being requested
     * @return board of the specified player; null if no such player exists
     */
    public Board getBoard(String boardId) {
        if (boardNames.containsKey(boardId)) {
            return boardNames.get(boardId);
        }
        return null;
    }



    /**
     * Getter for a specific character (player or monster).
     * @param characterId id of requested character
     * @return the matching character; null if the id doesn't exist
     */
    public Character getCharacter(String characterId) {
        if(playerNames.containsKey(characterId)) {
            return playerNames.get(characterId);
        }

        if(monsterNames.containsKey(characterId)) {
            return monsterNames.get(characterId);
        }

        return null;
    }

    /**
     * Getter for a specific player.
     * @param playerId the id of the requested player
     * @return the player matching the id; null if the id doesn't exist
     */
    public Player getPlayer(String playerId) {
        if (playerNames.containsKey(playerId)) {
            return playerNames.get(playerId);
        }
        return null;
    }

    /**
     * Gets the map of player names to player objects.
     * @return the requested map
     */
    public Map<String, Player> getAllPlayers() {
        return playerNames;
    }

    /**
     * Gets all players on a specific board.
     * @param boardId id of the board of interest
     * @return list of players on given board
     */
    public List<Player> getPlayersOnBoard(String boardId) {
        if (!boardNames.containsKey(boardId)) {
            return new ArrayList<>();
        }

        Predicate<Player> byBoard = player -> player.getPosition().getBoardID().equals(boardId);

        return playerNames.values().stream().filter(byBoard)
                .collect(Collectors.toList());
    }

    /**
     * Getter for a specific monster.
     * @param monsterId the id of the requested monster
     * @return the monster matching the id; null if the id doesn't exist
     */
    public Monster getMonster(String monsterId) {
        if (monsterNames.containsKey(monsterId)) {
            return monsterNames.get(monsterId);
        }
        return null;
    }

    /**
     * Gets the map of monster names to monster objects.
     * @return the requested map
     */
    public Map<String, Monster> getAllMonsters() {
        return monsterNames;
    }

    /**
     * Gets all monsters on a specific board.
     * @param boardId id of the board of interest
     * @return list of all monsters on given board
     */
    public List<Monster> getMonstersOnBoard(String boardId) {
        if (!boardNames.containsKey(boardId)) {
            return new ArrayList<>();
        }

        Predicate<Monster> byBoard = monster -> monster.getPosition().getBoardID().equals(boardId);

        return monsterNames.values().stream().filter(byBoard)
                .collect(Collectors.toList());
    }

    /**
     * Creates a GameState object for use with a variety of tests.
     * @return a custom GameState
     */
    public static GameState createDefaultGameState() {
        GameState defaultGameState = new GameState();
        //adds a 30x30 pvp board with hard coded obstacles
        defaultGameState.boardNames.put("pvp", createDefaultBoard(30, 30, false, "pvp"));
        for(int i = 1; i < 29; ++i) {
            defaultGameState.getPvpBoard().getGrid()[i][10].setType(Tile.TileType.IMPASSIBLE);
        }
        for(int i = 1; i < 29; ++i) {
            defaultGameState.getPvpBoard().getGrid()[i][20].setType(Tile.TileType.IMPASSIBLE);
        }

        //adds two portals to the pvp board
        defaultGameState.getPvpBoard().getPortals().add(new Position(10, 14, "pvp"));
        defaultGameState.getPvpBoard().getPortals().add(new Position(14, 1, "pvp"));

        //adds two players and moves them onto the pvp board
        defaultGameState.addNewPlayer("player1");
        defaultGameState.getPlayer("player1").setPosition(new Position(0, 4, "pvp"));
        defaultGameState.addNewPlayer("player2");
        defaultGameState.getPlayer("player2").setPosition(new Position(0, 24, "pvp"));

        //adds a single monster to the pvp board.
        //currently addNewMonster calls createDefaultMonster, so this may need changed depending on what happens to addNewMonster
        defaultGameState.addNewMonster(0, 0, 0, 0, 0, 0, 0, 0, new Position(14, 25, "pvp"));

        return defaultGameState;
    }



    /**
     * Builds a Protocol Buffer of the GameState it is called on.
     * @return Protocol Buffer representing this GameState
     */
    public GameStateProtos.GameState buildProtoClass() {
        GameStateProtos.GameState.Builder gameStateBuilder = GameStateProtos.GameState.newBuilder();
        gameStateBuilder.setStateId(turnNumber);

        for (String boardID : boardNames.keySet()) {
            gameStateBuilder.putBoardNames(boardID, boardNames.get(boardID).buildProtoClass());
        }

        for (String playerName : playerNames.keySet()) {
            gameStateBuilder.putPlayerNames(playerName, playerNames.get(playerName).buildProtoClassPlayer());
        }

        for (String monsterName : monsterNames.keySet()) {
            gameStateBuilder.putMonsterNames(monsterName, monsterNames.get(monsterName).buildProtoClassMonster());
        }

        return gameStateBuilder.build();
    }

    /**
     * Builds a GameState object from a given Protocol Buffer.
     * @param gameStateProto Protocol Buffer representing the GameState to be copied
     */
    public GameState(GameStateProtos.GameState gameStateProto) {
        boardNames = new HashMap<>();

        Map<String, BoardProtos.Board> boardProtoMap = gameStateProto.getBoardNamesMap();

        for (String boardId : boardProtoMap.keySet()) {
            boardNames.put(boardId, new Board(boardProtoMap.get(boardId)));
        }

        turnNumber = gameStateProto.getStateId();

        playerNames = new HashMap<>();
        monsterNames = new HashMap<>();

        Map<String, CharacterProtos.Monster> allMonsters = gameStateProto.getMonsterNamesMap();
        for (String monsterName : allMonsters.keySet()) {
            Monster newMonster = new Monster(allMonsters.get(monsterName));
            monsterNames.put(newMonster.getName(), newMonster);
        }

        Map<String, CharacterProtos.Player> allPlayers = gameStateProto.getPlayerNamesMap();
        for (String playerName : allPlayers.keySet()) {
            Player newPlayer = new Player(allPlayers.get(playerName));
            playerNames.put(newPlayer.getName(), newPlayer);
        }
    }

    /**
     * Adds new players to the game.
     * @param playerName name of the player being added
     */
    public void addNewPlayer(String playerName) {
        // TODO specify board dimensions
        boardNames.put(playerName, createHomeBoard(playerName));
        //TODO specify spawn point location on each board
        playerNames.put(playerName, new Player(playerName, new Position(0, 0, playerName)));
    }


    //TODO: should this function have a bool instaed of all the doubles where false sets all factors to 0
    // and true picks a random value from -1 to 1 for each of them?
    // Also this function will likely need split into a separate function for each monster type.
    /**
     * Adds a new monster to the game.
     * @param speedFactor random factor for speed spread
     * @param maxHealthFactor random factor for max hp spread
     * @param attackFactor random factor for attack spread
     * @param defenseFactor random factor for defense spread
     * @param experienceFactor random factor for exp spread
     * @param rangeFactor random factor for range spread
     * @param splashFactor random factor for splash spread
     * @param numberOfDrops random factor for number of drops
     * @param spawnPoint spawn point (and location the monster leashes back to)
     */
    public void addNewMonster(double speedFactor, double maxHealthFactor, double attackFactor, double defenseFactor, double experienceFactor, double rangeFactor, double splashFactor, int numberOfDrops, Position spawnPoint) {
        Monster monster = Monster.createDefaultMonster(speedFactor, maxHealthFactor, attackFactor, defenseFactor, experienceFactor, rangeFactor, splashFactor, numberOfDrops, spawnPoint);
        monsterNames.put(monster.getName(), monster);
    }


}