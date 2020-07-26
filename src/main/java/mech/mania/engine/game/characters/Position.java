package mech.mania.engine.game.characters;

public class Position {
    private int x;
    private int y;
    private String board_id;

    public Position(int x, int y, String board_id) {
        this.x = x;
        this.y = y;
        this.board_id = board_id;
    }

    public Position(CharacterProtos.Position positionProto) {
        x = positionProto.getX();
        y = positionProto.getY();
        board_id = positionProto.getBoardId();
    }

    public CharacterProtos.Position buildProtoClass() {
        CharacterProtos.Position.Builder positionBuilder = CharacterProtos.Position.newBuilder();
        positionBuilder.setX(x);
        positionBuilder.setY(y);
        positionBuilder.setBoardId(board_id);

        return positionBuilder.build();
    }

    public boolean equals(Position other) {
        if(this.x != other.x || this.y != other.y || this.board_id != other.board_id) {
            return false;
        }

        return true;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getBoardID() {
        return board_id;
    }

    public void setBoardID(String board_id) {
        this.board_id = board_id;
    }
}