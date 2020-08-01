package mech.mania.engine.domain.game.pathfinding;

public class Cell{
    public int x, y;
    public int g, f;
    public boolean valid;
    public Cell parent;

    public Cell(int x, int y, int g, int f, boolean v){
        this.x = x;
        this.y = y;
        this.g = g;
        this.valid = v;
        parent = null;
    }
    public Cell(int x, int y, boolean v){
        this.x = x;
        this.y = y;
        g = Integer.MAX_VALUE;
        f = Integer.MAX_VALUE;
        this.valid = v;
        parent = null;
    }

    public boolean equals(Cell other){
        return this.x == other.x && this.y == other.y;
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
}