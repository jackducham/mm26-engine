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
import java.util.List;

@SpringBootApplication
public class Main {
	private static boolean gameOver = false;
	private static int turnCount = 0;
	// TODO: visualizer wants us to store ALL past GameStates and VisualizerTurns. Should we do this here or through redis???

	public static void main(String[] args) {
		// Start server to communicate with infrastructure
		SpringApplication app = new SpringApplication(Main.class);
		String port = System.getenv("PORT");
		app.setDefaultProperties(Collections
				.singletonMap("server.port", port));
		app.run(args);

		// TODO: Start web socket to communicate with visualizer


		// Initialize game
		GameState gameState = new GameState();
		GameStateController controller = new GameStateController();

		while (!gameOver) {
			GameLogger.log(GameLogger.LogLevel.INFO, "Game is running- turn: " + turnCount);

			List<PlayerDecisionProtos.PlayerDecision> playerDecisions = controller.getPlayerDecisions();
			gameState = controller.updateGameState(gameState, playerDecisions);
			controller.asyncStorePlayerDecisions(turnCount, playerDecisions);
			controller.asyncStoreGameState(turnCount, gameState);

			VisualizerTurnProtos.VisualizerTurn turn = controller.constructVisualizerTurn(gameState);
			controller.asyncStoreVisualizerTurn(turnCount, turn);
			VisualizerBinaryWebSocketHandler.sendTurn(turn);

			PlayerTurnProtos.PlayerTurn playerTurn = controller.constructPlayerTurn(gameState);
			controller.asyncStorePlayerTurn(turnCount, playerTurn);
			PlayerBinaryWebSocketHandler.sendTurnAllPlayers(playerTurn);

			turnCount++;
		}
	}

	public static void setGameOver(boolean g) {
		gameOver = g;
	}
}
