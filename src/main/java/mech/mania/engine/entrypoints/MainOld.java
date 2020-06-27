package mech.mania.engine.entrypoints;

import mech.mania.engine.Bootstrap;
import mech.mania.engine.service_layer.GameStateController;
import mech.mania.engine.service_layer.MessageBus;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.logging.Logger;

public class MainOld {

	private static final Logger LOGGER = Logger.getLogger( MainOld.class.getName() );

	/** Whether the game is currently over. Only used for Infra to shut down the server using REST */
	private boolean gameOver = false;

	/** The Spring Server instance. Can be shut down at the end of the life of the game manually */
	private ConfigurableApplicationContext ctx;

	private MessageBus bus = Bootstrap.bootstrap();

	public static void main(String[] args) {
		setup(args);
		runGame();
		resetState();
	}

	public void setup(String[] args) {
		if (args.length == 0) {
			args = new String[1];
			args[0] = "8080";  // System.getenv("PORT");
		}

		// Start server to communicate with infrastructure
		SpringApplication app = new SpringApplication(MainOld.class);
		String port = args[0];
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		ctx = app.run();

		LOGGER.info("Starting server on port " + port);
	}

	public void resetState() {
		// reset state
		gameOver = false;
		GameStateController.resetState();
	}

	public void runGame() {
		while (!gameOver) {
			LOGGER.info("---------------------------------------------------------------");
			LOGGER.info("Game is running- turn: " + GameStateController.getCurrentTurnNum());

			GameStateController.asyncStoreCurrentGameState();

			GameStateController.sendPlayerRequestsAndUpdateGameState();

			GameStateController.sendVisualizerChange();

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

	public void setGameOver(boolean g) {
		gameOver = g;
	}
}
