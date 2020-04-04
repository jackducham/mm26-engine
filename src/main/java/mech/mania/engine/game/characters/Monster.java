package mech.mania.engine.game.characters;

import mech.mania.engine.game.GameState;
import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.items.*;

import mech.mania.engine.game.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Character {
    private List<Item> drops;

    public Monster(int experience, Position spawnPoint, Weapon weapon, List<Item> drops, String name) {
        super(experience, spawnPoint, weapon, name);
        this.drops = drops;
    }

    public Monster(CharacterProtos.Monster monsterProto) {
        super(
                monsterProto.getCharacter().getExperience(),
                new Position(monsterProto.getCharacter().getSpawnPoint()),
                new Weapon(monsterProto.getCharacter().getWeapon()),
                monsterProto.getCharacter().getName()
        );

        drops = new ArrayList<>(monsterProto.getDropsCount());
        for (int i = 0; i < monsterProto.getDropsCount(); i++) {
            ItemProtos.Item protoItem = monsterProto.getDrops(i);
            switch(protoItem.getItemCase()) {
                case CLOTHES:
                    drops.set(i, new Clothes(protoItem.getClothes()));
                    break;
                case HAT:
                    drops.set(i, new Hat(protoItem.getHat()));
                    break;
                case SHOES:
                    drops.set(i, new Shoes(protoItem.getShoes()));
                    break;
                case WEAPON:
                    drops.set(i, new Weapon(protoItem.getWeapon()));
                    break;
                case CONSUMABLE:
                    drops.set(i, new Consumable(protoItem.getMaxStack(), protoItem.getConsumable()));
            }
        }
    }

    private Position findPositionToMove(GameState gameState, Position destination) {
        List<Position> path = GameLogic.findPath(gameState, this.position, destination);
        Position toMove;
        if (path.size() < getSpeed()) {
            toMove = path.get(path.size() - 1);
        } else {
            // TODO remove cast once getSpeed returns int
            toMove = path.get((int)getSpeed() - 1);
        }
        return toMove;
    }

    public CharacterDecision makeDecision(GameState gameState) {
        if (taggedPlayersDamage.isEmpty()) {
            if (position.getX() != spawnPoint.getX() || position.getY() != spawnPoint.getY()) {
                Position toMove = findPositionToMove(gameState, spawnPoint);
                return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
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

                Position toMove = findPositionToMove(gameState, toAttack);
                return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
            }
        }
        return null;
    }

    @Override
    public void distributeRewards(GameState gameState) {
        super.distributeRewards(gameState);
        Board current = gameState.getBoard(position.getBoardID());
        Tile currentTile = current.getGrid()[position.getX()][position.getY()];
        currentTile.getItems().addAll(drops);
    }
}
