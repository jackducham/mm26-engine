package mech.mania.engine.domain.computation;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Position;

import javax.annotation.Priority;
import java.util.*;

public class PathFinder {

    /**
     * Provides a list of Cell objects from a start Cell to and end Cell within the Cell[] grid
     */
    public static List<Cell> findPath(Cell[][] grid, Cell start, Cell end){
        // If start is same as end or start is invalid return empty list
        if(start.equals(end) || !start.valid) return new ArrayList<Cell>();

        start.g = 0;
        start.f = calculateH(start, end);

        // Closed list keeps track of visited cells
        final int ROWS = grid.length;
        final int COLS = grid[0].length;
        boolean[][] closedList = new boolean[ROWS][COLS];

        // Open list keeps track of current candidates in order of lowest f-value
        PriorityQueue<Cell> openList = new PriorityQueue<>(10, new CellComparator());
        openList.add(start);

        while(!openList.isEmpty()) {
            Cell current = openList.poll();

            if(current.equals(end)){
                return reconstructPath(start, current);
            }

            ArrayList<Cell> neighbors = new ArrayList<Cell>();
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

            // DO this 4 times, to check cell above, below, left, and right of current cell (which would be Position point)
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

        return new ArrayList<Cell>(); // Goal is unreachable
    }

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
            return new ArrayList<Position>();
        }

        // Otherwise use A* path finding to find a path
        // This will break if getBoardID returns an ID that does not exist cause it'll be null
        Tile[][] tileGrid = gameState.getBoard(start.getBoardID()).getGrid();
        final int WIDTH = tileGrid.length;
        final int HEIGHT = tileGrid[0].length;

        // 2 Lists, closed list and open list
        // Closed list is all the cells that have been visited
        // Open list which keeps track of the next cells to visit

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

        Cell endCell = grid[end.getY()][end.getY()];
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

    // helper function to check whether given cell is valid
    public static boolean isValid(GameState gameState, Position point) {
        return  (point.getX() >= 0) && (point.getX() < gameState.getBoard(point.getBoardID()).getGrid().length) &&
                (point.getY() > 0) && (point.getY() < gameState.getBoard(point.getBoardID()).getGrid()[0].length);
    }

    // helper function to check whether the given cell is impassible or not
    public static boolean isImpassible(GameState gameState, Position point) {
        return gameState.getBoard(point.getBoardID()).getGrid()[point.getX()][point.getY()].getType() == Tile.TileType.IMPASSIBLE;

    }

    // helper function to check whether the destination has been reached
    public static boolean isDestination(Cell point, Position end) {
        return (point.x == end.getX() && point.y == end.getY());
    }

    // Assume 3 values per node, 'f' , 'g', 'h'. f = g + h where g is 1 (distance between tiles) and h is total distance from that point to end tile
    // h is the heuristic
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
