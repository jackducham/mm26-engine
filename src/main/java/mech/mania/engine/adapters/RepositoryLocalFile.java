package mech.mania.engine.adapters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.GameStateProtos;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Use files to store data (for now).
 */
public class RepositoryLocalFile implements RepositoryAbstract {

    private static final Logger LOGGER = Logger.getLogger( RepositoryLocalFile.class.getName() );

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        LOGGER.fine("Logging GameState for turn " + turn + ", GameState: " + gameState.toString());

        try {
            String serverName = System.getenv("ENGINE_NAME");
            File file = new File(String.format("./repository/%s/GameState/%06d.pb", serverName == null ? "unnamed" : serverName, turn));
            file.getParentFile().mkdirs();

            FileOutputStream stream = new FileOutputStream(file);

            gameState.buildProtoClass().writeTo(stream);
            stream.flush();
            stream.close();
        } catch (IOException e){
            LOGGER.warning("IOException when storing game state to file: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public int storeGameChange(final int turn, final VisualizerProtos.GameChange gameChange) {
        LOGGER.fine("Logging VisualizerTurn for turn " + turn + ", VisualizerTurn: " + gameChange.toString());

        try {
            String serverName = System.getenv("ENGINE_NAME");
            File file = new File(String.format("./repository/%s/GameChange/%06d.pb", serverName == null ? "unnamed" : serverName, turn));
            file.getParentFile().mkdirs();

            FileOutputStream stream = new FileOutputStream(file);

            gameChange.writeTo(stream);
            stream.flush();
            stream.close();
        } catch (IOException e){
            LOGGER.warning("IOException when storing game change to file: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public int storePlayerStatsBundle(int turn, CharacterProtos.PlayerStatsBundle playerStatsBundle) {
        LOGGER.fine("Logging PlayerStatsBundles for turn " + turn + ", PlayerStatsBundles: " + playerStatsBundle.toString());

        try {
            String serverName = System.getenv("ENGINE_NAME");
            File file = new File(String.format("./repository/%s/PlayerStatsBundle/%06d.pb", serverName == null ? "unnamed" : serverName, turn));
            file.getParentFile().mkdirs();

            FileOutputStream stream = new FileOutputStream(file);

            playerStatsBundle.writeTo(stream);
            stream.flush();
            stream.close();
        } catch (IOException e){
            LOGGER.warning("IOException when storing player stats bundle to file: " + e.getMessage());
        }

        return 0;
    }

    @Override
    public GameState getGameState(int turn) {
        LOGGER.info("Attempting to get stored GameState from file repository.");

        GameStateProtos.GameState gameState = null;
        try {
            String serverName = System.getenv("ENGINE_NAME");
            FileInputStream file = new FileInputStream(String.format("./repository/%s/GameState/%06d.pb", serverName == null ? "unnamed" : serverName, turn));

            gameState = GameStateProtos.GameState.parseFrom(file);
            file.close();
        } catch (IOException e){
            LOGGER.warning("IOException when reading game state to file: " + e.getMessage());
        }

        if(gameState == null){
            return null;
        }
        return new GameState(gameState);
    }

    @Override
    public void reset() {

    }
}
