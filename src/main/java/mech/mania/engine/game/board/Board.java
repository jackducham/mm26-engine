package mech.mania.engine.game.board;

import mech.mania.engine.game.characters.Monster;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.characters.Position;

import java.util.List;

public class Board {
    private Tile[][] grid;
    private List<Monster> enemies;
    private List<Player> players;
    private List<Position> portals;
    private String BoardID;

    public Tile[][] getGrid() {
        return grid;
    }

    public List<Monster> getEnemies() {
        return enemies;
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
