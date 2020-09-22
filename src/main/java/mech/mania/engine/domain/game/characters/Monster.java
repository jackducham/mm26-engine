package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.game.utils;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.ItemProtos;

import java.util.ArrayList;
import java.util.List;

import static mech.mania.engine.domain.game.pathfinding.PathFinder.findPath;

public class Monster extends Character {
    private final int aggroRange;
    private final List<Item> drops;


    // --------Constructors-------- //

    /**
     * Creates a Monster with the given stats.
     * @param name the monster's name
     * @param baseSpeed the monster's base movement speed
     * @param baseMaxHealth the monster's base maximum health
     * @param baseAttack the monster's base attack damage
     * @param baseDefense the monster's base defense
     * @param level the monster's level
     * @param spawnPoint the monster's spawn point, and the point it will leash back to
     * @param weapon the monster's weapon (used to apply on-hit effects)
     * @param drops the Items a monster will drop on kill (it will drop all Items on its drop list)
     */
    public Monster(String name, int baseSpeed, int baseMaxHealth, int baseAttack, int baseDefense,
                   int level, Position spawnPoint, Weapon weapon, int aggroRange, List<Item> drops) {
        super(name, baseSpeed, baseMaxHealth, baseAttack, baseDefense, level, spawnPoint, weapon);
        this.aggroRange = aggroRange;
        this.drops = drops;
    }


    // --------Proto Stuff-------- //

    /**
     * Creates a Monster object from a given Protocol Buffer.
     * @param monsterProto the protocol buffer being copied
     */
    public Monster(CharacterProtos.Monster monsterProto) {
        super(monsterProto.getCharacter());

        drops = new ArrayList<>(monsterProto.getDropsCount());
        aggroRange = monsterProto.getAggroRange();
        for (int i = 0; i < monsterProto.getDropsCount(); i++) {
            ItemProtos.Item protoItem = monsterProto.getDrops(i);
            switch(protoItem.getItemCase()) {
                case CLOTHES:
                    drops.add(i, new Clothes(protoItem.getClothes()));
                    break;
                case HAT:
                    drops.add(i, new Hat(protoItem.getHat()));
                    break;
                case SHOES:
                    drops.add(i, new Shoes(protoItem.getShoes()));
                    break;
                case WEAPON:
                    drops.add(i, new Weapon(protoItem.getWeapon()));
                    break;
                case CONSUMABLE:
                    drops.add(i, new Consumable(protoItem.getMaxStack(), protoItem.getConsumable()));
            }
        }
    }

