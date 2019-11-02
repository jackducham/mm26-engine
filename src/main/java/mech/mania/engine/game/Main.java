package mech.mania.engine.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class Main {
	public static boolean gameOver = false;
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

		// Begin game loop
		while(!gameOver){
			// TODO
			//System.out.println("Game is running.");
			turnCount++;
		}
	}

}
