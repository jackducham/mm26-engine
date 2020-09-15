
package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.model.BoardProtos;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.GameChange;
import mech.mania.engine.domain.model.GameStateProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static mech.mania.engine.domain.game.board.Board.createDefaultBoard;
import static mech.mania.engine.domain.game.board.Board.createHomeBoard;

public class GameState {
    private long turnNumber;
    private Map<String, Board> boardNames;
    private Map<String, Player> playerNames;
    private Map<String, Monster> monsterNames;
    public GameChange stateChange;

    /**
     * Sets up a default GameState. Will eventually be expanded to set up the entire pvp board.
     */
    public GameState() {
        turnNumber = 0;
        boardNames = new HashMap<>();
        playerNames = new HashMap<>();
        monsterNames = new HashMap<>();
        stateChange = new GameChange();

        // @TODO: Create actual pvp board
        boardNames.put("pvp", Board.createDefaultBoard(20, 20, true, "pvp"));
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

    public void setTurnNumber(long turnNumber) {
        this.turnNumber = turnNumber;
    }

    /**
     * Creates a GameState object for use with a variety of tests.
     * @return a custom GameState
     */
    public static GameState createDefaultGameState() {
        GameState defaultGameState = new GameState();
        //adds a 30x30 pvp board with hard coded obstacles
        defaultGameState.boardNames.put("pvp", createDefaultBoard(30, 30, false, "pvp"));
        Tile[][] tempGrid = defaultGameState.getPvpBoard().getGrid();
        for(int i = 1; i < 29; ++i) {
            tempGrid[i][10].setType(Tile.TileType.IMPASSIBLE);
        }
        for(int i = 1; i < 29; ++i) {
            tempGrid[i][20].setType(Tile.TileType.IMPASSIBLE);
        }

        tempGrid[29][29].setType(Tile.TileType.VOID);

        //adds two portals to the pvp board
        defaultGameState.getPvpBoard().addPortal(new Position(10, 14, "pvp"));
        defaultGameState.getPvpBoard().addPortal(new Position(14, 1, "pvp"));

        //adds two players and moves them onto the pvp board
        defaultGameState.addNewPlayer("player1");
        defaultGameState.getPlayer("player1").setPosition(new Position(0, 4, "pvp"));
        defaultGameState.addNewPlayer("player2");
        defaultGameState.getPlayer("player2").setPosition(new Position(0, 24, "pvp"));


        //adds a single monster to the pvp board.
        //currently addNewMonster calls createDefaultMonster, so this may need changed depending on what happens to addNewMonster
        defaultGameState.addNewMonster(
                Monster.createDefaultMonster(0, 0, 0, 0, 0,
                        0, 0, 0, new Position(14, 25, "pvp"))
        );

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
        stateChange = new GameChange();

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
        stateChange.addCharacter(playerName);
    }

    public void addNewMonster(Monster monster){
        monsterNames.put(monster.getName(), monster);
    }
}