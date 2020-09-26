package mech.mania.engine.service_layer;

import com.google.protobuf.InvalidProtocolBufferException;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Item;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.ItemProtos;
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
import java.util.AbstractMap;
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

    public ApiProtos.APIStatus getSuccessStatus(){
        return ApiProtos.APIStatus.newBuilder()
                .setStatus(200)
                .setMessage("Success")
                .build();
    }

    @PostMapping("/pathFinding")
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

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

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

    @PostMapping("/findEnemiesByDistance")
    public byte[] findEnemiesByDistanceAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindEnemiesByDistanceRequest requestInfo = ApiProtos.APIFindEnemiesByDistanceRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            Position position = new Position(requestInfo.getPosition());
            String player_name = requestInfo.getPlayerName();

            List<Character> enemies = utils.findEnemiesByDistance(gameState, position, player_name);

            ApiProtos.APIFindEnemiesByDistanceResponse.Builder responseBuilder = ApiProtos.APIFindEnemiesByDistanceResponse.newBuilder();
            List<CharacterProtos.Character> protoEnemies = new ArrayList<>();
            for (Character enemy : enemies) {
                protoEnemies.add(enemy.buildProtoClassCharacter());
            }
            responseBuilder.addAllEnemies(protoEnemies);
            LOGGER.fine(String.format("Successfully processed ApiFindEnemiesByDistanceRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findEnemiesByDistance request from player: " + e.getMessage());
            return ApiProtos.APIFindEnemiesByDistanceResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @PostMapping("/findMonstersByExp")
    public byte[] findMonstersByExpAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindMonstersByExpRequest requestInfo = ApiProtos.APIFindMonstersByExpRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            Position position = new Position(requestInfo.getPosition());

            List<Monster> monsters = utils.findMonstersByExp(gameState, position);

            ApiProtos.APIFindMonstersByExpResponse.Builder responseBuilder = ApiProtos.APIFindMonstersByExpResponse.newBuilder();
            List<CharacterProtos.Monster> protoMonsters = new ArrayList<>();
            for (Monster monster : monsters) {
                protoMonsters.add(monster.buildProtoClassMonster());
            }
            responseBuilder.addAllMonsters(protoMonsters);
            LOGGER.fine(String.format("Successfully processed ApiFindMonstersByExpRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findMonstersByExp request from player: " + e.getMessage());
            return ApiProtos.APIFindMonstersByExpResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @PostMapping("/findItemsInRangeByDistance")
    public byte[] findItemsInRangeByDistanceAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindItemsInRangeByDistanceRequest requestInfo = ApiProtos.APIFindItemsInRangeByDistanceRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            String playerName = requestInfo.getPlayerName();
            Position position = new Position(requestInfo.getPosition());
            int range = requestInfo.getRange();

            List<AbstractMap.SimpleEntry<Item, Position>> itemsInRange = utils.findItemsInRangeByDistance(gameState, position, playerName, range);

            ApiProtos.APIFindItemsInRangeByDistanceResponse.Builder responseBuilder = ApiProtos.APIFindItemsInRangeByDistanceResponse.newBuilder();
            List<ItemProtos.Item> protoItems = new ArrayList<>();
            List<CharacterProtos.Position> protoPositions = new ArrayList<>();
            for (AbstractMap.SimpleEntry<Item, Position> entry : itemsInRange) {
                protoItems.add(entry.getKey().buildProtoClassItem());
                protoPositions.add(entry.getValue().buildProtoClass());
            }
            responseBuilder.addAllItems(protoItems);
            responseBuilder.addAllPositions(protoPositions);
            LOGGER.fine(String.format("Successfully processed ApiFindItemsByDistanceRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findItemsInRangeByDistance request from player: " + e.getMessage());
            return ApiProtos.APIFindItemsInRangeByDistanceResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @PostMapping("/findEnemiesInRangeOfAttackByDistance")
    public byte[] findEnemiesInRangeOfAttackByDistanceAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindEnemiesInRangeOfAttackByDistanceRequest requestInfo = ApiProtos.APIFindEnemiesInRangeOfAttackByDistanceRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            Position position = new Position(requestInfo.getPosition());
            String player_name = requestInfo.getPlayerName();

            List<Character> enemies = utils.findEnemiesInRangeOfAttackByDistance(gameState, position, player_name);

            ApiProtos.APIFindEnemiesInRangeOfAttackByDistanceResponse.Builder responseBuilder = ApiProtos.APIFindEnemiesInRangeOfAttackByDistanceResponse.newBuilder();
            List<CharacterProtos.Character> protoEnemies = new ArrayList<>();
            for (Character enemy : enemies) {
                protoEnemies.add(enemy.buildProtoClassCharacter());
            }
            responseBuilder.addAllEnemies(protoEnemies);
            LOGGER.fine(String.format("Successfully processed ApiFindEnemiesInRangeOfAttackByDistanceRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findEnemiesInRangeOfAttackByDistance request from player: " + e.getMessage());
            return ApiProtos.APIFindEnemiesInRangeOfAttackByDistanceResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }


    @PostMapping("/findAllEnemiesHit")
    public byte[] findAllEnemiesHitRequestAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindAllEnemiesHitRequest requestInfo = ApiProtos.APIFindAllEnemiesHitRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            Position position = new Position(requestInfo.getPosition());
            String playerName = requestInfo.getPlayerName();

            List<Character> allEnemiesHit = utils.findAllEnemiesHit(gameState, position, playerName);

            ApiProtos.APIFindAllEnemiesHitResponse.Builder responseBuilder = ApiProtos.APIFindAllEnemiesHitResponse.newBuilder();
            List<CharacterProtos.Character> protoAllEnemiesHit = new ArrayList<>();
            for (Character character : allEnemiesHit) {
                protoAllEnemiesHit.add(character.buildProtoClassCharacter());
            }
            responseBuilder.addAllEnemiesHit(protoAllEnemiesHit);
            LOGGER.fine(String.format("Successfully processed ApiFindAllEnemiesHitRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /findAllEnemiesHit request from player: " + e.getMessage());
            return ApiProtos.APIFindAllEnemiesHitResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @PostMapping("/inRangeOfAttack")
    public byte[] inRangeOfAttackAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIInRangeOfAttackRequest requestInfo = ApiProtos.APIInRangeOfAttackRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            Position position = new Position(requestInfo.getPosition());
            String player_name = requestInfo.getPlayerName();

            boolean inRangeOfAttack = utils.inRangeOfAttack(gameState, position, player_name);

            ApiProtos.APIInRangeOfAttackResponse.Builder responseBuilder = ApiProtos.APIInRangeOfAttackResponse.newBuilder();
            responseBuilder.setInRangeOfAttack(inRangeOfAttack);
            LOGGER.fine(String.format("Successfully processed ApiInRangeOfAttackRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

        } catch (InvalidProtocolBufferException e) {
            // log that error occurred
            LOGGER.warning("InvalidProtocolBufferException on /inRangeOfAttack request from player: " + e.getMessage());
            return ApiProtos.APIInRangeOfAttackResponse.newBuilder()
                    .setStatus(ApiProtos.APIStatus.newBuilder()
                            .setStatus(400)
                            .setMessage("InvalidProtocolBufferException: " + e.getMessage())
                            .build())
                    .build()
                    .toByteArray();
        }
    }

    @PostMapping("/findClosestPortal")
    public byte[] findClosestPortalAPI(@RequestBody byte[] payload) {
        try {
            ApiProtos.APIFindClosestPortalRequest requestInfo = ApiProtos.APIFindClosestPortalRequest.parseFrom(payload);
            GameState gameState = new GameState(requestInfo.getGameState());
            Position position = new Position(requestInfo.getPosition());

            Position portal = utils.findClosestPortal(gameState, position);

            if (portal.getBoardID().equals("no_portal")) {
                return ApiProtos.APIFindClosestPortalResponse.newBuilder()
                        .setStatus(
                                ApiProtos.APIStatus.newBuilder()
                                .setStatus(404)
                                .setMessage("No portal found.")
                                .build()
                        )
                        .build().toByteArray();
            }

            ApiProtos.APIFindClosestPortalResponse.Builder responseBuilder = ApiProtos.APIFindClosestPortalResponse.newBuilder();
            responseBuilder.setPortal(portal.buildProtoClass());
            LOGGER.fine(String.format("Successfully processed ApiFindClosestPortalRequest."));

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

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

    @PostMapping("/leaderBoard")
    public byte[] leaderBoardAPI(@RequestBody byte[] payload) {
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

            return responseBuilder.setStatus(getSuccessStatus()).build().toByteArray();

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
}
