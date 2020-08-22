package mech.mania.engine.adapters;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.google.protobuf.MessageLite;
import mech.mania.engine.Config;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.VisualizerProtos;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Uses AWS to store GameStates
 */
public class RepositoryAws implements RepositoryAbstract {

    private static final String bucketName = Config.getProperty("awsBucketName"); // "mechmania2020"
    private static final String region = Config.getProperty("awsRegion"); // "us-east-1"

    @Override
    public int storeGameState(final int turn, final GameState gameState) {
        try {
            sendToAws(String.format("engine/GameState/%06d", turn), gameState.buildProtoClass());
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int storeGameChange(final int turn, final VisualizerProtos.GameChange gameChange) {
        try {
            sendToAws(String.format("engine/GameChange/%06d", turn), gameChange);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public int storePlayerStatsBundle(final int turn, final CharacterProtos.PlayerStatsBundle playerStatsBundle) {
        try {
            sendToAws(String.format("engine/PlayerStatsBundle/%06d", turn), playerStatsBundle);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
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
        } catch (IOException e) {
            LOGGER.warning(String.format("Failed in sendToAws for %s: %s", protobufObj.getClass(), e.getMessage()));
            throw e;
        }
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/model/ObjectMetadata.html
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("application/octet-stream");
        metadata.setContentLength(protobufObj.getSerializedSize());
        metadata.setContentEncoding("UTF-8");

        s3.putObject(bucketName, key, inputStream, metadata);
    }
}
