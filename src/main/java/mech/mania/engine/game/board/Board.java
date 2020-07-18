package mech.mania.engine.game.board;


import mech.mania.engine.game.characters.Position;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Board {
    private Tile[][] grid;
    private List<Position> portals;

    public Board(BoardProtos.Board board) {
        int rows = board.getRows();
        int cols = board.getColumns();
        grid = new Tile[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Tile(board.getGrid(r * c + c));
            }
        }

        portals = new ArrayList<>(board.getPortalsCount());
        for (int i = 0; i < board.getPortalsCount(); i++) {
            portals.set(i, new Position(board.getPortals(i)));
        }

    }

    /**
     * Board constructor used in testing to create small boards
     * @param dimension
     */
    public Board(int dimension) {
        grid = new Tile[dimension][dimension];
        portals = new ArrayList<>();
    }

    public BoardProtos.Board buildProtoClass() {
        BoardProtos.Board.Builder boardBuilder = BoardProtos.Board.newBuilder();

        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                boardBuilder.addGrid(r * grid[c].length + c, grid[r][c].buildProtoClass());
            }
        }

        for (int i = 0; i < portals.size(); i++) {
            boardBuilder.addPortals(i, portals.get(i).buildProtoClass());
        }

        boardBuilder.setRows(grid.length);
        boardBuilder.setColumns(grid[0].length);

        return boardBuilder.build();
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public List<Position> getPortals() {
        return portals;
    }

}
