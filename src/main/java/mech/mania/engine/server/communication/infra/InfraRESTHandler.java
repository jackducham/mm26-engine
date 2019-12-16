package mech.mania.engine.server.communication.infra;

import mech.mania.engine.Main;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.api.GameStateController;
import org.springframework.web.bind.annotation.*;

/**
 * A class to execute the game loop and other structural procedures.
 */
@RequestMapping("api/v1/infra")
@RestController
public class InfraRESTHandler {

    /**
     * Method to handle GET requests to the /health endpoint to check that the server is running correctly.
     * @return "200"
     */
    @GetMapping("/health")
    public @ResponseBody String health() {
        return "200";
    }

    /**
     * Method to handle GET requests to the /endgame endpoint to end the current game.
     * @return "Game ended."
     */
    @GetMapping("/endgame")
    public @ResponseBody String endgame() {
        Main.setGameOver(true);
        GameLogger.log(GameLogger.LogLevel.INFO,
                "INFRAREST",
                "Received game over signal");
        return "Game ended.";
    }
}
