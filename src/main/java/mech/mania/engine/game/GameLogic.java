package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.characters.Monster;
import mech.mania.engine.game.characters.Position;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.characters.CharacterDecision;
import mech.mania.engine.game.items.Item;
import mech.mania.engine.game.items.TempStatusModifier;
import mech.mania.engine.game.items.Weapon;
import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * A class to execute the game logic.
 */
public class GameLogic {
    /**
     * Executes the logic of one turn given a starting {@link GameState} and a list of {@link PlayerDecision}s.
     * @param gameState The initial game state.
     * @param decisions A list of player decisions.
     * @return the resulting {@link GameState}.
     */
    public static GameState doTurn(GameState gameState, List<PlayerDecision> decisions) {
        // TODO: update GameState using List<PlayerDecision>
        // Note: VisualizerChange will be sent later via Main.java, so no need to worry about that here
        return gameState;
    }

    public static void processDecision(GameState gameState, Character character, CharacterDecision decision) {
        if (decision == null) {
            return;
        }
        Position actionPosition = decision.getActionPosition();
        switch (decision.getDecision()) {
            case ATTACK:
                addAttackEffectToCharacters(gameState, character, actionPosition);
                break;
            case MOVE:
                // TODO pending method implementation
                moveCharacter(gameState, character, actionPosition);
                break;
            case PORTAL:
                // TODO pending method implementation
                usePortal(gameState, character, actionPosition);
                break;
            case EQUIP:
                Player player = (Player)character;
                player.equipItem(decision.getInventoryIndex());
                break;
            case DROP:
                Tile actionTile = getTileAtPosition(gameState, actionPosition);
                player = (Player) character;
                player.dropItem(actionTile, decision.getInventoryIndex());
                break;
            case PICKUP:
                actionTile = getTileAtPosition(gameState, actionPosition);
                player = (Player)character;
                player.pickUpItem(actionTile, decision.getInventoryIndex());
                break;
        }
    }

    /**
     * Moves a given character to a given position if it is reachable. Only used for moving characters within a single board.
     * @param gameState current gameState
     * @param character player to be moved
     * @param targetPosition position the player should be moved to
     * @return A list of position which make up the path used to reach the target
     */
    public static List<Position> moveCharacter(GameState gameState, Character character, Position targetPosition) {
        if (!validatePosition(gameState, targetPosition)) {
            return new ArrayList<Position>();
        }
        List<Position> path = findPath(gameState, character.getPosition(), targetPosition);
        if(path.size() > character.getSpeed()) {
            return new ArrayList<Position>();
        }
        character.setPosition(targetPosition);
        return path;
        //Default return value might be empty, or might be of size one
    }

    // ============================= PORTAL FUNCTIONS ================================================================== //

