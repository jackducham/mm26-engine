package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.Item;

import java.util.List;

public class Enemy extends Character {
    private int goldReward;
    private List<Item> drops;

    public Enemy(int goldReward, List<Item> drops) {
        this.goldReward = goldReward;
        this.drops = drops;
    }
}
