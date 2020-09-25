package mech.mania.engine.adapters;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.google.protobuf.MessageLite;
import mech.mania.engine.Config;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.messages.Message;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.GameStateProtos;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Uses AWS to store GameStates
 */
public class RepositoryAws implements RepositoryAbstract {

    private static final String bucketName = Config.getProperty("awsBucketName"); // "mechmania2020"
    private static final String region = Config.getProperty("awsRegion"); // "us-east-1"

    @Override
    public int storeCurrentTurn(int turn) {
        new Thread(() -> {
            try {
                String serverName = System.getenv("ENGINE_NAME");
                String key = String.format("engine/%s/CurrentTurn.txt", serverName == null ? "unnamed" : serverName);

                AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                        .withCredentials(new EnvironmentVariableCredentialsProvider())
                        .withRegion(region)
                        .build();

                s3.putObject(bucketName, key, "" + turn);
            } catch (AmazonServiceException e) {
                // The call was transmitted successfully, but Amazon S3 couldn't process
                // it, so it returned an error response.
                LOGGER.warning("Unable to process S3 request when setting CurrentTurn in AWS: " + e);
            } catch (SdkClientException e) {
                // Amazon S3 couldn't be contacted for a response, or the client
                // couldn't parse the response from Amazon S3.
                LOGGER.warning("Failed to connect to S3 when setting CurrentTurn in AWS: " + e);
            }
        }).start();
        return 0;
    }

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        new Thread(() -> {
            try {
                String serverName = System.getenv("ENGINE_NAME");
                sendToAws(String.format("engine/%s/GameState/%06d.pb", serverName == null ? "unnamed" : serverName, turn), gameState.buildProtoClass());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return 0;
    }

    @Override
    public int storeGameChange(final int turn, final VisualizerProtos.GameChange gameChange) {
        new Thread(() -> {
            try {
                String serverName = System.getenv("ENGINE_NAME");
                sendToAws(String.format("engine/%s/GameChange/%06d.pb", serverName == null ? "unnamed" : serverName, turn), gameChange);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return 0;
    }

    @Override
    public int storePlayerStatsBundle(final int turn, final CharacterProtos.PlayerStatsBundle playerStatsBundle) {
        new Thread(() -> {
            try {
                String serverName = System.getenv("ENGINE_NAME");
                sendToAws(String.format("engine/%s/PlayerStatsBundle/%06d.pb", serverName == null ? "unnamed" : serverName, turn), playerStatsBundle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return 0;
    }

    @Override
    public GameState getGameState(int turn) {
        String serverName = System.getenv("ENGINE_NAME");
        String gameStateKey = String.format("engine/%s/GameState/%06d", serverName == null ? "unnamed" : serverName, turn);
        String playerStatsKey = String.format("engine/%s/PlayerStatsBundle/%06d.pb", serverName == null ? "unnamed" : serverName, turn);
        MessageLite gameStateProto = GameStateProtos.GameState.getDefaultInstance();

        // Get GameState from AWS
        gameStateProto = getFromAws(gameStateKey, gameStateProto);

        GameState gameState = new GameState((GameStateProtos.GameState)gameStateProto);

        // Also restore PlayerStats
        MessageLite playerStatsBundleProto = CharacterProtos.PlayerStatsBundle.getDefaultInstance();

        // Get PlayerStatsBundle from AWS
        playerStatsBundleProto = getFromAws(playerStatsKey, playerStatsBundleProto);

        // Restore PlayerStats
        for(Map.Entry<String, CharacterProtos.PlayerStats> entry :
                ((CharacterProtos.PlayerStatsBundle)playerStatsBundleProto).getStatsMap().entrySet()){
            gameState.getPlayer(entry.getKey()).setPlayerStats(entry.getValue());
        }

        return gameState;
    }

    @Override
    public void reset() {

    }


    /**
     * Converts a List of PlayerStats objects to an InputStream and sends it to an
     * AWS bucket specified by this.bucket, this.key, and this.region
     * @param protobufObj List of PlayerStats to send
     */
    private void sendToAws(String key, MessageLite protobufObj) throws IOException {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(region)
                .build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            protobufObj.writeTo(outputStream);
            outputStream.close();
        } catch (IOException e) {
            LOGGER.warning(String.format("Failed in sendToAws for %s: %s", protobufObj.getClass(), e.getMessage()));
            throw e;
        }
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/model/ObjectMetadata.html
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        metadata.setContentLength(protobufObj.toByteArray().length);
        metadata.setContentEncoding("UTF-8");

        s3.putObject(bucketName, key, inputStream, metadata);
    }

    public MessageLite getFromAws(String key, MessageLite messageLite) {
        // https://docs.aws.amazon.com/AmazonS3/latest/dev/RetrievingObjectUsingJava.html
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(region)
                    .withCredentials(new EnvironmentVariableCredentialsProvider())
                    .build();

            // Get an object
            S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, key));

            messageLite.getParserForType().parseFrom(s3Object.getObjectContent());

            // Close connection
            s3Object.close();

            return messageLite;

        } catch(IOException e){
            LOGGER.warning("IOException when getting game state from AWS: " + e);
            return null;
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            LOGGER.warning("Unable to process S3 request when getting game state from AWS: " + e);
            return null;
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            LOGGER.warning("Failed to connect to S3 when getting game state from AWS: " + e);
            return null;
        }
    }
}
