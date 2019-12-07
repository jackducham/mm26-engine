package mech.mania.engine.game.board;

import mech.mania.engine.game.characters.Enemy;
import mech.mania.engine.game.characters.Player;

import java.util.List;

public class Board {
    private Tile[][] grid;
    private List<Enemy> enemies;
    private List<Player> players;
    private List<Position> portals;

    public List<Position> getPortals() {
        return portals;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
