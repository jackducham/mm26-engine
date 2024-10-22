package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.game.GameLogic;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.factory.ItemFactory;
import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.CharacterProtos;

import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static mech.mania.engine.domain.game.pathfinding.PathFinder.findPath;

public class Monster extends Character {
    public static final int REVIVE_TICKS = 15;

    private final int aggroRange;

    // --------Constructors-------- //

    /**
     * Creates a Monster with the given stats.
     * @param name the monster's name
     * @param sprite the filepath to this monster's sprite
     * @param baseSpeed the monster's base movement speed
     * @param baseMaxHealth the monster's base maximum health
     * @param baseAttack the monster's base attack damage
     * @param baseDefense the monster's base defense
     * @param level the monster's level
     * @param spawnPoint the monster's spawn point, and the point it will leash back to
     * @param weapon the monster's weapon (used to apply on-hit effects)
     */
    public Monster(String name, String sprite, int baseSpeed, int baseMaxHealth, int baseAttack, int baseDefense,
                   int level, Position spawnPoint, Weapon weapon, int aggroRange) {
        super(name, sprite, baseSpeed, baseMaxHealth,
                baseAttack, baseDefense, level, spawnPoint, weapon, REVIVE_TICKS);
        this.aggroRange = aggroRange;
    }

    public Monster(Monster other) {
        super(other.getName(), other.getSprite(), other.baseSpeed, other.baseMaxHealth, other.baseAttack,
                other.baseDefense, other.level, new Position(other.spawnPoint), new Weapon(other.weapon), REVIVE_TICKS);
        this.aggroRange = other.getAggroRange();
    }

    // --------Proto Stuff-------- //

    /**
     * Creates a Monster object from a given Protocol Buffer.
     * @param monsterProto the protocol buffer being copied
     */
    public Monster(CharacterProtos.Monster monsterProto) {
        super(monsterProto.getCharacter(), REVIVE_TICKS);
        this.aggroRange = monsterProto.getAggroRange();
    }

    /**
     * Creates a Protocol Buffer from the Monster object this function is called on.
     * @return a protocol buffer representing the Monster object
     */
    public CharacterProtos.Monster buildProtoClassMonster() {
        CharacterProtos.Character characterProtoClass = super.buildProtoClassCharacter();
        CharacterProtos.Monster.Builder monsterBuilder = CharacterProtos.Monster.newBuilder();
        monsterBuilder.mergeCharacter(characterProtoClass);
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
        List<Position> path = findPath(gameState, getPosition(), destination);

        if(path.size() == 0){
            return this.position;
        }

        Position pos;
        if (path.size() < getSpeed()) {
            pos = path.get(path.size() - 1);
        } else {
            pos = path.get(getSpeed() - 1);
        }
        return pos;
    }

    /**
     * Takes an input of a target position and returns the position the Monster should attack
     * in order to damage the Player at target
     * @param gameState the current Game State
     * @param target the position of the Player the monster wants to attack
     * @return the position the Monster should attack
     */
    private Position findPositionToTarget(GameState gameState, Position target) {
        return getPositionInRange(gameState, getPosition(), target, getWeapon().getRange());
    }

    private void addPlayersToAggroRangeTable(GameState gameState) {
        List<Character> inRange = GameLogic.findEnemiesInRangeByDistance(gameState, getPosition(), getName(), aggroRange);
        if(inRange != null) {
            for (Character character : inRange) {
                if (!character.isDead() && character instanceof Player && !taggedPlayersDamage.containsKey(character.getName())) {
                    taggedPlayersDamage.put(character.getName(), 0);
                }
            }
        }
    }

    /**
     * Calculates what the Monster should do on its next turn and passes this information to one of several helper
     * functions which generate and pass back a decision object.
     * @param gameState the current game state
     * @return the Monster's next decision
     */
    public CharacterDecision makeDecision(GameState gameState) {
        addPlayersToAggroRangeTable(gameState);

        Player target = null;
        LinkedHashMap<String, Integer> sortedPlayers = getPlayerWithMostDamage();
        if (sortedPlayers != null) {
            Iterator<Map.Entry<String,Integer>> it = sortedPlayers.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String,Integer> playerEntry = it.next();
                Player player = gameState.getPlayer(playerEntry.getKey());
                if (player != null && !player.isDead()) {
                    target = player;
                    break;
                }
            }
        }

