package mech.mania.engine.domain.messages;

public class CommandRestoreTurn implements Command{
    private final int turn;

    public CommandRestoreTurn(int turn){
        this.turn = turn;
    }

    public int getTurn() {
        return turn;
    }
}
