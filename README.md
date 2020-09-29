# MechMania 26 Game Engine

![MechMania 26 Logo](https://mechmania.io/images/MM26LOGO.png)

## Overview
The engine this year was simply a set of 3 Spring boot servers that were launched at the beginning of the game:
1. a server to communicate with the infrastructure for receiving teams
2. a server to connect via websocket to any/all visualizer instances
3. a server to handle API requests from competitors

## Running Locally
To run the engine locally, first clone this repo.

Depending on what you have, here are the two main ways to run the engine:
- docker: There is a Dockerfile located in this repo, so running `docker build -t mm26-engine .` while in the directory should build everything correctly, and `docker run -t mm26-engine` should then run that image.
- maven: This project was a maven-based Java project, which means that running `mvn install` (creates a jar in the directory `target`) then `java -jar target/MM26GameEngine-0.0.1-SNAPSHOT.jar` will run the server (this is what the Dockerfile under the hood).

By default, the 3 servers are configured to these ports:
- Infrastructure: port 8080
- Visualizers: port 8081
- API: port 8082

These defaults can be configured in `src/main/java/resources/config.properties`. They can also be set through the command line arguments to the program with `--infraPort`, `--visualizerPort`, and `--apiPort`.

## Connecting Players
The players during the tournament were spun up by the infrastructure team, then were passed to the game engine, where the game engine would then contact the teams each turn via HTTP (we now understand that this is a slow way of communicating).

The way that infrastructure communicates with the engine is through an HTTP POST request with the player name and player IP address to allow the game engine to connect. We _will_ include a small script that will send the correct request to the engine given a player name and port (IP defaults to localhost).

## Competition Afterhours
Now that the official competition is over, the engine, which relied on AWS to store GameState objects each turn, an infrastructure server to be receiving requests from, and a visualizer that accessed the engine through a separate host, must be reconfigured slightly in order to work properly (if running locally).

- The AWS database requirement can be circumvented by allowing engine to not be able to soft reset or store new game states physically.
- The infrastructure server can be mocked using HTTP POST requests, keeping in mind the method of communication ([link to wiki](https://github.com/jackducham/mm26-engine/wiki/Server-Communication), see "Connecting Players")
- The visualizer, unfortunately, must be manually re-compiled due to a hard-coded address to access the engine.

We _will_ modify the engine to now be able to soft-reset from the end of the competition (using argument `--recoverComp`; see `src/main/java/mech/mania/engine/entrypoints/Main.java`). Officially, this is hard coded to recover turn 16,128.

TO DO:
- [ ] Include script to send infra's connect request
- [ ] Upload game state for turn 16,128
- [ ] Modify engine to soft-reset to turn 16,128
