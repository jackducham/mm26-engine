package mech.mania.engine;

import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos.VisualizerChange;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class Main {

	private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );

	// CONSTANTS
	/** URL to the Visualizer websocket. TODO: insert URL */
	private static final String VISUALIZER_WEBSOCKET_URL = "";

	// GAMEWIDE VARIABLES
	/** Whether the game is currently over. Only used for Infra to shut down the server using REST */
	private static boolean gameOver = false;

	/** Current turn count, gets saved in database later */
	private static int turnCount = 0;

	/** The Spring Server instance. Can be shut down at the end of the life of the game manually */
	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		setup(args);
		runGame();
		resetState();
	}

	public static void setup(String[] args) {
		if (args.length == 0) {
			args = new String[1];
			args[0] = System.getenv("PORT");
		}

		// Start server to communicate with infrastructure
		SpringApplication app = new SpringApplication(Main.class);
		String port = args[0];
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		ctx = app.run();

		LOGGER.info("Starting server on port " + port);
	}

	public static void resetState() {
		// reset state
		gameOver = false;
		turnCount = 0;
		GameStateController.resetState();
	}

	public static void runGame() {

		while (!gameOver) {
			LOGGER.info("---------------------------------------------");
			LOGGER.info("Game is running- turn: " + turnCount);

			// Log the current date for the beginning of the turn in the database
			Date currDate = new Date(); // TODO: change to milliseconds?
			LOGGER.fine("Current time: " + currDate);
			GameStateController.logTurnDate(turnCount, currDate);

			// Get the players' decisions
			GameStateController.asyncStoreGameState(turnCount);

			// Send players a turn + update GameState
			GameStateController.sendPlayerRequestsAndUpdateGameState();

			// Send Visualizer a turn
			VisualizerChange change = GameStateController.constructVisualizerChange();
			VisualizerBinaryWebSocketHandler.sendChange(change);

			// Simulate time passing
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.info("Thread.sleep interrupted:\n" + e.getMessage());
			}

			turnCount++;
		}

		// Clean up any websocket connections (Player connections are not Websockets)
		VisualizerBinaryWebSocketHandler.destroy();

		SpringApplication.exit(ctx, () -> 0);
	}

	public static void setGameOver(boolean g) {
		gameOver = g;
	}
}
