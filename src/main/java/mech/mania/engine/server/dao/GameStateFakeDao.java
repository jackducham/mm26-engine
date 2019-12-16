package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Use regular data structures to store data (for now).
 */
public class GameStateFakeDao implements GameStateDao {

    private List<GameState> gameStates = new ArrayList<>();
    private List<VisualizerTurnProtos.VisualizerTurn> visualizerTurns = new ArrayList<>();
    private List<Date> dates = new ArrayList<>();

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", "Logging GameState for turn " + turn + ", GameState: " + gameState.toString());
        if (gameStates.size() <= turn) {
            gameStates.add(gameState);
        } else {
            gameStates.set(turn, gameState);
        }
        return 0;
    }

    @Override
    public int storeVisualizerTurn(final int turn, final VisualizerTurnProtos.VisualizerTurn visualizerTurn) {
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", "Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + visualizerTurn.toString());
        if (visualizerTurns.size() <= turn) {
            visualizerTurns.add(visualizerTurn);
        } else {
            visualizerTurns.set(turn, visualizerTurn);
        }
        return 0;
    }

    @Override
    public int logTurnDate(int turn, Date date) {
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", "Logging date for turn " + turn + ", date: " + date.toString());
        if (dates.size() <= turn) {
            dates.add(date);
        } else {
            dates.set(turn, date);
        }
        return 0;
    }

    @Override
    public int turnBeforeDate(final Date date) {

        return 0;
    }

    @Override
    public List<VisualizerTurnProtos.VisualizerTurn> getVisualizerTurns() {
        return visualizerTurns;
    }

}
