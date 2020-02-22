package mech.mania.engine.server.dao;

import mech.mania.engine.game.GameState;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Use regular data structures to store data (for now).
 */
public class DatabaseFake implements Database {

    private List<GameState> gameStates = new ArrayList<>();
    private List<VisualizerChange> visualizerChanges = new ArrayList<>();
    private List<Date> dates = new ArrayList<>();

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", "Logging GameState for turn " + turn + ", GameState: " + gameState.toString());
        if (gameStates.size() <= turn) {
            gameStates.add(gameState);
        } else {
            gameStates.set(turn, gameState);
        }
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", gameStates.size() + " GameStates stored currently");
        return 0;
    }

    @Override
    public int storeVisualizerChange(final int turn, final VisualizerChange visualizerChange) {
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", "Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + visualizerChange.toString());
        if (visualizerChanges.size() <= turn) {
            visualizerChanges.add(visualizerChange);
        } else {
            visualizerChanges.set(turn, visualizerChange);
        }
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", visualizerChanges.size() + " VisualizerTurns stored currently");
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
        GameLogger.log(GameLogger.LogLevel.DEBUG, "FAKEDAO", dates.size() + " dates stored currently");
        return 0;
    }

    @Override
    public int turnBeforeDate(final Date date) {

        return 0;
    }

    @Override
    public List<VisualizerChange> getVisualizerChanges() {
        return visualizerChanges;
    }

}
