package mech.mania.engine.game;

import javafx.geometry.Pos;
import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.characters.Position;
import mech.mania.engine.game.board.Tile;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.characters.Character;
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
     * @param pvpBoard part of PlayerDecision?
     * @param playerID part of PlayerDecision?
     * @param player part of PlayerDecision?
     * @param currentPlayerPosition part of PlayerDecision?
     * @param playerIDtoBoardMap part of PlayerDecision?
     * @return true if the action can be taken, false otherwise.
     */
    public static boolean canUsePortal(Board pvpBoard, UUID playerID, Player player, Position currentPlayerPosition, Map<UUID, Board> playerIDtoBoardMap) {
        for (Position portalPosition : pvpBoard.getPortals()) {
            if (currentPlayerPosition == portalPosition && isPlayerOnBoard(player, pvpBoard)) {
                return true;
            }
        }

        Board playerBoard = playerIDtoBoardMap.get(playerID);
        for (Position portalPosition : playerBoard.getPortals()) {
            if (currentPlayerPosition == portalPosition && isPlayerOnBoard(player, playerIDtoBoardMap.get(playerID))) {
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
     *
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
     *
     * @param player player to give the item to
     * @param index index of the item in the tile's items that the player is picking up
     * @param tile the tile the player picking an item up from
     * @return true if successful
     */
    public boolean pickUpItem(Player player, int index, Tile tile) {
        for(int i = 0; i < player.getInventory().length; i++) {
            if(player.getInventory()[i] == null) {
                Item temp = tile.getItems().get(index);
                tile.getItems().remove(index);
                player.getInventory()[i] = temp;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes one or more items from a Player's inventory and adds them to the items on a tile.
     *
     * @param player the player dropping items
     * @param itemsToDrop the indices of the items in the player's inventory which are being dropped
     * @param tile the tile the items will be dropped on
     * @return true if successful
     */
    public boolean dropItems(Player player, int[] itemsToDrop, Tile tile) {
        for(int i = 0; i < itemsToDrop.length; i++) {
            if(player.getInventory()[itemsToDrop[i]] != null) {
                Item temp = player.getInventory()[itemsToDrop[i]];
                player.getInventory()[itemsToDrop[i]] = null;
                tile.getItems().add(temp);
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
    public static boolean isPlayerOnBoard(Player player, Board board) {
        return (board.getPlayers().contains(player));
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


    // on the main or gamelogic/gamestate. doTurn will call this
    // validate player move, they give tile they want to go to, check to see if its possible for them to go there.
    //

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

    // create the path to show the movement, work with visualizer
    public static List<Position> movementPath(Position oldPosition, Position newPosition) {
        List<Position> path = new List<Position>();

        return path;
    }
    // internally doesnt matter how they get to their spot, visualizer needs to be given a path.
    // player function just changes the position. Create a new function to just make a path for visualizer.
    // new package similar to -> server -> communication -> visualizer called movement instead and do something
    // with the package that communicates a list of positions that will give to visualizer.



    /**
     * @param pos1 first position
     * @param pos2 second position
     * @return Manhattan Distance between pos1 and pos2
     */
    public static int calculateManhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getY()) + Math.abs(pos1.getY() - pos2.getY());
    }
}
