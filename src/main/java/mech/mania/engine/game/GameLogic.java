package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.Tile;

import mech.mania.engine.game.characters.Monster;
import mech.mania.engine.game.characters.Position;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.characters.CharacterDecision;

import mech.mania.engine.game.items.Item;
import mech.mania.engine.game.items.TempStatusModifier;
import mech.mania.engine.game.items.Weapon;

import mech.mania.engine.server.communication.player.model.PlayerProtos.PlayerDecision;

import java.util.*;

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
    public static GameState doTurn(GameState gameState, Map<String, PlayerDecision> decisions) {
        // ========== NOTES & TODOS ========== \\
        // TODO: update GameState using List<PlayerDecision>
        // TODO: convert PlayerDecision to CharacterDecision
        // Note: VisualizerChange will be sent later via Main.java, so no need to worry about that here


        // ========== HANDLE DEAD PLAYERS ========== \\
        List<Player> deadPlayers = new LinkedList<Player>();
        //keeping a list of dead players, so they can be respawned after everything else has happened.

        for (Player player: gameState.getAllPlayers()) {
            if (player.getCurrentHealth() <= 0) {
                deadPlayers.add(player);
                decisions.remove(player);
            }
        }


        // ========== CONVERT DECISIONS ========== \\
        //I (DREW) HAVE NO IDEA IF THIS IS RIGHT. SOMEONE ELSE SHOULD TAKE A LOOK AT THIS.
        Map<String, CharacterDecision> cDecisions = new HashMap<String, CharacterDecision>();
        for (Map.Entry<String, PlayerDecision> entry : decisions.entrySet()) {
            CharacterDecision newDecision = new CharacterDecision(entry.getValue());
            cDecisions.put(entry.getKey(), newDecision);
        }


        // ========== SORT DECISIONS ========== \\
        Map<String, CharacterDecision> inventoryActions = new HashMap<String, CharacterDecision>();
        Map<String, CharacterDecision> attackActions = new HashMap<String, CharacterDecision>();
        Map<String, CharacterDecision> movementActions = new HashMap<String, CharacterDecision>();

        for (Map.Entry<String, CharacterDecision> entry : cDecisions.entrySet()) {
            if (entry.getValue().getDecision() == CharacterDecision.decisionTypes.PICKUP
                || entry.getValue().getDecision() == CharacterDecision.decisionTypes.EQUIP
                || entry.getValue().getDecision() == CharacterDecision.decisionTypes.DROP) {
                inventoryActions.put(entry.getKey(), entry.getValue());

            } else if (entry.getValue().getDecision() == CharacterDecision.decisionTypes.ATTACK) {
                attackActions.put(entry.getKey(), entry.getValue());

            } else {
                movementActions.put(entry.getKey(), entry.getValue());
            }
        }


        // ========== HANDLE INVENTORY ACTIONS ========== \\
        for (Map.Entry<String, CharacterDecision> entry : inventoryActions.entrySet()) {

        }


        // ========== HANDLE ATTACK ACTIONS ========== \\
        for (Map.Entry<String, CharacterDecision> entry : attackActions.entrySet()) {

        }


        // ========== HANDLE MOVEMENT ACTIONS ========== \\
        for (Map.Entry<String, CharacterDecision> entry : movementActions.entrySet()) {

        }


        // ========== RESPAWN DEAD PLAYERS ========== \\
        for (Player player : deadPlayers) {

        }


        return gameState;
    }

    public static void processDecision(GameState gameState, Character character, CharacterDecision decision) {
        if (decision == null) {
            return;
        }
        Position actionPosition = decision.getActionPosition();
        int index = decision.getIndex();
        switch (decision.getDecision()) {
            case ATTACK:
                processAttack(gameState, character, actionPosition);
                break;
            case MOVE:
                moveCharacter(gameState, character, actionPosition);
                break;
            case PORTAL:
                usePortal(gameState, character, index);
                break;
            case EQUIP:
                Player player = (Player) character;
                player.equipItem(index);
                break;
            case DROP:
                player = (Player) character;
                dropItem(gameState, player, index);
                break;
            case PICKUP:
                player = (Player) character;
                pickUpItem(gameState, player, decision.getIndex());
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
            return new ArrayList<>();
        }
        List<Position> path = findPath(gameState, character.getPosition(), targetPosition); //Default return value might be empty, or might be of size one
        if(path.size() > character.getSpeed()) {
            return new ArrayList<>();
        }
        character.setPosition(targetPosition);
        return path;
    }

    // ============================= PORTAL FUNCTIONS ================================================================== //

    /**
     * Checks whether a player can take the UsePortal action.
     * @param gameState current gameState
     * @param player player to be moved
     * @return true if the action can be taken, false otherwise
     */
    public static boolean canUsePortal(GameState gameState, Character player) {
        if(player instanceof Monster) return false; // Only players can take portals

        //checks the portals on the player's current board
        for(int i = 0; i < gameState.getBoard(player.getPosition().getBoardID()).getPortals().size(); i++) {
            if(player.getPosition() == gameState.getBoard(player.getPosition().getBoardID()).getPortals().get(i)) {
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
    public static boolean usePortal(GameState gameState, Character player, int portalIndex) {
        if(canUsePortal(gameState, player)) {

            //sends player to home board
            if(portalIndex == -1) {
                player.setPosition(gameState.getBoard(player.getSpawnPoint().getBoardID()).getPortals().get(0));
                return true;
            }

            //checks for out of bounds indices
            if(portalIndex >= gameState.getPvpBoard().getPortals().size() || portalIndex < -1) {
                return false;
            }

            //handles usual cases
            player.setPosition(gameState.getPvpBoard().getPortals().get(portalIndex));
            return true;
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
    public static boolean validateAttack(GameState gameState, Character character, Position attackCoordinate) {
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
    public static Map<Position, Integer> returnAffectedPositions(GameState gameState, Character character, Position attackCoordinate) {
        if (!validateAttack(gameState, character, attackCoordinate)) {
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
    public static void processAttack(GameState gameState, Character attacker, Position attackCoordinate) {
        Board board = gameState.getBoard(attackCoordinate.getBoardID());
        TempStatusModifier onHitEffect = attacker.getWeapon().getOnHitEffect();
        List<Monster> monsters = gameState.getMonstersOnBoard(attackCoordinate.getBoardID());
        List<Player> players = gameState.getPlayersOnBoard(attackCoordinate.getBoardID());
        Map<Position, Integer> affectedPositions = returnAffectedPositions(gameState, attacker, attackCoordinate);

        // Character gave invalid attack position
        if (affectedPositions.isEmpty()) {
            return;
        }

        for (Player player: players) {
            if (player == attacker) {
                continue;
            }
            Position playerPos = player.getPosition();
            if (affectedPositions.containsKey(playerPos)) {
                player.takeDamage(
                                    attacker.getWeapon().getDamage(),
                                    onHitEffect,
                                    attacker.getName()
                );
            }
        }

        for (Monster monster: monsters) {
            if (monster == attacker) {
                continue;
            }
            Position playerPos = monster.getPosition();
            if (affectedPositions.containsKey(playerPos)) {
                monster.addEffect(onHitEffect);
                monster.takeDamage(
                        attacker.getWeapon().getDamage(),
                        onHitEffect,
                        attacker.getName()
                );
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
    public static boolean pickUpItem(GameState gameState, Player player, int index) {
        Tile tile = getTileAtPosition(gameState, player.getPosition());
        if (tile == null) {
            return false;
        }
        if (index < 0 || index > tile.getItems().size()) {
            return false;
        }
        int playerInventoryIndex = player.getFreeInventoryIndex();
        if (playerInventoryIndex == -1) {
            return false;
        }

        Item item = tile.getItems().get(index);
        tile.removeItem(index);
        player.setInventory(playerInventoryIndex, item);
        return true;
    }

    /**
     * Removes one item from a Player's inventory and adds it to the items on a tile.
     * @param gameState current gameState
     * @param player the player dropping items
     * @param index the index of the item in the player's inventory which is being dropped
     * @return true if successful
     */
    public static boolean dropItem(GameState gameState, Player player, int index) {
        Tile currentTile = getTileAtPosition(gameState, player.getPosition());
        if (index < 0 || index > player.getInventorySize()) {
            return false;
        }
        if (player.getInventory()[index] != null) {
            Item item = player.getInventory()[index];
            player.setInventory(index, null);
            currentTile.addItem(item);
        }
        return true;
    }

    // ============================= GENERAL HELPER FUNCTIONS ========================================================== //

    /**
     * Provides the Tile at a given Position.
     * @param gameState current gameState
     * @param position position of the Tile to be retrieved
     * @return the Tile at the given position
     */
    public static Tile getTileAtPosition(GameState gameState, Position position) {
        if (!validatePosition(gameState, position)) {
            return null;
        }
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
