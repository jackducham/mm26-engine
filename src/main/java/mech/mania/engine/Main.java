package mech.mania.engine;

import mech.mania.engine.game.GameState;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.PlayerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos.PlayerDecision;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos.VisualizerTurn;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Main {

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

		GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "Starting server on port " + port);

		GameLogger.setPrintLevel(GameLogger.LogLevel.DEBUG);

		// reset state
		gameOver = false;
		turnCount = 0;
	}

	public static void runGame() {
		// Initialize game
		GameState gameState = new GameState();
		GameStateController controller = new GameStateController();

		while (!gameOver) {
			GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "---------------------------------------------");
			GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "Game is running- turn: " + turnCount);

			// Log the current date for the beginning of the turn in the database
			Date currDate = new Date(); // TODO: change to milliseconds?
			GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "Current time: " + currDate);
			controller.logTurnDate(turnCount, currDate);

			// Get the players' decisions
			List<PlayerDecision> playerDecisions = controller.getPlayerDecisions(turnCount);
			gameState = controller.updateGameState(gameState, playerDecisions);
			controller.asyncStoreGameState(turnCount, gameState);

			if (controller.isGameOver(gameState)) {
				gameOver = true;
			}

			// Send to Visualizer a turn
			VisualizerTurn turn = controller.constructVisualizerTurn(gameState);
			VisualizerBinaryWebSocketHandler.sendTurn(turn);

			// Send to players a turn
			PlayerBinaryWebSocketHandler.sendTurnAllPlayers(controller);

			// Simulate time passing
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				GameLogger.log(GameLogger.LogLevel.ERROR,
						"MAIN",
						"Thread.sleep interrupted:\n" + e.getMessage());
			}

			turnCount++;
		}

		// Clean up any connections
		PlayerBinaryWebSocketHandler.destroy();
		VisualizerBinaryWebSocketHandler.destroy();

		SpringApplication.exit(ctx, () -> 0);
	}

	public static void setGameOver(boolean g) {
		gameOver = g;
	}
}
