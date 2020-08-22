package mech.mania.engine.domain.game.pathfinding;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder {
    /**
     * Provides a list of positions from a start position to and end position. For use in determining
     * if a move is valid and sending to visualizer.
     * @param gameState current gameState
     * @param start position at beginning of desired path
     * @param end position at end of desired path
     * @return a List of positions along the path
     */
    public static List<Position> findPath(GameState gameState, Position start, Position end) {
        // Case if start and end do not reference positions on the same board
        // Case if start == end
        // Return empty list
        if (!start.getBoardID().equals(end.getBoardID()) || start.equals(end)) {
            return new ArrayList<>();
        }

        // Otherwise use A* path finding to find a path
        Tile[][] tileGrid = gameState.getBoard(start.getBoardID()).getGrid();
        final int WIDTH = tileGrid.length;
        final int HEIGHT = tileGrid[0].length;

        // Transforming tileGrid into A* cells
        Cell[][] grid = new Cell[WIDTH][HEIGHT];
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                // Mark cell as invalid (can't move there) if IMPASSIBLE or VOID
                Tile.TileType tileType = tileGrid[i][j].getType();
                boolean valid = tileType != Tile.TileType.IMPASSIBLE && tileType != Tile.TileType.VOID;
                grid[i][j] = new Cell(i, j, valid);
            }
        }

        Cell endCell = grid[end.getX()][end.getY()];
        Cell startCell = grid[start.getX()][start.getY()];

        // Call path finding with A* classes
        List<Cell> cellPath = findPath(grid, startCell, endCell);

        // Convert list back to Position objects
        List<Position> path = new ArrayList<>();
        for(Cell c : cellPath){
            path.add(new Position(c.x, c.y, start.getBoardID()));
        }
        return path;
    }

    /**
     * Provides a list of Cell objects from a start Cell to and end Cell within the Cell[] grid
     */
    public static List<Cell> findPath(Cell[][] grid, Cell start, Cell end){
        // If start is same as end or start is invalid return empty list
        if(start.equals(end) || !start.valid) return new ArrayList<>();

        // Shortcut for constant
        final int ROWS = grid.length;
        final int COLS = grid[0].length;

        // Mark start with g and f scores
        start.g = 0;
        start.f = calculateH(start, end);

        // Open list keeps track of current candidates in order of lowest f-score
        PriorityQueue<Cell> openList = new PriorityQueue<>(10, new CellComparator());
        openList.add(start);

        while(!openList.isEmpty()) {
            Cell current = openList.poll();

            if(current.equals(end)){
                return reconstructPath(start, current);
            }

            ArrayList<Cell> neighbors = new ArrayList<>();
            if (current.x + 1 < ROWS) {
                neighbors.add(grid[current.x + 1][current.y]);
            }
            if (current.y + 1 < COLS) {
                neighbors.add(grid[current.x][current.y + 1]);
            }
            if (current.x - 1 >= 0) {
                neighbors.add(grid[current.x - 1][current.y]);
            }
            if (current.y - 1 >= 0) {
                neighbors.add(grid[current.x][current.y - 1]);
            }

            // Check each neighboring cell
            for (Cell n : neighbors) {
                if(!n.valid) continue; // Skip impassable terrain

                int tentative_gValue = current.g + 1;
                if(tentative_gValue < n.g){
                    // This is the new best path to n
                    n.parent = current;
                    n.g = tentative_gValue;
                    n.f = n.g + calculateH(n, end);
                    if(!openList.contains(n)){
                        openList.add(n);
                    }
                }
            }
        }

        return new ArrayList<>(); // Goal is unreachable
    }

    /**
     * Return H heuristic for A* algorithm. In our case, this is Manhattan distance.
     * @param point
     * @param end
     * @return
     */
    public static int calculateH(Cell point, Cell end) {
        return Math.abs(point.x - end.x) + Math.abs(point.y - end.y);
    }

    /**
     * Trace back path using Cell parent field
     * @param start
     * @param end
     * @return
     */
    public static ArrayList<Cell> reconstructPath(Cell start, Cell end){
        Cell current = end;
        ArrayList<Cell> path = new ArrayList<>();
        while(!start.equals(current)){
            path.add(0, current);
            current = current.parent;
        }

        return path;
    }
}
