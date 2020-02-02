package mech.mania.engine.game;

import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision;

import java.util.List;

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
        // TODO: do turn logic
        // update visualizerTurn <- doesn't need to happen, will send from Main

        int number = gameState.getNumber();
        for (PlayerDecision decision : decisions) {
            // TODO: update GameState
            number += decision.getIncrement();
        }
        return new GameState(number);
    }

//    /**
//     *
//     * @param targetBoard
//     * @param playersToMove
//     * @param targetPositions
//     * @return
//     */
//    public static boolean movePlayers(Board targetBoard, Player[] playersToMove, Position[] targetPositions) {
//        if (playersToMove.length != targetPositions.length) {
//            return false;
//        }
//
//        for (int i = 0; i < playersToMove.length; i++) {
//            // @TODO validatePosition takes in gameState now, instead of board --> discuss next week though
//            if (validatePosition(targetBoard, targetPositions[i]) && targetBoard.getPlayers().contains(playersToMove[i])) {
//                playersToMove[i].setPosition(targetPositions[i]);
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // ============================= PORTAL FUNCTIONS ================================================================== //
//
//    /**
//     * Checks whether a player can take the UsePortal action.
//     * @param pvpBoard part of PlayerDecision?
//     * @param playerID part of PlayerDecision?
//     * @param player part of PlayerDecision?
//     * @param currentPlayerPosition part of PlayerDecision?
//     * @param playerIDtoBoardMap part of PlayerDecision?
//     * @return true if the action can be taken, false otherwise.
//     */
//    public static boolean canUsePortal(Board pvpBoard, UUID playerID, Player player, Position currentPlayerPosition, Map<UUID, Board> playerIDtoBoardMap) {
//        for (Position portalPosition : pvpBoard.getPortals()) {
//            if (currentPlayerPosition == portalPosition && isPlayerOnBoard(player, pvpBoard)) {
//                return true;
//            }
//        }
//
//        Board playerBoard = playerIDtoBoardMap.get(playerID);
//        for (Position portalPosition : playerBoard.getPortals()) {
//            if (currentPlayerPosition == portalPosition && isPlayerOnBoard(player, playerIDtoBoardMap.get(playerID))) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // ============================= ATTACKING HELPER FUNCTIONS ======================================================== //
//
//    /**
//     * Validate whether character's weapon isn't null and if target Position is within range and on the board
//     * @param player character that's doing the attacking
//     * @param attackCoordinate central Position where the weapon is attacking
//     * @param gameState current gameState
//     * @return true if attackCoordinate is valid, false otherwise
//     */
//    // @TODO do enemies have weapons too?
//    public static boolean validateAttack(Player player, Position attackCoordinate, GameState gameState) {
//        // @TODO how does player indicate they want to attack --> will they just give coordinate and it'll automatically use equiped weapon or will they give weapon and attackCoordinate
//        // @TODO exceptions? or just return false
//
//        Weapon playerWeapon = player.getWeapon();
//        if (playerWeapon == null) {
//            // throw new InvalidWeaponException
//            return false;
//        }
//
//        // @TODO is gameState better or boardID better? to differentiate btwn personal and main board
//        if (!validatePosition(gameState, attackCoordinate)) {
//            //throw new AttackOutOfBoundsException
//            return false;
//        }
//
//        if (calculateManhattanDistance(player.getPosition(), attackCoordinate) > playerWeapon.getRange()) {
//            //throw new AttackOutOfRangeException
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     *
//     * @param player character that's doing the attacking
//     * @param attackCoordinate central Position where the weapon is attacking
//     * @param gameState current gameState
//     * @return list of Positions that would get attacked by the player's weapon
//     */
//    public static ArrayList<Position> returnAffectedPositions(Player player, Position attackCoordinate, GameState gameState) {
//        validateAttack(player, attackCoordinate, gameState);
//        Weapon weapon = player.getWeapon();
//        ArrayList<Position> affectedPositions = new ArrayList<Position>();
//
//        // @TODO talk about attack patterns? did we say we'd just capture everything within a range around the attackCoordinate?
//        return affectedPositions;
//    }
//
//    // ============================= GENERAL HELPER FUNCTIONS ========================================================== //
//
//    /**
//     * Checks whether given player is on given board.
//     * @param player The target player.
//     * @param board The target board.
//     * @return true if the player is on the board, false otherwise.
//     */
//    public static boolean isPlayerOnBoard(Player player, Board board) {
//        return (board.getPlayers().contains(player));
//    }
//
//    /**
//     * Checks whether position is within the bounds of the board
//     * @param gameState current game state with board to check position against
//     * @param position position to validate
//     * @return true, if position is valid
//     */
//    public static boolean validatePosition(GameState gameState, Position position) {
//        Board board = gameState.getPvpBoard();
//
//        // @TODO what's the coordinate system?
//        if (position.getX() > board.getGrid()[0].length || position.getX() < 0) {
//            return false;
//        }
//
//        if (position.getY() > board.getGrid().length || position.getY() < 0) {
//            return false;
//        }
//
//        // @TODO figure out if board is rectangular or circular. extra validation steps needed for circular board
//        return true;
//    }
//
//    /**
//     * @param pos1 first position
//     * @param pos2 second position
//     * @return Manhattan Distance between pos1 and pos2
//     */
//    public static int calculateManhattanDistance(Position pos1, Position pos2) {
//        return Math.abs(pos1.getX() - pos2.getY()) + Math.abs(pos1.getY() - pos2.getY());
//    }
}