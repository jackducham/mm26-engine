package mech.mania.engine.game.characters;

import static java.lang.Math.max;

import java.util.HashMap;
import java.util.Map;
import mech.mania.engine.game.GameState;
import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.items.Item;

import java.util.List;
import mech.mania.engine.game.items.Weapon;

public class Monster extends Character {
    private List<Item> drops;

    public Monster(int experience, Position spawnPoint, Weapon weapon, List<Item> drops) {
        super(experience, spawnPoint, weapon);
        this.drops = drops;
    }

    // TODO: return a Decision object
    public void makeDecision(GameState gameState) {
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

    @Override
    public void distributeRewards(GameState gameState) {
        super.distributeRewards(gameState);
        Board current = gameState.getBoard(position.getBoardID());
        Tile currentTile = current.getGrid()[position.getX()][position.getY()];
        // TODO: update how items are dropped
        currentTile.getItems().addAll(drops);
    }
}
