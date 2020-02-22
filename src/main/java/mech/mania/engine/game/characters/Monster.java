package mech.mania.engine.game.characters;

import java.util.HashMap;
import java.util.Map;
import mech.mania.engine.game.items.Item;

import java.util.List;

public class Monster extends Character {
    private List<Item> drops;
    private Map<Player, Double> taggedPlayersDamage;

    public Monster(List<Item> drops) {
        this.drops = drops;
        taggedPlayersDamage = new HashMap<Player, Double>();
    }

    public void takeDamage(double damage, Player player) {
        if (taggedPlayersDamage.containsKey(player)) {
            taggedPlayersDamage.put(player, taggedPlayersDamage.get(player) + damage);
        }
    }

}
