package mech.mania.engine.game;

import mech.mania.engine.game.board.Position;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.game.items.Weapon;
import mech.mania.engine.server.playercommunication.PlayerDecision;
import mech.mania.engine.server.visualizercommunication.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.visualizercommunication.VisualizerTurnProtos.VisualizerTurn;
import mech.mania.engine.game.items.Item;

import java.util.ArrayList;
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
        VisualizerTurn.Builder visualizerTurn = VisualizerTurn.newBuilder();
        // TODO: do turn logic
        // TODO: update visualizerTurn
        VisualizerBinaryWebSocketHandler.sendTurn(visualizerTurn.build());
        return null;
    }

    /*
    Player has weapon in inv
    coord is valid (map, within range)
     */
    public boolean validateAttack(Player player, PlayerDecision decision) {
        Weapon decisionWeapon = decision.getWeapon(); // need to get weapon from playerDecision
        Weapon playerWeapon = player.getWeapon();

        if (playerWeapon == null || decisionWeapon != playerWeapon) {
            // throw new InvalidWeaponException
            return false;
        }

        // need to get attack coordinate from playerDecision
        Position attackPosition = decision.getAttackPosition();
        

        // boolean inMap = false;
        int boardWidth = 1;
        int boardHeight = 1;

        int attackX = 1;
        int attackY = 1;

        if(attackX >= boardHeight || attackY >= boardWidth{
            //throw new AttackOutOfBoundsException
        }

        double range = 1;
        int currentX = 1;
        int currentY = 1;

        if(Math.sqrt(Math.pow(currentX - attackX,2) + Math.pow(currentY - attackY,2)) > range){
            //throw new AttackOutOfRangeException
        }

        return false;
    }


    /**
     * Validate whether character's weapon isn't null and if target Position is within range and on the board
     * @param player character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @return true if attackCoordinate is valid, false otherwise
     */
    public static boolean validateAttack(Player player, Position attackCoordinate) {
        Weapon decisionWeapon = decision.getWeapon(); // need to get weapon from playerDecision
        Weapon playerWeapon = player.getWeapon();

        if (playerWeapon == null || decisionWeapon != playerWeapon) {
            // throw new InvalidWeaponException
            return false;
        }

        if (validatePosition(board, attackCoordinate))

        // check if within range
        return true;
    }
    /**
     *
     * @param character character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @return list of Positions that would get attacked by the player's weapon
     */
    public static ArrayList<Position> returnAffectedPositions(Character character, Position attackCoordinate) {
        validateAttack(character, attackCoordinate);
        Weapon weapon = character.getWeapon();
        ArrayList<Position> affectedPositions = new ArrayList<Position>();

        return affectedPositions;
    }

    /**
     *
     * @param pos1
     * @param pos2
     * @return Manhattan Distance between pos1 and pos2
     */
    public static int calculateManhattanDistance(Position pos1, Position pos2) {
        return Math.abs(pos1.getX() - pos2.getY()) + Math.abs(pos1.getY() - pos2.getY());
    }
}
