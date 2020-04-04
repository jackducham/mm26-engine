package mech.mania.engine.game.board;

import mech.mania.engine.game.characters.Monster;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.characters.Position;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Tile[][] grid;
    private List<Monster> monsters;
    private List<Player> players;
    private List<Position> portals;
    private String BoardID;

    public Board(BoardProtos.Board board) {
        int rows = board.getRows();
        int cols = board.getColumns();
        grid = new Tile[rows][cols];
        BoardID = board.getBoardId();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Tile(board.getGrid(r * c + c));
            }
        }

        monsters = new ArrayList<>(board.getMonstersCount());
        for (int i = 0; i < board.getMonstersCount(); i++) {
            monsters.set(i, new Monster(board.getMonsters(i)));
        }

        players = new ArrayList<>(board.getPlayersCount());
        for (int i = 0; i < board.getPlayersCount(); i++) {
            players.set(i, new Player(board.getPlayers(i)));
        }

        portals = new ArrayList<>(board.getPortalsCount());
        for (int i = 0; i < board.getPortalsCount(); i++) {
            portals.set(i, new Position(board.getPortals(i)));
        }

    }

    public Tile[][] getGrid() {
        return grid;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Position> getPortals() {
        return portals;
    }

    public String getBoardID() {
        return BoardID;
    }
}
