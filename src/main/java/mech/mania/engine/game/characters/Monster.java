package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.Item;

import java.util.List;

public class Monster extends Character {
    private List<Item> drops;

    public Monster(List<Item> drops) {
        this.drops = drops;
    }
}
