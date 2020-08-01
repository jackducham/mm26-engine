package mech.mania.engine.domain.game.pathfinding;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {
    public int compare(Cell x, Cell y){
        return x.f - y.f;
    }
}