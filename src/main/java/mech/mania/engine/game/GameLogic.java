package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.characters.Position;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.items.Weapon;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn;
import mech.mania.engine.game.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public static GameState doTurn(GameState gameState, List<PlayerDecision> decisions){
        VisualizerTurn.Builder visualizerTurn = VisualizerTurn.newBuilder();
        // TODO: do turn logic
        // TODO: update visualizerTurn
        VisualizerBinaryWebSocketHandler.sendTurn(visualizerTurn.build());
        return null;
    }

    /**
     *
     * @param gameState
     * @param playersToMove
     * @param targetPositions
     * @return
     */
    public static boolean movePlayers(GameState gameState, Player[] playersToMove, Position[] targetPositions) {
        if (playersToMove.length != targetPositions.length) {
            return false;
        }

        for (int i = 0; i < playersToMove.length; i++) {
            Board board = gameState.getBoard(targetPositions[i].getBoardID());
            if (validatePosition(gameState, targetPositions[i]) && board.getPlayers().contains(playersToMove[i])) {
                playersToMove[i].setPosition(targetPositions[i]);
            } else {
                return false;
            }
        }
        return true;
    }

    // ============================= PORTAL FUNCTIONS ================================================================== //

    /**
     * Checks whether a player can take the UsePortal action.
     * @param gameState current gameState.
     * @param pvpBoard part of PlayerDecision?
     * @param playerID part of PlayerDecision?
     * @param player part of PlayerDecision?
     * @param currentPlayerPosition part of PlayerDecision?
     * @param playerIDtoBoardMap part of PlayerDecision?
     * @return true if the action can be taken, false otherwise.
     */
    public static boolean canUsePortal(GameState gameState, Board pvpBoard, UUID playerID, Player player, Position currentPlayerPosition, Map<UUID, Board> playerIDtoBoardMap) {
        for (Position portalPosition : pvpBoard.getPortals()) {
            if (currentPlayerPosition == portalPosition && isPlayerOnBoard(gameState, player, pvpBoard)) {
                return true;
            }
        }

        Board playerBoard = playerIDtoBoardMap.get(playerID);
        for (Position portalPosition : playerBoard.getPortals()) {
            if (currentPlayerPosition == portalPosition && isPlayerOnBoard(gameState, player, playerIDtoBoardMap.get(playerID))) {
                return true;
            }
        }
        return false;
    }

    // ============================= ATTACKING HELPER FUNCTIONS ======================================================== //

    /**
     * Validate whether character's weapon isn't null and if target Position is within range and on the board
     * @param player character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @param gameState current gameState
     * @return true if attackCoordinate is valid, false otherwise
     */
    // @TODO do enemies have weapons too?
    public static boolean validateAttack(Player player, Position attackCoordinate, GameState gameState) {
        Weapon playerWeapon = player.getWeapon();
        if (playerWeapon == null) {
            return false;
        }

        if (!validatePosition(gameState, attackCoordinate)) {
            return false;
        }

        if (calculateManhattanDistance(player.getPosition(), attackCoordinate) > playerWeapon.getRange()) {
            return false;
        }

        return true;
    }

    /**
     * Provides a list of position affected by a given player's attack.
     * @param player character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @param gameState current gameState
     * @return list of Positions that would get attacked by the player's weapon
     */
    public static ArrayList<Position> returnAffectedPositions(Player player, Position attackCoordinate, GameState gameState) {
        if (!validateAttack(player, attackCoordinate, gameState)) {
            return null;
        }
        Weapon weapon = player.getWeapon();
        int radius = weapon.getSplashRadius();
        ArrayList<Position> affectedPositions = new ArrayList<>();

        int centerX = attackCoordinate.getX();
        int centerY = attackCoordinate.getY();

        int xMin = ((centerX - radius) < 0) ? 0 : (centerX - radius);
        int yMin = ((centerY - radius) < 0) ? 0 : (centerY - radius);

        for (int x = xMin; x <= centerX + radius; x++) {
            for (int y = yMin; y <= centerY + radius; y++) {
                Position position = new Position(x, y);
                if (calculateManhattanDistance(position, attackCoordinate) <= radius && validatePosition(gameState, position)) {
                    affectedPositions.add(position);
                }
            }
        }

        return affectedPositions;
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
}
