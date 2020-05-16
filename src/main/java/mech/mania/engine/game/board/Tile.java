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
                    items.set(i, new Clothes(protoItem.getClothes()));
                    break;
                case HAT:
                    items.set(i, new Hat(protoItem.getHat()));
                    break;
                case SHOES:
                    items.set(i, new Shoes(protoItem.getShoes()));
                    break;
                case WEAPON:
                    items.set(i, new Weapon(protoItem.getWeapon()));
                    break;
                case CONSUMABLE:
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

    public BoardProtos.Tile buildProtoClass() {
        BoardProtos.Tile.Builder tileBuilder = BoardProtos.Tile.newBuilder();
        switch (type) {
            case VOID:
                tileBuilder.setTileType(BoardProtos.Tile.TileType.VOID);
                break;
            case BLANK:
                tileBuilder.setTileType(BoardProtos.Tile.TileType.BLANK);
                break;
            case IMPASSIBLE:
                tileBuilder.setTileType(BoardProtos.Tile.TileType.IMPASSIBLE);
                break;
            case PORTAL:
                tileBuilder.setTileType(BoardProtos.Tile.TileType.PORTAL);
                break;
        }

        for (int i = 0; i < items.size(); i++) {
            Item curItem = items.get(i);
            if (curItem instanceof Clothes) {
                tileBuilder.setItems(i, ((Clothes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Hat) {
                tileBuilder.setItems(i, ((Hat)curItem).buildProtoClassItem());
            } else if (curItem instanceof Shoes) {
                tileBuilder.setItems(i, ((Shoes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Weapon) {
                tileBuilder.setItems(i, ((Weapon)curItem).buildProtoClassItem());
            } else if (curItem instanceof Consumable) {
                tileBuilder.setItems(i, ((Consumable)curItem).buildProtoClass());
            }
        }

        return tileBuilder.build();
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
