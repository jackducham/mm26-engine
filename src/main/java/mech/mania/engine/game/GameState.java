package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;

import java.util.Map;

public class GameState {
    private Board pvpBoard;
    private Map<String, Board> playerIdToBoardMap;
    private int number = 0;

    public GameState() {
        this.number = 0;
    }

    public GameState(int number) {
        this.number = number;
    }

    /**
     * Checks the validity of parameters given by player
     * @param increment value to check
     * @return whether the parameter is valid or not
     */
    private boolean isValid(int increment) {
        // TODO: change to GameState specific stuff
        return increment < 1e5 && increment > 0;
    }

    /**
     * Getter method for number
     * @return only internal state of GameState: number
     */
    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("GameState{number=%d}", number);
    }
}