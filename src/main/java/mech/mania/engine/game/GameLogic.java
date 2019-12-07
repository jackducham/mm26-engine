package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.board.Position;
import mech.mania.engine.game.characters.Player;
import mech.mania.engine.server.playercommunication.PlayerDecision;
import mech.mania.engine.server.visualizercommunication.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.visualizercommunication.VisualizerTurnProtos.VisualizerTurn;

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
     * Checks whether given player is on given board.
     * @param player The target player.
     * @param board The target board.
     * @return True if the player is on the board, false otherwise.
     */
    public boolean IsPlayerOnBoard(Player player, Board board) {
        return (board.getPlayers().contains(player));
    }

    /**
     * Checks whether a player can take the UsePortal action.
     * @param pvpBoard part of PlayerDecision?
     * @param playerID part of PlayerDecision?
     * @param player part of PlayerDecision?
     * @param currentPlayerPosition part of PlayerDecision?
     * @param playerIDtoBoardMap part of PlayerDecision?
     * @return True if the action can be taken, false otherwise.
     */
    public boolean CanUsePortal(Board pvpBoard, UUID playerID, Player player, Position currentPlayerPosition, Map<UUID, Board> playerIDtoBoardMap) {
        for (Position portalPosition : pvpBoard.getPortals()) {
            if (currentPlayerPosition == portalPosition && IsPlayerOnBoard(player, pvpBoard)) {
                return true;
            }
        }

        Board playerBoard = playerIDtoBoardMap.get(playerID);
        for (Position portalPosition : playerBoard.getPortals()) {
            if (currentPlayerPosition == portalPosition && IsPlayerOnBoard(player, playerIDtoBoardMap.get(playerID))) {
                return true;
            }
        }
        return false;
    }
}
