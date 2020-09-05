package mech.mania.engine.domain.pathfinding;

import mech.mania.engine.domain.game.pathfinding.Cell;
import mech.mania.engine.domain.game.pathfinding.PathFinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class PathFindingTests {

    final int WIDTH = 10;
    final int HEIGHT = 10;
    private Cell[][] grid;
    private Cell start, end;

    /**
     * Setup before tests
     */
    @Before
    public void setup() {
        // Default grid is ROWSxCOLS and all valid
        grid = new Cell[WIDTH][HEIGHT];
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                grid[x][y] = new Cell(x, y, true);
            }
        }

        // Default start is (0, 0)
        start = grid[0][0];

        // Default end is also (0, 0) and expected to be overridden
        end = grid[0][0];
    }


    /**
     * Cleanup after tests
     */
    @After
    public void cleanup() {
    }


    /**
     * Test path finding when end == start
     */
    @Test
    public void testFindPathToSameCell() {
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertTrue(path.size() == 0);
    }

    /**
     * Test path finding when end is adjacent to start
     */
    @Test
    public void testFindPathToAdjacentEastCell(){
        end = grid[1][0];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(1, path.size());

        ArrayList<Cell> expectedPath = new ArrayList<Cell>();
        expectedPath.add(grid[1][0]);
        assertTrue(path.equals(expectedPath));
    }

    /**
     * Test path finding when end is adjacent to start
     */
    @Test
    public void testFindPathToAdjacentNorthCell(){
        end = grid[0][1];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(1, path.size());

        ArrayList<Cell> expectedPath = new ArrayList<Cell>();
        expectedPath.add(grid[0][1]);
        assertTrue(path.equals(expectedPath));
    }

    /**
     * Test path finding when end is adjacent to start
     */
    @Test
    public void testFindPathToAdjacentWestCell(){
        start = grid[1][1];
        end = grid[0][1];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(1, path.size());

        ArrayList<Cell> expectedPath = new ArrayList<Cell>();
        expectedPath.add(grid[0][1]);
        assertTrue(path.equals(expectedPath));
    }

    /**
     * Test path finding when end is adjacent to start
     */
    @Test
    public void testFindPathToAdjacentSouthCell(){
        start = grid[1][1];
        end = grid[1][0];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(1, path.size());

        ArrayList<Cell> expectedPath = new ArrayList<Cell>();
        expectedPath.add(grid[1][0]);
        assertTrue(path.equals(expectedPath));
    }

    /**
     * Test path finding when end far from start in a straight line
     */
    @Test
    public void testFindPathToFarCellInStraightLine(){
        end = grid[WIDTH -1][0];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(WIDTH -1, path.size());

        ArrayList<Cell> expectedPath = new ArrayList<Cell>();
        for(int i = 1; i < WIDTH; i++){
            expectedPath.add(grid[i][0]);
        }
        assertTrue(path.equals(expectedPath));
    }

    /**
     * Test path finding when end far from start NOT in a straight line
     */
    @Test
    public void testFindPathToFarCellNotInStraightLine(){
        end = grid[WIDTH -1][HEIGHT -1];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(WIDTH -1 + HEIGHT -1, path.size());
    }

    /**
     * Test path finding when end is unreachable
     */
    @Test
    public void testFindPathToUnreachableCell(){
        // Create horizontal wall
        for(int i = 0; i < WIDTH; i++){
            grid[i][1].valid = false;
        }

        end = grid[WIDTH -1][HEIGHT -1];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals(0, path.size());
    }

    /**
     * Test path finding when end is somewhat blocked by terrain
     */
    @Test
    public void testFindPathWithTerrainCell(){
        // Create horizontal wall with one chunk missing
        for(int i = 0; i < WIDTH - 1; i++){
            grid[i][1].valid = false;
        }

        end = grid[0][HEIGHT -1];
        List<Cell> path = PathFinder.findPath(grid, start, end);
        assertEquals((WIDTH - 1)*2 + HEIGHT - 1, path.size());
    }
}