    /**
     * Creates a Protocol Buffer from the Monster object this function is called on.
     * @return a protocol buffer representing the Monster object
     */
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
                monsterBuilder.setDrops(i, ((Consumable)curItem).buildProtoClassItem());
            }
        }
        monsterBuilder.setAggroRange(aggroRange);

        return monsterBuilder.build();
    }



    // --------Monster Decisions-------- //

    /**
     * Takes an input of a target position and returns the position to which the Monster should move on its next turn
     * in order to arrive at the target position in the shortest time.
     * @param gameState the current Game State
     * @param destination the position the monster wants to end up at
     * @return the position the Monster should move to
     */
    private Position findPositionToMove(GameState gameState, Position destination) {
        List<Position> path = findPath(gameState, this.position, destination);
        Position toMove;
        if (path.size() < getSpeed()) {
            toMove = path.get(path.size() - 1);
        } else {
            toMove = path.get(getSpeed() - 1);
        }
        return toMove;
    }

    /**
     * Calculates what the Monster should do on its next turn and passes this information to one of several helper
     * functions which generate and pass back a decision object.
     * @param gameState the current game state
     * @return the Monster's next decision
     */
    public CharacterDecision makeDecision(GameState gameState) {
        Player target = null;
        if (!taggedPlayersDamage.isEmpty()) {
            String highestDamageCharacter = getPlayerWithMostDamage();
            target = gameState.getPlayer(highestDamageCharacter);
        }

        if (target == null) {
            List<Character> inRange = utils.findEnemiesInRangeOfAttackByDistance(gameState, getPosition(), getName(), aggroRange);
            if(inRange != null) {
                for (Character character : inRange) {
                    if (character instanceof Player) {
                        target = (Player) character;
                        break;
                    }
                }
            }
        }

        // nothing in taggedPlayersDamage and no Players in agroRange
        if (target == null) {
            return moveToStartDecision(gameState);
        }

        Position toAttack = target.position;
        int manhattanDistance = position.manhattanDistance(toAttack);
        if (manhattanDistance <= weapon.getRange()) {
            return new CharacterDecision(CharacterDecision.decisionTypes.ATTACK, toAttack);
        } else {
            Position toMove = findPositionToMove(gameState, toAttack);
            return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
        }
    }

    /**
     * A helper function which generates a decision object when a Monster leashes: i.e. the Monster strays too far
     * from it spawn location or has no player left to chase.
     * @param gameState the current game state
     * @return the Monster's leash decision
     */
    public CharacterDecision moveToStartDecision(GameState gameState) {
        if (!position.equals(spawnPoint)) {
            Position toMove = findPositionToMove(gameState, spawnPoint);
            return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
        }
        return new CharacterDecision(CharacterDecision.decisionTypes.NONE, position, -1);
    }


    // --------Static Monster Creators-------- //
    //TODO: this function (and static count variable) should be copied and modified for each monster in the game.
    public static int DefaultMonsterQuantity = 0;

    /**
     * [only for use in testing]
     * Creates a "default" Monster. This function contains default values for the Monster it creates. The inputs should
     * be values between -1 and 1 which apply a (relatively) small modifier to the base stats of the Monster.
     * This allows some variation between each Monster of a given type and may not be used given the nature of the AI
     * competition. This feature could be modified to produce a range of difficulties for each Monster type instead.
     * [only for use in testing]
     * @param speedFactor slightly modifies the Monster's base speed
     * @param maxHealthFactor slightly modifies the Monster's base maximum health
     * @param attackFactor slightly modifies the Monster's base attack damage
     * @param defenseFactor slightly modifies the Monster's base defense
     * @param experienceFactor slightly modifies the Monster's base experience awarded upon kill
     * @param rangeFactor slightly modifies the Monster's base attack range
     * @param splashFactor slightly modifies the Monster's base splash damage size
     * @param numberOfDrops the number of Items this Monster instance will drop. (This function contains the full drop
     *                      list and will pick a number of Items from that list based on this input)
     * @param spawnPoint the Monster's spawn point
     * @return
     */
    public static Monster createDefaultMonster(double speedFactor, double maxHealthFactor, double attackFactor, double defenseFactor, double experienceFactor, double rangeFactor, double splashFactor, int level, int agroRange, int numberOfDrops, Position spawnPoint) {

        /*
        Default stats and their scaling factors:
        These stats are used to create a monster with slightly randomized stats based on random double inputs which should range between -1 and 1.
        The random values aren't directly calculated so as to allow the creation of a "standard" monster by inputting 0 for all factors,
        or to set a certain "difficulty" of monster by inputting other values such as 0.5, 1,  or even other ints like 2 or 3.
         */
        int baseSpeed = 5;
        int baseSpeedSpread = 1;

        int baseMaxHealth = 20;
        int baseMaxHealthSpread = 3;

        int baseAttack = 5;
        int baseAttackSpread = 1;

        int baseDefense = 2;
        int baseDefenseSpread = 1;

        int range = 3;
        int rangeSpread = 0;

        int splash = 0;
        int splashSpread = 0;

        int attack = 1;

        /*
        TODO: add Item drop generation
        My current plan is to have an item generator specific to this monster which will generate a number of items
        equal to the numberOfDrops argument and add them to the list of drops. Everything in this list will then be
        dropped upon the monster dieing.
         */

        List<Item> drops = new ArrayList<Item>();
        /*
        //here is where we will generate and add items:

        for(int i = 0; i < numberOfDrops; ++i) {
            //generate item and add it to the list
        }

        */


        StatusModifier defaultWeaponStats = new StatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        TempStatusModifier defaultOnHit = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Weapon defaultWeapon = new Weapon(defaultWeaponStats, range + (int)rangeFactor*rangeSpread, splash + (int)splashFactor*splashSpread, attack, defaultOnHit);

        Monster newMonster = new Monster("DefaultMonster" + DefaultMonsterQuantity,
                baseSpeed + (int)speedFactor*baseSpeedSpread,
                baseMaxHealth + (int)maxHealthFactor*baseMaxHealthSpread,
                baseAttack + (int)attackFactor*baseAttackSpread,
                baseDefense + (int)defenseFactor*baseDefenseSpread,
                level, spawnPoint, defaultWeapon, agroRange, drops);

        ++DefaultMonsterQuantity;
        return newMonster;
    }
}
