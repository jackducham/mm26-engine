package mech.mania.engine.service_layer.handlers;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.messages.Event;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.service_layer.UnitOfWorkAbstract;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SendPlayerStats extends EventHandler {

    // TODO: get from environment variables
    String bucketName = "mechmania2020";
    String region = "us-east-1";

    public SendPlayerStats(UnitOfWorkAbstract uow) {
        super(uow);
    }

    @Override
    public void handle(Event event) {
        Map<String, Player> playersMap = uow.getGameState().getAllPlayers();
        List<CharacterProtos.PlayerStats> playerStatsMultiple = new ArrayList<>();
        for (Map.Entry<String, Player> entry : playersMap.entrySet()) {
            playerStatsMultiple.add(entry.getValue().getPlayerStats());
        }
        String key = "";
        sendToAws(key, playerStatsMultiple);
    }


    /**
     * Converts a List of PlayerStats objects to an InputStream and sends it to an
     * AWS bucket specified by this.bucket, this.key, and this.region
     * @param playerStats List of PlayerStats to send
     */
    private void sendToAws(String key, List<CharacterProtos.PlayerStats> playerStats) {
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new EnvironmentVariableCredentialsProvider())
                .withRegion(region)
                .build();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        for (CharacterProtos.PlayerStats playerStat : playerStats) {
            try {
                playerStat.writeTo(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        // TODO: figure out what this means
        ObjectMetadata metadata = new ObjectMetadata();
        s3.putObject(bucketName, key, inputStream, metadata);
    }
}
