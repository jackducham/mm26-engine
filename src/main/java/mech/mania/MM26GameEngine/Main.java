package mech.mania.MM26GameEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class Main {
	public static boolean gameOver = false;

	public static void main(String[] args) {
		// Start server to communicate with infrastructure
		SpringApplication app = new SpringApplication(Main.class);
		String port = System.getenv("PORT");
		app.setDefaultProperties(Collections
				.singletonMap("server.port", port));
		app.run(args);

		// TODO: Start web socket to communicate with visualizer


		// TODO: Initialize game


		// Begin game loop
		while(!gameOver){
			// TODO
		}
	}

}