        // nothing in taggedPlayersDamage and no Players in aggroRange
        if (target == null || weapon == null) {
            return moveToStartDecision(gameState);
        }

        Position targetPos = target.position;
        int manhattanDistance = position.manhattanDistance(targetPos);
        if (manhattanDistance <= weapon.getRange() + weapon.getSplashRadius()) {
            Position toTarget = findPositionToTarget(gameState, targetPos);
            if (toTarget != null) {
                return new CharacterDecision(CharacterDecision.decisionTypes.ATTACK, toTarget);
            }
        }

        Position toMove = findPositionToMove(gameState, targetPos);
        return new CharacterDecision(CharacterDecision.decisionTypes.MOVE, toMove);
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
        StatusModifier defaultWeaponStats = new StatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        TempStatusModifier defaultOnHit = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Weapon defaultWeapon = new Weapon(defaultWeaponStats, range + (int)rangeFactor*rangeSpread, splash + (int)splashFactor*splashSpread, attack, defaultOnHit, "");

        Monster newMonster = new Monster("DefaultMonster" + DefaultMonsterQuantity,
                "",
                baseSpeed + (int)speedFactor*baseSpeedSpread,
                baseMaxHealth + (int)maxHealthFactor*baseMaxHealthSpread,
                baseAttack + (int)attackFactor*baseAttackSpread,
                baseDefense + (int)defenseFactor*baseDefenseSpread,
                level, spawnPoint, defaultWeapon, agroRange);

        ++DefaultMonsterQuantity;
        return newMonster;
    }


    @Override
    public void distributeRewards(GameState gameState) {
        // Pass our XP to attackers
        super.distributeRewards(gameState);

        // Handle item drops and player stats
        Board current = gameState.getBoard(position.getBoardID());
        Tile currentTile = current.getGrid()[position.getX()][position.getY()];

        // Drop between 2 and 4 items
        Random rand = new Random();
        int numberOfDrops = 2 + rand.nextInt(3);
        for (int i = 0; i < numberOfDrops; ++i) {
            currentTile.getItems().add(ItemFactory.generateItem(this.getLevel()));
        }

        //iterates through every player still on the taggedPlayersDamage map.
        for (Map.Entry<String, Integer> entry : taggedPlayersDamage.entrySet()) {
            Player currentPlayer = gameState.getPlayer(entry.getKey());
            if (currentPlayer != null && !currentPlayer.isDead()
                    && taggedPlayersDamage.get(currentPlayer.getName()) != 0) {
                currentPlayer.getExtraStats().incrementMonstersSlain();
            }
        }
    }

    /**
     * Can use this function to determine the position to attack within a range
     * @param gameState
     * @param currentPosition
     * @param targetPosition
     * @param range
     * @return position within range along the path
     */
    public static Position getPositionInRange(GameState gameState, Position currentPosition, Position targetPosition, int range) {
        if (currentPosition == null || targetPosition == null) {
            return null;
        }

        Board board = gameState.getBoard("pvp");

        if (!currentPosition.getBoardID().equals(targetPosition.getBoardID())) {
            return null;
        }
        int distance = currentPosition.manhattanDistance(targetPosition);
        if (distance <= range) {
            return targetPosition;
        }

        int currX = currentPosition.getX();
        int currY = currentPosition.getY();
        int targetX = targetPosition.getX();
        int targetY = targetPosition.getY();
        int xDiff = Math.abs(targetX - currX);
        int xDir = (targetX - currX) < 0 ? -1 : 1;
        int yDir = (targetY - currY) < 0 ? -1 : 1;

        int yOffset;
        int xOffset;
        if (xDiff >= range) {
            yOffset = currY;
            xOffset = currX + (xDir * range);
        } else {
            yOffset = currY + yDir * (range - xDiff);
            xOffset = currX + xDir * xDiff;
        }

        while(xOffset <= Math.max(currX, targetX) &&
                xOffset >= Math.min(currX, targetX) &&
                yOffset <= Math.max(currY, targetY) &&
                yOffset >= Math.min(currY, targetY)) {
            if (board.getGrid()[xOffset][yOffset].getType() != Tile.TileType.VOID) {
                return new Position(xOffset, yOffset, currentPosition.getBoardID());
            }
            yOffset += yDir;
            xOffset += -xDir;
        }
        return null;
    }

    public int getAggroRange() {
        return aggroRange;
    }
}
