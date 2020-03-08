package mech.mania.engine;

import mech.mania.engine.server.api.GameStateController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.logging.Logger;

@SpringBootApplication
public class Main {

	private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );

	/** Whether the game is currently over. Only used for Infra to shut down the server using REST */
	private static boolean gameOver = false;

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
		GameStateController.resetState();
	}

	public static void runGame() {
		while (!gameOver) {
			LOGGER.info("---------------------------------------------------------------");
			LOGGER.info("Game is running- turn: " + GameStateController.getCurrentTurnNum());

			GameStateController.asyncStoreCurrentGameState();

			GameStateController.sendPlayerRequestsAndUpdateGameState();

			GameStateController.sendVisualizerChange();

			// Simulate time passing
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.info("Thread.sleep interrupted:\n" + e.getMessage());
			}

			GameStateController.incrementTurn();
		}

		GameStateController.cleanUpConnections();

		SpringApplication.exit(ctx, () -> 0);
	}

	public static void setGameOver(boolean g) {
		gameOver = g;
	}
}
