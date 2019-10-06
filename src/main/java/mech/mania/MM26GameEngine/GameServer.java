package mech.mania.MM26GameEngine;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * A class to execute the game loop and other structural procedures.
 */
@RestController
public class GameServer {
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
