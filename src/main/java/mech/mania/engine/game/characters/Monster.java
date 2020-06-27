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

    public Monster(String name, int baseSpeed, int baseMaxHealth, int baseAttack, int baseDefense,
                   int experience, Position spawnPoint, Weapon weapon, List<Item> drops) {
        super(name, baseSpeed, baseMaxHealth, baseAttack, baseDefense, experience, spawnPoint, weapon);
        this.drops = drops;
    }

    // @TODO: Update CharacterProtos
    public Monster(CharacterProtos.Monster monsterProto) {
        super(
                monsterProto.getCharacter().getName(),
                monsterProto.getCharacter().getBaseSpeed(),
                monsterProto.getCharacter().getBaseMaxHealth(),
                monsterProto.getCharacter().getBaseAttack(),
                monsterProto.getCharacter().getBaseDefense(),
                monsterProto.getCharacter().getExperience(),
                new Position(monsterProto.getCharacter().getSpawnPoint()),
                new Weapon(monsterProto.getCharacter().getWeapon())
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

    public CharacterProtos.Monster buildProtoClassMonster() {
        CharacterProtos.Character characterProtoClass = super.buildProtoClassCharacter();
        CharacterProtos.Monster.Builder monsterBuilder = CharacterProtos.Monster.newBuilder();
        monsterBuilder.mergeCharacter(characterProtoClass);
        for (int i = 0; i < drops.size(); i++) {
            Item curItem = drops.get(i);
            if (curItem instanceof Clothes) {
                monsterBuilder.setDrops(i, ((Clothes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Hat) {
                monsterBuilder.setDrops(i, ((Hat)curItem).buildProtoClassItem());
            } else if (curItem instanceof Shoes) {
                monsterBuilder.setDrops(i, ((Shoes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Weapon) {
                monsterBuilder.setDrops(i, ((Weapon)curItem).buildProtoClassItem());
            } else if (curItem instanceof Consumable) {
                monsterBuilder.setDrops(i, ((Consumable)curItem).buildProtoClass());
            }
        }

        return monsterBuilder.build();
    }

    private Position findPositionToMove(GameState gameState, Position destination) {
        List<Position> path = GameLogic.findPath(gameState, this.position, destination);
        Position toMove;
        if (path.size() < getSpeed()) {
            toMove = path.get(path.size() - 1);
        } else {
            toMove = path.get(getSpeed() - 1);
        }
        return toMove;
    }

    public CharacterDecision makeDecision(GameState gameState) {
        if (taggedPlayersDamage.isEmpty()) {
            return moveToStartDecision(gameState);
        } else {
            String highestDamagePlayer = getPlayerWithMostDamage();
            Player player = gameState.getPlayer(highestDamagePlayer);

            // @TODO: Minor bug - if highestDamagePlayer is a Monster, Monster will move to start
            if (player == null) {
                return moveToStartDecision(gameState);
            }

            Position toAttack = player.position;

            int manhattanDistance = GameLogic.calculateManhattanDistance(position, toAttack);
            if (manhattanDistance <= weapon.getRange()) {
                return new CharacterDecision(CharacterDecision.decisionTypes.ATTACK, toAttack);
            } else {
                Position toMove = findPositionToMove(gameState, toAttack);
                return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
            }
        }
    }

    public CharacterDecision moveToStartDecision(GameState gameState) {
        // @TODO: Can update if statement to Position .equals() method
        if (position.getX() != spawnPoint.getX() || position.getY() != spawnPoint.getY()) {
            Position toMove = findPositionToMove(gameState, spawnPoint);
            return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
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
