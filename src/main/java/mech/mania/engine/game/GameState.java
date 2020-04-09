package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.characters.CharacterProtos;
import mech.mania.engine.game.characters.*;
import mech.mania.engine.game.model.GameStateProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {
    private long turnNumber;
    private Board pvpBoard;
    private ArrayList<Board> playerBoards;
    private Map<String, Character> characterNames;

    public Board getPvpBoard() {
        return pvpBoard;
    }

    public Board getBoard(String boardID){
        if(pvpBoard.getBoardID().equals(boardID)){return pvpBoard;}
        for(Board b : playerBoards) {
            if (b.getBoardID().equals(boardID)) {
                return b;
            }
        }
        return null;
    }

    public GameStateProtos.GameState buildProtoClass() {
        GameStateProtos.GameState.Builder gameStateBuilder = GameStateProtos.GameState.newBuilder();
        gameStateBuilder.setStateId(turnNumber);

        gameStateBuilder.setPvpBoard(pvpBoard.buildProtoClass());

        for (int i = 0; i < playerBoards.size(); i++) {
            gameStateBuilder.addPlayerBoards(i, playerBoards.get(i).buildProtoClass());
        }

        int monsterIndex = 0;
        int playerIndex = 0;
        for (Character character : characterNames.values()) {
            if (character instanceof Monster) {
                // TODO: add allMonsters field to GameStateProto
                gameStateBuilder.addAllMonsters(monsterIndex, ((Monster) character).buildProtoClassMonster());
                monsterIndex++;
            } else {
                // TODO: add allPlayers field to GameStateProto
                gameStateBuilder.addAllPlayers(playerIndex, ((Player)character).buildProtoClassPlayer());
                playerIndex++;
            }
        }

        return gameStateBuilder.build();
    }

    public GameState(GameStateProtos.GameState gameStateProto) {
        this.pvpBoard = new Board(gameStateProto.getPvpBoard());
        playerBoards = new ArrayList<>(gameStateProto.getPlayerBoardsCount());
        for (int i = 0; i < gameStateProto.getPlayerBoardsCount(); i++) {
            playerBoards.set(i, new Board(gameStateProto.getPlayerBoards(i)));
        }

        turnNumber = gameStateProto.getStateId();

        characterNames = new HashMap<>();

        // TODO: add allMonsters field to GameStateProto
        List<CharacterProtos.Monster> allMonsters = gameStateProto.getAllMonsters();
        for (CharacterProtos.Monster monsterProto : allMonsters) {
            Character newMonster = new Monster(monsterProto);
            characterNames.put(newMonster.getName(), newMonster);
        }

        // TODO: add allPlayers field to GameStateProto
        List<CharacterProtos.Player> allPlayers = gameStateProto.getAllPlayers();
        for (CharacterProtos.Player playerProto : allPlayers) {
            Character newPlayer = new Player(playerProto);
            characterNames.put(newPlayer.getName(), newPlayer);
        }
    }
}