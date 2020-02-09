package mech.mania.engine.server.communication.infra;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.Main;
import mech.mania.engine.logging.GameLogger;
import org.springframework.web.bind.annotation.*;
import mech.mania.engine.server.communication.infra.model.InfraProtos.InfraStatus;
import mech.mania.engine.server.communication.infra.model.InfraProtos.InfraPlayer;

/**
 * A class to execute the game loop and other structural procedures.
 */
@RequestMapping("infra")
@RestController
public class InfraRESTHandler {

    /**
     * Method to handle GET requests to the /health endpoint to check that the server is running correctly.
     * @return status proto with status code 200
     */
    @GetMapping("/health")
    public @ResponseBody byte[] health() {
        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage("Alive.")
                .build()
                .toByteArray();
    }

    /**
     * Method to handle GET requests to the /endgame endpoint to end the current game.
     * @return status proto with status code 200
     */
    @GetMapping("/endgame")
    public @ResponseBody byte[] endgame() {
        Main.setGameOver(true);
        GameLogger.log(GameLogger.LogLevel.INFO,
                "INFRAREST",
                "Received game over signal");
        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage("Game over.")
                .build()
                .toByteArray();
    }

    /**
     * Receiving new player from infra.
     * @param payload protobuf following InfraPlayer with player name and ip to connect to
     * @return status proto with status code 200
     */
    @PostMapping("/player/new")
    public byte[] newPlayer(@RequestBody byte[] payload) {
        try {
            InfraPlayer playerInfo = InfraPlayer.parseFrom(payload);
            String name = playerInfo.getPlayerName();
            String ip = playerInfo.getPlayerIp();

            // do new player stuff
        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            InfraStatus.newBuilder()
                    .setStatus(400)
                    .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                    .build()
                    .toByteArray();
        }

        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage("Success.")
                .build()
                .toByteArray();
    }

    /**
     * Receiving reconnecting player from infra.
     * @param payload protobuf following InfraPlayer with player name and ip to connect to
     * @return status proto with status code 200
     */
    @PostMapping("/player/reconnect")
    public byte[] reconnectPlayer(@RequestBody byte[] payload) {
        try {
            InfraPlayer playerInfo = InfraPlayer.parseFrom(payload);
            String name = playerInfo.getPlayerName();
            String ip = playerInfo.getPlayerIp();

            // do player reconnect stuff
        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            InfraStatus.newBuilder()
                    .setStatus(400)
                    .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                    .build()
                    .toByteArray();
        }

        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage("Success.")
                .build()
                .toByteArray();
    }
}
