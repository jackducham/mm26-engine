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

import java.util.ArrayList;
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
        int index = decision.getIndex();
        switch (decision.getDecision()) {
            case ATTACK:
                addAttackEffectToCharacters(gameState, character, actionPosition);
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
                dropItems(gameState, player, index);
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
    public static void addAttackEffectToCharacters(GameState gameState, Character attacker, Position attackCoordinate) {
        Board board = gameState.getPvpBoard();
        TempStatusModifier onHitEffect = attacker.getWeapon().getOnHitEffect();
        List<Monster> enemies = board.getMonsters();
        List<Player> players = board.getPlayers();
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
     * Removes one or more items from a Player's inventory and adds them to the items on a tile.
     * @param gameState current gameState
     * @param player the player dropping items
     * @param index the index of the item in the player's inventory which is being dropped
     * @return true if successful
     */
    public static boolean dropItems(GameState gameState, Player player, int index) {
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
     * Checks whether the desired move from the character is valid
     * @param oldPosition Current position of the player
     * @param newPosition Desired position of the player
     * @param character Character that is moving
     * @return whether the move is valid
     */
    public static boolean validateMove(Position oldPosition, Position newPosition, Character character) {
        int distance = calculateManhattanDistance(oldPosition, newPosition);
        return (character.getSpeed() <= distance);
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
     * Provides a list of positions from a start position to and end position. For use in determining
     * if a move is valid and sending to visualizer.
     * @param gameState current gameState
     * @param start position at beginning of desired path
     * @param end position at end of desired path
     * @return a List of positions along the path
     */
    public static List<Position> findPath(GameState gameState, Position start, Position end) {
        // TODO: Remove these comments before making a pull request
        // internally doesnt matter how they get to their spot, visualizer needs to be given a path.
        // player function just changes the position. Create a new function to just make a path for visualizer.
        // new package similar to -> server -> communication -> visualizer called movement instead and do something
        // with the package that communicates a list of positions that will give to visualizer.

        // TODO: Implement A* path finding. Make sure that if start==end, this function returns an EMPTY list.
        // return new ArrayList<Position>();



        // Case if start and end do not reference positions on the same board
        // Case if start == end
        // Return empty list
        if (start.getBoardID() != end.getBoardID() || start == end) {
            return new ArrayList<Position>();
        }

        // Otherwise use A* path finding to find a path
        // This will break is getBoardID returns an ID that does not exist cause itll be null
        Tile[][] grid = gameState.getBoard(start.getBoardID()).getGrid();


        // current will change depending on the f value of the cells around it, it only begins at 'start'
        Position current = start;

        // 2 Lists, closed list and open list
        // Closed list is all the cells that have been visited, maybe a boolean 2d array that will keep track of visited cells
        // Open list which keeps track of f values for certain cells, this will likely be the list that will be returned at the end maybe?
        // boolean[][] closedList = grid;
        // ArrayList openList = new ArrayList<Position>();

        // DO this 4 times, to check cell above, below, left, and right of current cell (which would be Position point)
        if (isValid(gameState, current)) {

            // If at destination
            if (isDestination(current, end)) {
                // Add this cell to list then return list
            }

            // Check to see if it is on the closed list already and whether it is blocked, if neither then continue otherwise ignore this cell
            else if (isImpassible(gameState, current) == false) {
                // do some wacky stuff here
                // check this cells new f g and h values, compare to the other 3 and find the best one?
            }
        }
    }

    // helper function to check whether given cell is valid
    public static boolean isValid(GameState gameState, Position point) {
        return (point.getX() >= 0) && (point.getX() < gameState.getBoard(point.getBoardID()).getGrid().length) &&
                (point.getY() > 0) && (point.getY() < gameState.getBoard(point.getBoardID()).getGrid()[0].length);
    }

    // helper function to check whether the given cell is impassible or not
    public static boolean isImpassible(GameState gameState, Position point) {
        return gameState.getBoard(point.getBoardID()).getGrid()[point.getX()][point.getY()].getType() == Tile.TileType.IMPASSIBLE;

    }

    // helper function to check whether the destination has been reached
    public static boolean isDestination(Position point, Position end) {
        return (point == end);
    }

    // Assume 3 values per node, 'f' , 'g', 'h'. f = g + h where g is 1 (distance between tiles) and h is total distance from that point to end tile
    // h is the heuristic
    public static double calculateH(GameState gameState, Position point, Position end) {
        return ((double) Math.sqrt(((point.getX() - end.getX()) * (point.getX() - end.getX())) + ((point.getY() - end.getY()) * (point.getY() - end.getY()))));
    }










}