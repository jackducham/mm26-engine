package mech.mania.engine.server.infracommunication;

import mech.mania.engine.game.Main;
import org.springframework.web.bind.annotation.*;

/**
 * A class to execute the game loop and other structural procedures.
 */
@RestController
public class InfraRESTHandler {
    /**
     * Method to handle GET requests to the /health endpoint to check that the server is running correctly.
     * @return "200"
     */
    @RequestMapping(value = "/health" , method = RequestMethod.GET)
    public @ResponseBody String health() {
        return "200";
    }

    /**
     * Method to handle GET requests to the /endgame endpoint to end the current game.
     * @return "Game ended."
     */
    @RequestMapping(value = "/endgame" , method = RequestMethod.GET)
    public @ResponseBody String endgame() {
        Main.gameOver = true;
        return "Game ended.";
    }
}