    /**
     * Checks whether a player can take the UsePortal action.
     * @param gameState current gameState
     * @param player player to be moved
     * @return true if the action can be taken, false otherwise
     */
    public static boolean canUsePortal(GameState gameState, Player player) {
        for(int i = 0; i < gameState.getBoard(player.getPosition().getBoardID()).getPortals().size(); i++) {
            if(player.getPosition() == gameState.getBoard(player.getPosition().getBoardID()).getPortals().get(i)) {
                return true;
            }
        }
        for(int i = 0; i < gameState.getPvpBoard().getPortals().size(); i++) {
            if(player.getPosition() == gameState.getPvpBoard().getPortals().get(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves a player to the portal at given index, if they are able.
     * @param gameState current gameState
     * @param player player using a portal
     * @param portalIndex index of target portal on pvpBoard or -1 if targeting the home portal
     * @return true if successful
     */
    public static boolean usePortal(GameState gameState, Player player, int portalIndex) {
        if(canUsePortal(gameState, player)) {
            if(portalIndex == -1) {
                player.setPosition(gameState.getBoard(player.getPosition().getBoardID()).getPortals().get(0));
                return true;
            }
            for(int i = 0; i < gameState.getPvpBoard().getPortals().size(); i++) {
                if(i == portalIndex) {
                    player.setPosition(gameState.getBoard(player.getPosition().getBoardID()).getPortals().get(i));
                    return true;
                }
            }
        }
        return false;
    }

    // ============================= ATTACKING HELPER FUNCTIONS ======================================================== //

    /**
     * Validate whether character's weapon isn't null and if target Position is within range and on the board
     * @param character character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @param gameState current gameState
     * @return true if attackCoordinate is valid, false otherwise
     */
    public static boolean validateAttack(Character character, Position attackCoordinate, GameState gameState) {
        Weapon playerWeapon = character.getWeapon();
        if (playerWeapon == null) {
            return false;
        }

        if (!validatePosition(gameState, attackCoordinate)) {
            return false;
        }

        if (calculateManhattanDistance(character.getPosition(), attackCoordinate) > playerWeapon.getRange()) {
            return false;
        }

        return true;
    }

    /**
     * Provides a list of position affected by a given player's attack.
     * @param character character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @param gameState current gameState
     * @return hashmap of Positions that would get attacked by the player's weapon
     */
    public static Map<Position, Integer> returnAffectedPositions(Character character, Position attackCoordinate, GameState gameState) {
        if (!validateAttack(character, attackCoordinate, gameState)) {
            return null;
        }
        Weapon weapon = character.getWeapon();
        int radius = weapon.getSplashRadius();
        Map<Position, Integer> affectedPositions = new HashMap<>();

        int centerX = attackCoordinate.getX();
        int centerY = attackCoordinate.getY();

        int xMin = ((centerX - radius) < 0) ? 0 : (centerX - radius);
        int yMin = ((centerY - radius) < 0) ? 0 : (centerY - radius);

        for (int x = xMin; x <= centerX + radius; x++) {
            for (int y = yMin; y <= centerY + radius; y++) {
                Position position = new Position(x, y);
                if (calculateManhattanDistance(position, attackCoordinate) <= radius && validatePosition(gameState, position)) {
                    affectedPositions.put(position, 1);
                }
            }
        }

        return affectedPositions;
    }

    /**
     * Applies Weapon's onHitEffect to the TempStatusModifier of all characters within the range of the attackCoordinate
     * @param attacker character doing the attacking
     * @param attackCoordinate coordinate to attack
     * @param gameState current gamestate
     */
    public static void addAttackEffectToCharacters(GameState gameState, Character attacker, Position attackCoordinate) {
        Board board = gameState.getPvpBoard();
        TempStatusModifier onHitEffect = attacker.getWeapon().getOnHitEffect();
        List<Monster> enemies = board.getEnemies();
        List<Player> players = board.getPlayers();
        Map<Position, Integer> affectedPositions = returnAffectedPositions(attacker, attackCoordinate, gameState);

        // Character gave invalid attack position
        if (affectedPositions == null) {
            return;
        }

        for (Player player: players) {
            if (player == attacker) {
                continue;
            }
            Position playerPos = player.getPosition();
            if (affectedPositions.containsKey(playerPos)) {
                player.addEffect(onHitEffect);
            }
        }

        for (Monster monster: enemies) {
            if (monster == attacker) {
                continue;
            }
            Position playerPos = monster.getPosition();
            if (affectedPositions.containsKey(playerPos)) {
                monster.addEffect(onHitEffect);
            }
        }

        return;
    }


    // ============================= ITEM FUNCTIONS ==================================================================== //

    /**
     * Adds an item to the Player's inventory and removes it from the tile.
     * @param gameState current gameState
     * @param player player to give the item to
     * @param index index of the item in the tile's items that the player is picking up
     * @return true if successful
     */
    public boolean pickUpItem(GameState gameState, Player player, int index) {
        Tile currentTile = getTileAtPosition(gameState, player.getPosition());
        for(int i = 0; i < player.getInventory().length; i++) {
            if(player.getInventory()[i] == null) {
                Item temp = currentTile.getItems().get(index);
                currentTile.getItems().remove(index);
                player.getInventory()[i] = temp;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes one or more items from a Player's inventory and adds them to the items on a tile.
     * @param gameState current gameState
     * @param player the player dropping items
     * @param itemsToDrop the indices of the items in the player's inventory which are being dropped
     * @return true if successful
     */
    public boolean dropItems(GameState gameState, Player player, int[] itemsToDrop) {
        Tile currentTile = getTileAtPosition(gameState, player.getPosition());
        for(int i = 0; i < itemsToDrop.length; i++) {
            if(player.getInventory()[itemsToDrop[i]] != null) {
                Item temp = player.getInventory()[itemsToDrop[i]];
                player.getInventory()[itemsToDrop[i]] = null;
                currentTile.getItems().add(temp);
            }
        }
        return true;
    }

    // ============================= GENERAL HELPER FUNCTIONS ========================================================== //

    /**
     * Checks whether given player is on given board.
     * @param player The target player.
     * @param board The target board.
     * @return true if the player is on the board, false otherwise.
     */
    public static boolean isPlayerOnBoard(GameState gameState, Player player, Board board) {
        return (board.getPlayers().contains(player));
    }

    /**
     * Provides the Tile at a given Position.
     * @param gameState current gameState
     * @param position position of the Tile to be retrieved
     * @return the Tile at the given position
     */
    public static Tile getTileAtPosition(GameState gameState, Position position) {
        return gameState.getBoard(position.getBoardID()).getGrid()[position.getX()][position.getY()];
    }

    /**
     * Checks whether position is within the bounds of the board
     * @param gameState
     * @param position position to validate
     * @return true, if position is valid
     */
    public static boolean validatePosition(GameState gameState, Position position) {
        Board board = gameState.getBoard(position.getBoardID());
        if(board == null){return false;}

        if (position.getX() > board.getGrid()[0].length || position.getX() < 0) {
            return false;
        }

        if (position.getY() > board.getGrid().length || position.getY() < 0) {
            return false;
        }

        return board.getGrid()[position.getY()][position.getX()].getType() != Tile.TileType.VOID;
    }

    /**
     * @param pos1 first position
     * @param pos2 second position
     * @return Manhattan Distance between pos1 and pos2
     */
    public static int calculateManhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getY()) + Math.abs(pos1.getY() - pos2.getY());
    }

    /**
     * Provides a list of positions from a start position to and end position.
     * @param gameState current gameState
     * @param start position at beginning of desired path
     * @param end position at end of desired path
     * @return a List of positions along the path
     */
    public static List<Position> findPath(GameState gameState, Position start, Position end) {
        return new ArrayList<Position>();
    }
}