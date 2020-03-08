package mech.mania.engine.game.characters;

import mech.mania.engine.game.GameState;
import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.items.Item;

import mech.mania.engine.game.GameLogic;

import java.util.List;
import mech.mania.engine.game.items.Weapon;

public class Monster extends Character {
    private List<Item> drops;

    public Monster(int experience, Position spawnPoint, Weapon weapon, List<Item> drops, String name) {
        super(experience, spawnPoint, weapon, name);
        this.drops = drops;
    }

    public CharacterDecision makeDecision(GameState gameState) {
        if (taggedPlayersDamage.isEmpty()) {
            if (position.getX() != spawnPoint.getX() || position.getY() != spawnPoint.getY()) {
                //TODO: call non-existent pathfinder code to navigate home as much as possible
                // spawnPoint should be passed into pathfinder code; this returns the actual point
                // to move to
                return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, spawnPoint);
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

            Position toAttack = highestDamagePlayer.getPosition();

            int manhattanDistance = GameLogic.calculateManhattanDistance(position, toAttack);
            if (manhattanDistance <= weapon.getRange()) {
                return new CharacterDecision(CharacterDecision.decisionTypes.ATTACK, toAttack);
            } else {
                // TODO: call non-existent pathfinder code to navigate as close as possible
                // toAttack should be passed into pathfinder code; this returns the actual point
                // to move to
                return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toAttack);
            }
        }
        return null;
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
