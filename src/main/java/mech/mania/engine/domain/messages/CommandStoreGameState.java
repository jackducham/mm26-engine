package mech.mania.engine.domain.messages;

import mech.mania.engine.domain.game.GameState;

public class CommandStoreGameState implements Command {

    private int turn;
    private GameState gameState;

    public CommandStoreGameState(int turn, GameState gameState) {
        this.turn = turn;
        this.gameState = gameState;
    }

    public int getTurn() {
        return turn;
    }

    public GameState getGameState() {
        return gameState;
    }
}
