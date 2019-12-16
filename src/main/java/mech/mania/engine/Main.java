package mech.mania.engine;

import mech.mania.engine.game.GameState;
import mech.mania.engine.logging.GameLogger;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.player.PlayerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.player.model.PlayerDecisionProtos;
import mech.mania.engine.server.communication.player.model.PlayerTurnProtos;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Main {
	private static boolean gameOver = false;
	private static int turnCount = 0;

	public static void main(String[] args) {
		// Start server to communicate with infrastructure
		SpringApplication app = new SpringApplication(Main.class);
		String port = System.getenv("PORT");
		app.setDefaultProperties(Collections
				.singletonMap("server.port", port));
		app.run(args);

		// TODO: Start web socket to communicate with visualizer

		// TODO: give access to Main GameStateController to WebSocketHandlers

		// Initialize game
		GameState gameState = new GameState();
		GameStateController controller = new GameStateController();

		while (!gameOver) {
			GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "---------------------------------------------");
			GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "Game is running- turn: " + turnCount);

			// Log the current date for the beginning of the turn in the database
			Date currDate = new Date();
			GameLogger.log(GameLogger.LogLevel.INFO, "MAIN", "Current time: " + currDate);
			controller.logTurnDate(turnCount, currDate);

			// Get the players' decisions
			List<PlayerDecisionProtos.PlayerDecision> playerDecisions = controller.getPlayerDecisions(turnCount);
			GameLogger.log(GameLogger.LogLevel.INFO,
					"MAIN",
					playerDecisions.size() + " PlayerDecision objects received");
			controller.updateGameState(gameState, playerDecisions);
			controller.asyncStoreGameState(turnCount, gameState);

			// Send to Visualizer a turn
			VisualizerTurnProtos.VisualizerTurn turn = controller.constructVisualizerTurn(gameState);
			GameLogger.log(GameLogger.LogLevel.INFO,
					"MAIN",
					"VisualizerTurn constructed");
			controller.asyncStoreVisualizerTurn(turnCount, turn);
			VisualizerBinaryWebSocketHandler.sendTurn(turn);

			// Send to players a turn
			PlayerTurnProtos.PlayerTurn playerTurn = controller.constructPlayerTurn(gameState);
			GameLogger.log(GameLogger.LogLevel.INFO,
					"MAIN",
					"PlayerTurn constructed");
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
	}

	public static void setGameOver(boolean g) {
		gameOver = g;
	}
}
