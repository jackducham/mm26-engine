package mech.mania.engine.game.computation;

import mech.mania.engine.game.GameState;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.characters.Position;

import java.util.ArrayList;
import java.util.List;

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
        Tile[][] grid = gameState.getBoard(start.getBoardID()).getGrid();


        // current will change depending on the f value of the cells around it, it only begins at 'start'
        Position current = start;

        // 2 Lists, closed list and open list
        // Closed list is all the cells that have been visited, maybe a boolean 2d array that will keep track of visited cells
        // Open list which keeps track of f values for certain cells, this will likely be the list that will be returned at the end maybe?
        // boolean[][] closedList = grid;
        // ArrayList openList = new ArrayList<Position>();

        // DO this 4 times, to check cell above, below, left, and right of current cell (which would be Position point)
        if (isValid(gameState, current)) {

            // If at destination
            if (isDestination(current, end)) {
                // Add this cell to list then return list
            }

            // Check to see if it is on the closed list already and whether it is blocked, if neither then continue otherwise ignore this cell
            else if (isImpassible(gameState, current) == false) {
                // do some wacky stuff here
                // check this cells new f g and h values, compare to the other 3 and find the best one?
            }
        }
    }

    // helper function to check whether given cell is valid
    public static boolean isValid(GameState gameState, Position point) {
        return (point.getX() >= 0) && (point.getX() < gameState.getBoard(point.getBoardID()).getGrid().length) &&
                (point.getY() > 0) && (point.getY() < gameState.getBoard(point.getBoardID()).getGrid()[0].length);
    }

    // helper function to check whether the given cell is impassible or not
    public static boolean isImpassible(GameState gameState, Position point) {
        return gameState.getBoard(point.getBoardID()).getGrid()[point.getX()][point.getY()].getType() == Tile.TileType.IMPASSIBLE;

    }

    // helper function to check whether the destination has been reached
    public static boolean isDestination(Position point, Position end) {
        return (point == end);
    }

    // Assume 3 values per node, 'f' , 'g', 'h'. f = g + h where g is 1 (distance between tiles) and h is total distance from that point to end tile
    // h is the heuristic
    public static double calculateH(GameState gameState, Position point, Position end) {
        return ((double) Math.sqrt(((point.getX() - end.getX()) * (point.getX() - end.getX())) + ((point.getY() - end.getY()) * (point.getY() - end.getY()))));
    }
}
