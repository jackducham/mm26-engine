package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.BoardProtos;
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
            gameStateBuilder.putBoardNames(boardID, boardNames.get(boardID));
        }

        int playerIndex = 0;
        for (Player player : playerNames.values()) {
            gameStateBuilder.addPlayerNames(playerIndex, player.buildProtoClassPlayer());
            playerIndex++;
        }

        int monsterIndex = 0;
        for (Monster monster : monsterNames.values()) {
            gameStateBuilder.addMonsterNames(monsterIndex, monster.buildProtoClassMonster());
            monsterIndex++;
        }

        return gameStateBuilder.build();
    }

    public GameState(GameStateProtos.GameState gameStateProto) {
        boardNames = new HashMap<>(gameStateProto.getPlayerBoardsCount());

        Map<String, BoardProtos.Board> boardProtoMap = gameStateProto.getBoardNames();

        for (String boardId : boardProtoMap.keySet()) {
            boardNames.put(boardId, new Board(boardProtoMap.get(boardId)));
        }

        turnNumber = gameStateProto.getStateId();

        playerNames = new HashMap<>();
        monsterNames = new HashMap<>();

        // TODO: add allMonsters field to GameStateProto
        List<CharacterProtos.Monster> allMonsters = gameStateProto.getMonsterNames();
        for (CharacterProtos.Monster monsterProto : allMonsters) {
            Monster newMonster = new Monster(monsterProto);
            monsterNames.put(newMonster.getName(), newMonster);
        }

        // TODO: add allPlayers field to GameStateProto
        List<CharacterProtos.Player> allPlayers = gameStateProto.getPlayerNames();
        for (CharacterProtos.Player playerProto : allPlayers) {
            Player newPlayer = new Player(playerProto);
            playerNames.put(newPlayer.getName(), newPlayer);
        }
    }
}