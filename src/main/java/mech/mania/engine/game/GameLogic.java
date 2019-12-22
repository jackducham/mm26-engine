package mech.mania.engine.game;

import mech.mania.engine.server.playercommunication.PlayerDecision;
import mech.mania.engine.server.visualizercommunication.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.visualizercommunication.VisualizerTurnProtos.VisualizerTurn;
import mech.mania.engine.game.items.Item;
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
    public boolean validateAttack(PlayerDecision p){

        Item weapon = null;
        Item[] playersInventory = new Item[1];

        boolean hasWeapon = false;

        for(Item i : playersInventory){
            if(weapon.equals(i)){
                hasWeapon = true;
                break;
            }
        }

        if(!hasWeapon){
            //throw new InvalidWeaponException
        }

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

}
