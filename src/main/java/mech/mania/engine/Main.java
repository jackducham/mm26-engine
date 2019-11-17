package mech.mania.engine;

import mech.mania.engine.game.GameState;
import mech.mania.engine.server.api.GameStateController;
import mech.mania.engine.server.communication.visualizer.VisualizerBinaryWebSocketHandler;
import mech.mania.engine.server.communication.visualizer.model.VisualizerTurnProtos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

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


		// TODO: Initialize game
		GameState gameState = new GameState();

		GameStateController controller = new GameStateController();

		// Begin game loop
		while(!gameOver){
			// TODO
			// System.out.println("Game is running.");


			controller.storeGameState(gameState);

			VisualizerTurnProtos.VisualizerTurn turn = gameState.constructVisualizerTurn();
			controller.storeVisualizerTurn(turn);
			VisualizerBinaryWebSocketHandler.sendTurn(turn);

			turnCount++;
		}
	}

	public static void setGameOver(boolean g) {
		gameOver = g;
	}
}
