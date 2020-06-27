package mech.mania.engine.domain.messages;

import mech.mania.engine.Config;

public class CommandStartGameTurn implements Command {
    private int millisBetweenTurns;
    private int turn;

    public CommandStartGameTurn(int turn, int millisBetweenTurns) {
        this.millisBetweenTurns = millisBetweenTurns;
    }

    public CommandStartGameTurn(int turn) {
        this.turn = turn;
        this.millisBetweenTurns = Config.getMillisBetweenTurns();
    }

    public int getTurn() {
        return turn;
    }

    public int getMillisBetweenTurns() {
        return millisBetweenTurns;
    }
}
