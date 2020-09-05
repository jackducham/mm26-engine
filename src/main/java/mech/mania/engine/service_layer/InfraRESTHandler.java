package mech.mania.engine.service_layer;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.domain.messages.EventEndGame;
import mech.mania.engine.domain.model.InfraProtos.InfraPlayer;
import mech.mania.engine.domain.model.InfraProtos.InfraStatus;
import mech.mania.engine.domain.model.InfraProtos.InfraVisualizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * A class to execute the game loop and other structural procedures.
 */
@SpringBootApplication
@RequestMapping("/infra")
@RestController
@ComponentScan("mech.mania.engine.entrypoints")
public class InfraRESTHandler {
    private final Logger LOGGER = Logger.getLogger( getClass().getName() );

    @Resource
    private MessageBus bus;

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
        bus.handle(new EventEndGame());
        LOGGER.info("Received game over signal");
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

        String message;

        try {
            InfraPlayer playerInfo = InfraPlayer.parseFrom(payload);
            String name = playerInfo.getPlayerName();
            String ip = playerInfo.getPlayerIp();

            if (bus.getUow().updatePlayerConnectInfoMap(name, ip)) {
                message = "Successfully updated already existing player";
            } else {
                message = "Successfully added new player";
            }

            LOGGER.fine(String.format("Received request from infra to connect with player \"%s\" @ %s", name, ip));

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /player/ request from Infra: " + e.getMessage());
            return InfraStatus.newBuilder()
                    .setStatus(400)
                    .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                    .build()
                    .toByteArray();
        }

        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage(message)
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
        // TODO: ask about whether this is ok
        return newPlayer(payload);
    }

    /**
     * Receiving visualizer connect URL from infra
     * @param payload protobuf following InfraVisualizer with player name and ip to connect to
     * @return status proto with status code 200
     */
    @PostMapping("/visualizer/start")
    public byte[] setVisualizerConnectUrl(@RequestBody byte[] payload) {

        String message;

        try {
            InfraVisualizer infraVisualizer = InfraVisualizer.parseFrom(payload);
            String visualizerConnectUrl = infraVisualizer.getVisualizerConnectUrl();

            if (bus.getUow().setVisualizerConnectUrl(visualizerConnectUrl)) {
                message = "Successfully updated visualizerConnectUrl";
            } else {
                message = "Error in updating visualizerConnectUrl";
            }

            LOGGER.fine(String.format("Updating visualizerConnectUrl to: %s", visualizerConnectUrl));

            VisualizerWebSocket.VisualizerBinaryWebSocketHandler.connectToUrl(bus.getUow().getVisualizerConnectUrl());

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /visualizer/start/ request from Infra: " + e.getMessage());
            return InfraStatus.newBuilder()
                    .setStatus(400)
                    .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                    .build()
                    .toByteArray();
        } catch (URISyntaxException | IOException | DeploymentException e) {
            LOGGER.warning("Error while connecting to Visualizer: " + e.getMessage());
            return InfraStatus.newBuilder()
                    .setStatus(400)
                    .setMessage("Error of type " + e.getClass() + ": " + e.getMessage())
                    .build()
                    .toByteArray();
        }

        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage(message)
                .build()
                .toByteArray();
    }
}
