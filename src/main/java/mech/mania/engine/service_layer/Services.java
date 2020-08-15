package mech.mania.engine.service_layer;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.PlayerProtos.PlayerDecision;
import mech.mania.engine.domain.model.PlayerProtos.PlayerTurn;
import mech.mania.engine.domain.model.GameChange;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.util.Map;
import java.util.logging.Logger;

/**
 * A class that has utility functions that don't directly need access
 * to the domain model.
 */
public class Services {

    private static final Logger LOGGER = Logger.getLogger( Services.class.getName() );

    /**
     * Constructs a PlayerTurn for a specific player using a
     * GameState and a specific player's name
     * @return PlayerTurn a playerTurn specific for a player
     */
    public static PlayerTurn constructPlayerTurn(GameState gameState, String playerName) {
        return PlayerTurn.newBuilder()
                .setGameState(gameState.buildProtoClass())
                .setPlayerName(playerName)
                .build();
    }

    /**
     * Returns an updated GameState object after updating it using
     * PlayerDecision objects from playerDecisionMap
     */
    public static GameState updateGameState(GameState currentGameState, Map<String, PlayerDecision> playerDecisionMap) {
        // TODO: copy over GameLogic.doTurn()
        return new GameState();
    }

    /**
     * Returns a VisualizerChange object that denotes how the gameState
     * has changed in relevant terms to the Visualizer team
     */
    public static VisualizerProtos.GameChange constructVisualizerChange(GameState gameState) {
        // TODO: construct VisualizerTurn (GameChange)
        VisualizerProtos.CharacterChange characterChange = VisualizerProtos.CharacterChange.newBuilder()
                .build();
        VisualizerProtos.GameChange gameChange = VisualizerProtos.GameChange.newBuilder()
                .build();
        return VisualizerProtos.GameChange.newBuilder()
                .build();
    }
}
