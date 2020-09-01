package mech.mania.engine.service_layer;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.model.CharacterProtos;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import mech.mania.engine.domain.model.InfraProtos.InfraStatus;
import mech.mania.engine.domain.model.ApiProtos;
import mech.mania.engine.domain.game.pathfinding.PathFinder;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.utils;

import javax.annotation.Resource;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * A class to execute the game loop and other structural procedures.
 */
@SpringBootApplication
@RequestMapping("/api")
@RestController
@ComponentScan("mech.mania.engine.entrypoints")
public class APIRESTHandler {
    private final Logger LOGGER = Logger.getLogger( getClass().getName() );

    @Resource
    private MessageBus bus;

    /**
     * Method to handle GET requests to the /health endpoint to check that the server is running correctly.
     * @return status proto with status code 200
     */
    @GetMapping("/health")
    public @ResponseBody byte[] health() {

        return InfraStatus.newBuilder()
                .setStatus(200)
                .setMessage("Alive.")
                .build()
                .toByteArray();
    }

    @GetMapping("/pathFinding")
    public byte[] pathFinding(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIPathFindingRequest requestInfo = ApiProtos.APIPathFindingRequest.parseFrom(payload);
            Position start = new Position(requestInfo.getStart());
            Position end = new Position(requestInfo.getEnd());
            GameState gameState = new GameState(requestInfo.getGameState());

            List<Position> path = PathFinder.findPath(gameState, start, end);

            ApiProtos.APIPathFindingResponse.Builder responseBuilder = ApiProtos.APIPathFindingResponse.newBuilder();
            List<CharacterProtos.Position> protoPositions = new ArrayList<>();
            for (Position pos : path) {
                protoPositions.add(pos.buildProtoClass());
            }
            responseBuilder.addAllPath(protoPositions);
            LOGGER.fine(String.format("Successfully processed ApiPathFindingRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /pathFinding request from player: " + e.getMessage());
            return ApiProtos.APIPathFindingResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                                    .setStatus(400)
                                    .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                                    .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/findEnemies")
    public byte[] getEnemies(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindEnemiesRequest requestInfo = ApiProtos.APIFindEnemiesRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String player_name = requestInfo.getPlayerName();

            List<Character> enemies = utils.findEnemies(gameState, player_name);

            ApiProtos.APIFindEnemiesResponse.Builder responseBuilder = ApiProtos.APIFindEnemiesResponse.newBuilder();
            List<CharacterProtos.Character> protoEnemies = new ArrayList<>();
            for (Character enemy : enemies) {
                protoEnemies.add(enemy.buildProtoClassCharacter());
            }
            responseBuilder.addAllEnemies(protoEnemies);
            LOGGER.fine(String.format("Successfully processed ApiFindEnemiesRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findEnemies request from player: " + e.getMessage());
            return ApiProtos.APIFindEnemiesResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/findMonsters")
    public byte[] getMonsters(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindMonstersRequest requestInfo = ApiProtos.APIFindMonstersRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String player_name = requestInfo.getPlayerName();

            List<Monster> monsters = utils.findMonsters(gameState, player_name);

            ApiProtos.APIFindMonstersResponse.Builder responseBuilder = ApiProtos.APIFindMonstersResponse.newBuilder();
            List<CharacterProtos.Monster> protoMonsters = new ArrayList<>();
            for (Monster monster : monsters) {
                protoMonsters.add(monster.buildProtoClassMonster());
            }
            responseBuilder.addAllMonsters(protoMonsters);
            LOGGER.fine(String.format("Successfully processed ApiFindMonstersRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findMonsters request from player: " + e.getMessage());
            return ApiProtos.APIFindMonstersResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/findEnemiesInRange")
    public byte[] getEnemiesInRange(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindEnemiesInRangeRequest requestInfo = ApiProtos.APIFindEnemiesInRangeRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String player_name = requestInfo.getPlayerName();

            List<Character> enemies = utils.findEnemiesInRange(gameState, player_name);

            ApiProtos.APIFindEnemiesInRangeResponse.Builder responseBuilder = ApiProtos.APIFindEnemiesInRangeResponse.newBuilder();
            List<CharacterProtos.Character> protoEnemies = new ArrayList<>();
            for (Character enemy : enemies) {
                protoEnemies.add(enemy.buildProtoClassCharacter());
            }
            responseBuilder.addAllEnemies(protoEnemies);
            LOGGER.fine(String.format("Successfully processed ApiFindEnemiesInRange."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findEnemiesInRange request from player: " + e.getMessage());
            return ApiProtos.APIFindEnemiesInRangeResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/canBeAttacked")
    public byte[] canBeAttacked(@RequestBody byte[] payload) {
        try {
            ApiProtos.APICanBeAttackedRequest requestInfo = ApiProtos.APICanBeAttackedRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String player_name = requestInfo.getPlayerName();

            boolean canPlayerBeAttacked = utils.canBeAttacked(gameState, player_name);

            ApiProtos.APICanBeAttackedResponse.Builder responseBuilder = ApiProtos.APICanBeAttackedResponse.newBuilder();
            responseBuilder.setCanBeAttacked(canPlayerBeAttacked);
            LOGGER.fine(String.format("Successfully processed ApiCanBeAttackedRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /canBeAttacked request from player: " + e.getMessage());
            return ApiProtos.APICanBeAttackedResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/findClosestPortal")
    public byte[] getClosestPortal(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindClosestPortalRequest requestInfo = ApiProtos.APIFindClosestPortalRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String player_name = requestInfo.getPlayerName();

            Position portal = utils.findClosestPortal(gameState, player_name);

            ApiProtos.APIFindClosestPortalResponse.Builder responseBuilder = ApiProtos.APIFindClosestPortalResponse.newBuilder();
            responseBuilder.setPortal(portal.buildProtoClass());
            LOGGER.fine(String.format("Successfully processed ApiFindClosestPortalRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findClosestPortal request from player: " + e.getMessage());
            return ApiProtos.APIFindClosestPortalResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/leaderBoard")
    public byte[] getLeaderBoard(@RequestBody byte[] payload) {
        try {
            ApiProtos.APILeaderBoardRequest requestInfo = ApiProtos.APILeaderBoardRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());

            List<Player> leaderBoard = utils.leaderBoard(gameState);

            ApiProtos.APILeaderBoardResponse.Builder responseBuilder = ApiProtos.APILeaderBoardResponse.newBuilder();
            List<CharacterProtos.Player> protoLeaderBoard = new ArrayList<>();
            for (Player player : leaderBoard) {
                protoLeaderBoard.add(player.buildProtoClassPlayer());
            }
            responseBuilder.addAllLeaderBoard(protoLeaderBoard);
            LOGGER.fine(String.format("Successfully processed ApiLeaderBoardRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /leaderBoard request from player: " + e.getMessage());
            return ApiProtos.APILeaderBoardResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @GetMapping("/findAllEnemiesHit")
    public byte[] getAllEnemiesHit(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIAllEnemiesHitRequest requestInfo = ApiProtos.APIAllEnemiesHitRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String playerName = requestInfo.getPlayerName();
            Position targetSpot = new Position(requestInfo.getTargetSpot());

            List<Character> allEnemiesHit= utils.findAllEnemiesHit(gameState, playerName, targetSpot);

            ApiProtos.APIAllEnemiesHitResponse.Builder responseBuilder = ApiProtos.APIAllEnemiesHitResponse.newBuilder();
            List<CharacterProtos.Character> protoAllEnemiesHit = new ArrayList<>();
            for (Character character : allEnemiesHit) {
                protoAllEnemiesHit.add(character.buildProtoClassCharacter());
            }
            responseBuilder.addAllEnemiesHit(protoAllEnemiesHit);
            LOGGER.fine(String.format("Successfully processed ApiAllEnemiesHitRequest."));

            return responseBuilder.build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findAllEnemiesHit request from player: " + e.getMessage());
            return ApiProtos.APIAllEnemiesHitResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }
}
