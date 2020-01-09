package mech.mania.engine;

import mech.mania.engine.game.GameState;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.PlayerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Main {
	private static boolean gameOver = false;
	private static int turnCount = 0;
	private static ConfigurableApplicationContext ctx;

	public static void main(String[] args) {
		setup(args);
		runGame();
	}

	public static void setup(String[] args) {
		// Start server to communicate with infrastructure
		SpringApplication app = new SpringApplication(Main.class);
		String port = args[0];  // System.getenv("PORT");
		app.setDefaultProperties(Collections.singletonMap("server.port", port));
		ctx = app.run();

		GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "Starting server on port " + port);

		GameLogger.setPrintLevel(GameLogger.LogLevel.DEBUG);
	}

	public static void runGame() {
		// TODO: Start web socket to communicate with visualizer

		// TODO: give access to Main GameStateController to WebSocketHandlers

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
			List<PlayerDecisionProtos.PlayerDecision> playerDecisions = controller.getPlayerDecisions(turnCount);
			controller.updateGameState(gameState, playerDecisions);
			controller.asyncStoreGameState(turnCount, gameState);

			// Send to Visualizer a turn
			VisualizerTurnProtos.VisualizerTurn turn = controller.constructVisualizerTurn(gameState);
			VisualizerBinaryWebSocketHandler.sendTurn(turn);

			// Send to players a turn
			PlayerTurnProtos.PlayerTurn playerTurn = controller.constructPlayerTurn(gameState);
			PlayerBinaryWebSocketHandler.sendTurnAllPlayers(playerTurn);

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
