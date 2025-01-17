package mech.mania.engine.domain.game.board;

import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.BoardProtos;
import mech.mania.engine.domain.model.ItemProtos;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    private List<Item> items;
    public enum TileType{
        VOID, BLANK, IMPASSIBLE, PORTAL
    }
    private TileType type;

    // Filenames for sprites for this tile
    public String groundSprite;
    public String aboveSprite;

    /**
     * Constructs a default Tile of type BLANK.
     */
    public Tile() {
        this.items = new ArrayList<>();
        this.type = TileType.VOID;
        this.groundSprite = "";
        this.aboveSprite = "";
    }

    public Tile(Tile t){
        this.items = new ArrayList<>();
        for(Item item : t.getItems()){
            this.items.add(item.copy());
        }
        this.type = t.type;
        this.groundSprite = t.groundSprite;
        this.aboveSprite = t.aboveSprite;
    }

    /**
     * Creates a Tile based on a Protocol Buffer.
     * @param tileProto the protocol buffer being copied
     */
    public Tile(BoardProtos.Tile tileProto) {
        items = new ArrayList<>();
        for (int i = 0; i < tileProto.getItemsCount(); i++) {
            ItemProtos.Item protoItem = tileProto.getItems(i);
            switch(protoItem.getItemCase()) {
                case CLOTHES:
                    items.add(new Clothes(protoItem.getClothes()));
                    break;
                case HAT:
                    items.add(new Hat(protoItem.getHat()));
                    break;
                case SHOES:
                    items.add(new Shoes(protoItem.getShoes()));
                    break;
                case WEAPON:
                    items.add(new Weapon(protoItem.getWeapon()));
                    break;
                case CONSUMABLE:
                    items.add(new Consumable(protoItem.getConsumable()));
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

        groundSprite = tileProto.getGroundSprite();
        aboveSprite = tileProto.getAboveSprite();
    }

    /**
     * Creates a Protocol Buffer based on the Tile this function is called on.
     * @return a completed protocol buffer
     */
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
                tileBuilder.addItems(((Clothes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Hat) {
                tileBuilder.addItems(((Hat)curItem).buildProtoClassItem());
            } else if (curItem instanceof Shoes) {
                tileBuilder.addItems(((Shoes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Weapon) {
                tileBuilder.addItems(((Weapon)curItem).buildProtoClassItem());
            } else if (curItem instanceof Consumable) {
                tileBuilder.addItems(((Consumable)curItem).buildProtoClassItem());
            } else if (curItem instanceof Accessory) {
                tileBuilder.addItems(((Accessory)curItem).buildProtoClassItem());
            }
        }

        tileBuilder.setGroundSprite(groundSprite);
        tileBuilder.setAboveSprite(aboveSprite);

        return tileBuilder.build();
    }

    /**
     * Getter for the list of Items on a Tile instance.
     * @return the list of all Items on the Tile
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Getter for the type of a Tile instance.
     * @return the type of the Tile
     */
    public TileType getType() {
        return type;
    }

    /**
     * Setter for the type of a Tile instance.
     * @param type the type the tile will be set to
     */
    public void setType(TileType type) {
        this.type = type;
    }

    /**
     * Adds an Item to the list of Items on a Tile instance.
     * @param item the Item to be added
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Removes the Item at a given index in the list of Items on a Tile instance.
     * @param index the index of the Item to be removed
     */
    public void removeItem(int index) {
        items.remove(index);
    }
}
