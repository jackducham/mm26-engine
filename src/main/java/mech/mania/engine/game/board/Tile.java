package mech.mania.engine.game.board;

import mech.mania.engine.game.items.*;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private List<Item> items;
    public enum TileType{
        VOID, BLANK, IMPASSIBLE, PORTAL
    }
    private TileType type;

    public Tile() {
        items = new ArrayList<>();
        type = TileType.BLANK;
    }

    public Tile(BoardProtos.Tile tileProto) {
        items = new ArrayList<>(tileProto.getItemsCount());
        for (int i = 0; i < tileProto.getItemsCount(); i++) {
            ItemProtos.Item protoItem = tileProto.getItems(i);
            switch(protoItem.getItemCase()) {
                case CLOTHES:
                    // TODO: create Clothes constructor
                    items.set(i, new Clothes(protoItem.getClothes()));
                    break;
                case HAT:
                    // TODO: create Hat constructor
                    items.set(i, new Hat(protoItem.getHat()));
                    break;
                case SHOES:
                    // TODO: create Shoes constructor
                    items.set(i, new Shoes(protoItem.getShoes()));
                    break;
                case WEAPON:
                    // TODO: create Weapon constructor
                    items.set(i, new Weapon(protoItem.getWeapon()));
                    break;
                case CONSUMABLE:
                    // TODO: create Consumable constructor
                    items.set(i, new Consumable(protoItem.getMaxStack(), protoItem.getConsumable()));
            }
        }

        switch (tileProto.getTileType()) {
            case VOID:
                type = TileType.VOID;
                break;
            case BLANK:
                type = TileType.BLANK;
                break;
            case IMPASSIBLE:
                type = TileType.IMPASSIBLE;
                break;
            case PORTAL:
                type = TileType.PORTAL;
                break;
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public TileType getType() {
        return type;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(int index) {
        items.remove(index);
    }
}
