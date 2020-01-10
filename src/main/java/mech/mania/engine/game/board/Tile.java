package mech.mania.engine.game.board;

import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private Character character;
    private List<Item> items;

    public Tile() {
        character = null;
        items = new ArrayList<>();
        type = TileType.BLANK;
    }

    private TileType type;

    public enum TileType {
        BLANK, IMPASSIBLE, INN, PORTAL
    }
}
