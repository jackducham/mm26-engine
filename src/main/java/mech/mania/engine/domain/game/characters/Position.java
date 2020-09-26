package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.model.CharacterProtos;

import java.util.Objects;

public class Position {
    private int x;
    private int y;
    private String board_id;

    public Position(Position p){
        this.x = p.x;
        this.y = p.y;
        this.board_id = p.board_id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x &&
                y == position.y &&
                Objects.equals(board_id, position.board_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, board_id);
    }

    public int manhattanDistance(Position other) {
        if (!this.getBoardID().equals(other.getBoardID())) {
            return Integer.MAX_VALUE;
        }
        int x = Math.abs(this.x - other.getX());
        int y = Math.abs(this.y - other.getY());
        return x + y;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", board_id='" + board_id + '\'' +
                '}';
    }
}
