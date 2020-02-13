package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.Item;

import java.util.List;

public class Enemy extends Character {
    private List<Item> drops;

    public Enemy(List<Item> drops) {
        this.drops = drops;
    }
}
