package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.BoardProtos;
import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.characters.CharacterProtos;
import mech.mania.engine.game.characters.*;
import mech.mania.engine.game.model.GameStateProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GameState {
    private long turnNumber;
    private Map<String, Board> boardNames;
    private Map<String, Player> playerNames;
    private Map<String, Monster> monsterNames;

    public Board getPvpBoard() {
        return boardNames.get("pvp");
    }

    public GameState() {
        turnNumber = 0;
        boardNames = new HashMap<>();
        playerNames = new HashMap<>();
        monsterNames = new HashMap<>();
    }

    public Board getBoard(String boardId){
        if (boardNames.containsKey(boardId)) {
            return boardNames.get(boardId);
        }
        return null;
    }

    public Player getPlayer(String playerId) {
        if (playerNames.containsKey(playerId)) {
            return playerNames.get(playerId);
        }
        return null;
    }

    public Character getCharacter(String characterId) {
        if(playerNames.containsKey(characterId)) {
            return playerNames.get(characterId);
        }

        if(monsterNames.containsKey(characterId)) {
            return monsterNames.get(characterId);
        }

        return null;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<Player>();
        for(Player player: playerNames.values()) {
            players.add(player);
        }
        return players;
    }

    public Monster getMonster(String monsterId) {
        if (monsterNames.containsKey(monsterId)) {
            return monsterNames.get(monsterId);
        }
        return null;
    }

    public List<Monster> getMonstersOnBoard(String boardId) {
        if (!boardNames.containsKey(boardId)) {
            return new ArrayList<>();
        }

        Predicate<Monster> byBoard = monster -> monster.getPosition().getBoardID().equals(boardId);

        return monsterNames.values().stream().filter(byBoard)
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersOnBoard(String boardId) {
        if (!boardNames.containsKey(boardId)) {
            return new ArrayList<>();
        }

        Predicate<Player> byBoard = player -> player.getPosition().getBoardID().equals(boardId);

        return playerNames.values().stream().filter(byBoard)
                .collect(Collectors.toList());
    }

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
}