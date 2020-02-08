package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.Position;
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
        ArrayList<Position> radiusPositions = getPositionsWithinRadius(attackCoordinate, radius);

        for (Position position: radiusPositions) {
            if (validatePosition(gameState, position)) {
                affectedPositions.add(position);
            }
        }

        return affectedPositions;
    }

    /**
     * Get an ArrayList with all the Positions within a certain radius around another Position
     * @param center Position to find positions around
     * @param radius distance to find coordinates in
     * @return arraylist of positions
     */
    public static ArrayList<Position> getPositionsWithinRadius(Position center, int radius) {
        ArrayList<Position> radiusPositions = new ArrayList<>();
        int centerX = center.getX();
        int centerY = center.getY();

        int xMin = ((centerX - radius) < 0) ? 0 : (centerX - radius);
        int yMin = ((centerY - radius) < 0) ? 0 : (centerY - radius);

        for (int x = xMin; x <= centerX + radius; x++) {
            for (int y = yMin; y <= centerY + radius; y++) {
                if (calculateManhattanDistance(x, centerX, y, centerY) <= radius) {
                    radiusPositions.add(new Position(x, y));
                }
            }
        }

        return radiusPositions;
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

    /**
     * @param pos1 first position
     * @param pos2 second position
     * @return Manhattan Distance between pos1 and pos2
     */
    public static int calculateManhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getY()) + Math.abs(pos1.getY() - pos2.getY());
    }

    /**
     *
     * @param x_1
     * @param y_1
     * @param x_2
     * @param y_2
     * @return manhattan distance between the two points
     */
    public static int calculateManhattanDistance(int x_1, int y_1, int x_2, int y_2) {
        return Math.abs(x_1 - x_2) + Math.abs(y_1 - y_2);
    }
}
