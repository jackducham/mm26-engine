package mech.mania.engine.game.characters;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;
import mech.mania.engine.game.items.Item;

import java.util.List;

public class Monster extends Character {
    private List<Item> drops;
    private Map<Player, Double> taggedPlayersDamage;
    private Position spawnPoint;

    public Monster(List<Item> drops, Position spawnPoint) {
        this.drops = drops;
        taggedPlayersDamage = new HashMap<Player, Double>();
        this.spawnPoint = spawnPoint;
    }

    public void takeDamage(double physicalDamage, double magicalDamage, Player player) {
        double actualDamage = max(0, physicalDamage - getPhysicalDefense())
                                + max(0, magicalDamage - getMagicalDefense());

        if (taggedPlayersDamage.containsKey(player)) {
            taggedPlayersDamage.put(player, taggedPlayersDamage.get(player) + actualDamage);
        } else {
            taggedPlayersDamage.put(player, actualDamage);
        }

        //TODO: update the monster's HP based on damage taken. Method does not yet exist.
    }

    // this should return a Decision- to be implemented
    public void makeDecision() {
        if (taggedPlayersDamage.isEmpty()) {
            if (position.getX() != spawnPoint.getX() || position.getY() != spawnPoint.getY()) {
                //TODO: call non-existent pathfinder code to navigate home
            }
        } else {
            Player highestDamagePlayer = null;
            double highestDamage = -1;
            for (Player player : taggedPlayersDamage.keySet()) {
                if (taggedPlayersDamage.get(player) > highestDamage) {
                    highestDamagePlayer = player;
                    highestDamage = taggedPlayersDamage.get(player);
                }
            }

            Position toNavigate = highestDamagePlayer.getPosition();
            //TODO: call non-existent pathfinder code to navigate as close to player as possible

            double euclideanDistance = Math.sqrt(
                                                Math.pow(
                                                        getPosition().getX() - toNavigate.getX(),
                                                        2
                                                    )
                                                + Math.pow(
                                                        getPosition().getY() - toNavigate.getY(),
                                                        2
                                                    )
            );
            if (euclideanDistance <= weapon.getRange()) {
                //TODO: attack player with most power possible
            }
        }

        //TODO: return Decision instance
    }

    public void onDeath() {
        //TODO
    }

    /**
     * Called by the game when a player dies or leaves the board
     * @param toRemove Player to remove
     */
    public void removePlayer(Player toRemove) {
        taggedPlayersDamage.remove(toRemove);
    }
}
