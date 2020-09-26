package mech.mania.engine.domain.messages;

public class CommandStartTurn implements Command {
    private int turn;

    public CommandStartTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }
}
