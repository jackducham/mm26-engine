package mech.mania.engine.domain.messages;

import mech.mania.engine.Config;

public class CommandStartTurn implements Command {
    private int millisBetweenTurns;
    private int turn;

    public CommandStartTurn(int turn, int millisBetweenTurns) {
        this.turn = turn;
        this.millisBetweenTurns = millisBetweenTurns;
    }

    public CommandStartTurn(int turn) {
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
