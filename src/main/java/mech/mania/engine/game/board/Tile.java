package mech.mania.engine.game.board;

import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private List<Item> items;
    public enum TileType{
        BLANK, IMPASSIBLE, INN, PORTAL;
    }
    private TileType type;

    public Tile() {
        items = new ArrayList<Item>();
        type = TileType.BLANK;
    }
}
