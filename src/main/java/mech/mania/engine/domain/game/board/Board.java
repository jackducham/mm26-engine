package mech.mania.engine.domain.game.board;


import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.model.BoardProtos;


import java.util.*;

public class Board {
    private Tile[][] grid;
    private List<Position> portals;

    /**
     * Board constructor used to recreate boards from Protocol Buffers.
     * @param board the ProtoBuff being copied
     */
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
            portals.add(i, new Position(board.getPortals(i)));
        }

    }

    /**
     * Board constructor used to create blank boards.
     * @param xdim size of board's x dimension
     * @param ydim size of board's y dimension
     */
    private Board(int xdim, int ydim) {
        grid = new Tile[xdim][ydim];
        for(int i = 0; i < xdim; ++i) {
            for(int j = 0; j < ydim; ++j) {
                grid[i][j] = new Tile();
            }
        }
        portals = new ArrayList<>();
    }

    /**
     * Function used to create custom boards for use in testing.
     * @param xdim width of the board
     * @param ydim height of the board
     * @param createPortals whether or not portals should be created at opposite corners
     * @param id the board's and its player's id. Used when creating the position which is required to create portals
     * @return the finished custom board (this function does not add the board to the GameState)
     */
    public static Board createDefaultBoard(int xdim, int ydim, boolean createPortals, String id) {
        Board defaultBoard = new Board(xdim, ydim);

        if(createPortals) {
            defaultBoard.portals.add(new Position(0, 0, id));
            if(xdim > 1 && ydim > 1) {
                defaultBoard.portals.add(new Position(xdim - 1, ydim - 1, id));
            }
        }

        return defaultBoard;
    }

    /**
     * Creates a home board for a new player. Used by GameState when adding new players.
     * @param id the id of the player. required to correctly create the home board portal
     * @return a finished home board with default settings
     */
    public static Board createHomeBoard(String id) {
        int WIDTH = 20;
        int HEIGHT = 20;
        int PORTAL_X = 5;
        int PORTAL_Y = 10;

        //TODO: load this data from an XML file instead

        Board homeBoard = new Board(WIDTH , HEIGHT);
        homeBoard.portals.add(new Position(PORTAL_X, PORTAL_Y, id));

        return homeBoard;
    }

    /**
     * Creates a Protocol Buffer version of the board it is called on.
     * @return a Protocol Buffer board
     */
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

    public void addPortal(Position position) {
        portals.add(position);
        grid[position.getX()][position.getY()].setType(Tile.TileType.PORTAL);
    }
}
