package mech.mania.engine.domain.computation;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Position;

import javax.annotation.Priority;
import java.util.*;

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
        // TODO: Remove these comments before making a pull request
        // internally doesnt matter how they get to their spot, visualizer needs to be given a path.
        // player function just changes the position. Create a new function to just make a path for visualizer.
        // new package similar to -> server -> communication -> visualizer called movement instead and do something
        // with the package that communicates a list of positions that will give to visualizer.

        // TODO: Implement A* path finding. Make sure that if start==end, this function returns an EMPTY list.
        // return new ArrayList<Position>();



        // Case if start and end do not reference positions on the same board
        // Case if start == end
        // Return empty list
        if (start.getBoardID() != end.getBoardID() || start.equals(end)) {
            return new ArrayList<Position>();
        }

        // Otherwise use A* path finding to find a path
        // This will break is getBoardID returns an ID that does not exist cause it'll be null
        Tile[][] tileGrid = gameState.getBoard(start.getBoardID()).getGrid();
        final int ROWS = tileGrid.length;
        final int COLS = tileGrid[0].length;

        // 2 Lists, closed list and open list
        // Closed list is all the cells that have been visited, maybe a boolean 2d array that will keep track of visited cells
        // Open list which keeps track of f values for certain cells, this will likely be the list that will be returned at the end maybe?

        // Transforming tileGrid into A* cells
        Cell[][] grid = new Cell[ROWS][COLS];
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                grid[i][j] = new Cell(i, j, tileGrid[i][j].getType() != Tile.TileType.IMPASSIBLE);
            }
        }

        Cell endCell = grid[end.getY()][end.getY()];
        Cell startCell = grid[start.getX()][start.getY()];
        startCell.g = 0;
        startCell.f = calculateH(startCell, endCell);

        // If start is not a valid position, return empty list
        if(!startCell.valid) return new ArrayList<Position>();

        // Closed list keeps track of visited cells
        boolean[][] closedList = new boolean[ROWS][COLS];

        // Open list keeps track of current candidates in order of lowest f-value
        PriorityQueue<Cell> openList = new PriorityQueue<>(10, new CellComparator());
        openList.add(startCell);

        while(!openList.isEmpty()) {
            Cell current = openList.poll();

            if(isDestination(current, end)){
                return reconstructPath(start, current);
            }

            ArrayList<Cell> neighbors = new ArrayList<Cell>();
            if (isValid(gameState, new Position(current.x + 1, current.y, start.getBoardID()))) {
                neighbors.add(grid[current.x + 1][current.y]);
            }
            if (isValid(gameState, new Position(current.x, current.y + 1, start.getBoardID()))) {
                neighbors.add(grid[current.x][current.y + 1]);
            }
            if (isValid(gameState, new Position(current.x - 1, current.y, start.getBoardID()))) {
                neighbors.add(grid[current.x - 1][current.y]);
            }
            if (isValid(gameState, new Position(current.x, current.y - 1, start.getBoardID()))) {
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
                    n.f = n.g + calculateH(n, endCell);
                    if(!openList.contains(n)){
                        openList.add(n);
                    }
                }
            }
        }

        return new ArrayList<Position>(); // Goal is unreachable
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

    public static ArrayList<Position> reconstructPath(Position start, Cell endCell){
        Cell current = endCell;
        ArrayList<Position> path = new ArrayList<>();
        while(! (current.x == start.getX() && current.y == start.getY())){
            path.add(0, new Position(current.x, current.y, start.getBoardID()));
            current = current.parent;
        }

        return path;
    }
}
