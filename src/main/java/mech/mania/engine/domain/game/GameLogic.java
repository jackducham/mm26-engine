package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.*;
import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.GameChange;
import mech.mania.engine.domain.model.PlayerProtos;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mech.mania.engine.domain.game.pathfinding.PathFinder.findPath;

/**
 * A class to execute the game logic.
 */
public class GameLogic {
    /**
     * Returns a VisualizerChange object that denotes how the gameState
     * has changed in relevant terms to the Visualizer team
     */
    public static GameChange constructGameChange(GameState gameState) {
        return gameState.stateChange;
    }


    /**
     * Constructs a PlayerTurn for a specific player using a
     * GameState and a specific player's name
     * @return PlayerTurn a playerTurn specific for a player
     */
    public static PlayerProtos.PlayerTurn constructPlayerTurn(GameState gameState, String playerName) {
        return PlayerProtos.PlayerTurn.newBuilder()
                .setGameState(gameState.buildProtoClass())
                .setPlayerName(playerName)
                .build();
    }


    /**
     * Executes the logic of one turn given a starting {@link GameState} and a list of {@link CharacterProtos.CharacterDecision}s.
     * @param gameState The initial game state.
     * @param constestantDecisions A list of player decisions.
     * @return the resulting {@link GameState}.
     */
    public static GameState doTurn(GameState gameState, Map<String, CharacterProtos.CharacterDecision> constestantDecisions) {
        // ========== NOTES & TODOS ========== \\
        // Note: VisualizerChange will be sent later via Main.java, so no need to worry about that here

        // Clear changes for visualizer
        gameState.stateChange.clearChanges();

        // ========== CONVERT DECISIONS AND REMOVE DECISIONS MADE BY DEAD PLAYERS ========== \\
        // Search decisions map for new players. For each new player, create Player object and a private Board
        for (String playerName : constestantDecisions.keySet()) {
            if (!gameState.getAllPlayers().containsKey(playerName)) {
                gameState.addNewPlayer(playerName);
            }
        }

        // ========== CONVERT DECISIONS AND REMOVE DECISIONS MADE BY DEAD PLAYERS ========== \\
        Map<String, CharacterDecision> allDecisions = new HashMap<String, CharacterDecision>();
        for (Map.Entry<String, CharacterProtos.CharacterDecision> entry : constestantDecisions.entrySet()) {
            // Remove decision from dead players and NONE decisions
            if(!gameState.getPlayer(entry.getKey()).isDead()
                    && entry.getValue() != null
                    && entry.getValue().getDecisionType() != CharacterProtos.DecisionType.NONE) {
                CharacterDecision newDecision = new CharacterDecision(entry.getValue());
                allDecisions.put(entry.getKey(), newDecision);
            }
        }

        // ========== CONVERT DECISIONS FROM MONSTERS ========== \\
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            if(entry.getValue() == null) continue;
            CharacterDecision decision = entry.getValue().makeDecision(gameState);
            // Remove decision from dead monsters and NONE decisions
            if(!gameState.getMonster(entry.getKey()).isDead()
                    && decision != null
                    && decision.getDecision() != CharacterDecision.decisionTypes.NONE) {
                allDecisions.put(entry.getKey(), decision);
            }
        }

        // ========== SORT DECISIONS ========== \\
        Map<String, CharacterDecision> inventoryActions = new HashMap<>();
        Map<String, CharacterDecision> attackActions = new HashMap<>();
        Map<String, CharacterDecision> movementActions = new HashMap<>();

        for (Map.Entry<String, CharacterDecision> entry : allDecisions.entrySet()) {
            if (entry.getValue().getDecision() == CharacterDecision.decisionTypes.PICKUP
                    || entry.getValue().getDecision() == CharacterDecision.decisionTypes.EQUIP
                    || entry.getValue().getDecision() == CharacterDecision.decisionTypes.DROP) {
                inventoryActions.put(entry.getKey(), entry.getValue());

            } else if (entry.getValue().getDecision() == CharacterDecision.decisionTypes.ATTACK) {
                attackActions.put(entry.getKey(), entry.getValue());

            } else {
                movementActions.put(entry.getKey(), entry.getValue());
            }
        }


        // ========== HANDLE INVENTORY ACTIONS ========== \\
        for (Map.Entry<String, CharacterDecision> entry : inventoryActions.entrySet()) {
            processDecision(gameState, gameState.getCharacter(entry.getKey()), entry.getValue());
        }


        // ========== HANDLE ATTACK ACTIONS ========== \\
        for (Map.Entry<String, CharacterDecision> entry : attackActions.entrySet()) {
            processDecision(gameState, gameState.getCharacter(entry.getKey()), entry.getValue());
        }


        // ========== HANDLE MOVEMENT ACTIONS ========== \\
        for (Map.Entry<String, CharacterDecision> entry : movementActions.entrySet()) {
            processDecision(gameState, gameState.getCharacter(entry.getKey()), entry.getValue());
        }


        // ========== UPDATE PLAYER FUNCTIONS ========== \\
        //updateCharacter handles clearing active effects, setting status to dead/alive,
        // respawning, and distributing rewards
        Collection<Player> players = gameState.getAllPlayers().values();
        Collection<Monster> monsters = gameState.getAllMonsters().values();

        for (Player player: players) {
            player.updateCharacter(gameState);
        }
        for (Monster monster: monsters) {
            monster.updateCharacter(gameState);
        }

        return gameState;
    }

    /**
     * Passes individual decisions to the appropriate decision handler based on type.
     * @param gameState the current game state (used to check the conditions of the game state, and to manipulate items)
     * @param character the character whose decision is being processed
     * @param decision the decision being processed
     */
    public static void processDecision(GameState gameState, Character character, CharacterDecision decision) {
        // Check for invalid protos
        if (decision == null) return;

        Position actionPosition = decision.getActionPosition();
        int index = decision.getIndex();

        switch (decision.getDecision()) {
            case ATTACK:
                // Check for invalid protos
                if(character == null || actionPosition == null) return;
                processAttack(gameState, character, actionPosition);
                break;
            case MOVE:
                // Check for invalid protos
                if(character == null || actionPosition == null) return;
                moveCharacter(gameState, character, actionPosition);
                break;
            case PORTAL:
                // Check for invalid protos
                if(character == null || index < 0) return;
                usePortal(gameState, character, index);
                break;
            case EQUIP:
                // Check for invalid protos
                if(character == null || index < 0) return;
                Player player = (Player) character;
                player.equipItem(index);
                break;
            case DROP:
                // Check for invalid protos
                if(character == null || index < 0) return;
                player = (Player) character;
                dropItem(gameState, player, index);
                break;
            case PICKUP:
                // Check for invalid protos
                if(character == null || index < 0) return;
                player = (Player) character;
                pickUpItem(gameState, player, index);
                break;
        }
        gameState.stateChange.updatePlayer(character, decision, null, false, false);
    }

    /**
     * Moves a given character to a given position if it is reachable. Only used for moving characters within a single board.
     * @param gameState current gameState
     * @param character player to be moved
     * @param targetPosition position the player should be moved to
     */
    public static void moveCharacter(GameState gameState, Character character, Position targetPosition) {
        if (!validatePosition(gameState, targetPosition)) return;

        // Get shortest path length from current to target position (returns empty list for impossible target)
        List<Position> path = findPath(gameState, character.getPosition(), targetPosition);

        // If path is empty (i.e. target is unreachable), don't move
        if(path.size() == 0) return;

        // If path would be greater than speed allows, act as if impossible target was chosen and don't move
        if(path.size() > character.getSpeed()) return;

        character.setPosition(targetPosition);
        gameState.stateChange.updatePlayer(character, null, path, false, false);
    }

    // ============================= PORTAL FUNCTIONS ================================================================== //

    /**
     * Checks whether a player can take the UsePortal action.
     * @param gameState current gameState
     * @param player player to be moved
     * @return true if the action can be taken, false otherwise
     */
    public static boolean canUsePortal(GameState gameState, Character player) {
        if(player instanceof Monster) return false; // Only players can take portals

        //checks the portals on the player's current board
        for(int i = 0; i < gameState.getBoard(player.getPosition().getBoardID()).getPortals().size(); i++) {
            if(player.getPosition().equals(gameState.getBoard(player.getPosition().getBoardID()).getPortals().get(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * Moves a player to the portal at given index, if they are able.
     * @param gameState current gameState
     * @param player player using a portal
     * @param portalIndex index of target portal on pvpBoard or -1 if targeting the home portal
     * @return true if successful
     */
    public static boolean usePortal(GameState gameState, Character player, int portalIndex) {
        if(canUsePortal(gameState, player)) {

            //sends player to home board
            if(portalIndex == -1) {
                player.setPosition(gameState.getBoard(player.getSpawnPoint().getBoardID()).getPortals().get(0));
                return true;
            }

            //checks for out of bounds indices
            if(portalIndex >= gameState.getPvpBoard().getPortals().size() || portalIndex < -1) {
                return false;
            }

            //handles usual cases
            player.setPosition(gameState.getPvpBoard().getPortals().get(portalIndex));
            return true;
        }

        return false;
    }

    // ============================= ATTACKING HELPER FUNCTIONS ======================================================== //

    /**
     * Validate whether character's weapon isn't null and if target Position is within range and on the board
     * @param character character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @param gameState current gameState
     * @return true if attackCoordinate is valid, false otherwise
     */
    public static boolean validateAttack(GameState gameState, Character character, Position attackCoordinate) {
        Weapon playerWeapon = character.getWeapon();
        if (playerWeapon == null) {
            return false;
        }

        if (!validatePosition(gameState, attackCoordinate)) {
            return false;
        }

        return calculateManhattanDistance(character.getPosition(), attackCoordinate) <= playerWeapon.getRange();
    }

    /**
     * Provides a list of position affected by a given player's attack.
     * @param character character that's doing the attacking
     * @param attackCoordinate central Position where the weapon is attacking
     * @param gameState current gameState
     * @return hashmap of Positions that would get attacked by the player's weapon
     */
    public static Map<Position, Integer> returnAffectedPositions(GameState gameState, Character character, Position attackCoordinate) {
        if (!validateAttack(gameState, character, attackCoordinate)) {
            return null;
        }
        Weapon weapon = character.getWeapon();
        int radius = weapon.getSplashRadius();
        Map<Position, Integer> affectedPositions = new HashMap<>();

        int centerX = attackCoordinate.getX();
        int centerY = attackCoordinate.getY();

        int xMin = Math.max((centerX - radius), 0);
        int yMin = Math.max((centerY - radius), 0);

        for (int x = xMin; x <= centerX + radius; x++) {
            for (int y = yMin; y <= centerY + radius; y++) {
                Position position = new Position(x, y, attackCoordinate.getBoardID());
                if (calculateManhattanDistance(position, attackCoordinate) <= radius && validatePosition(gameState, position)) {
                    affectedPositions.put(position, 1);
                }
            }
        }

        return affectedPositions;
    }

    /**
     * Applies Weapon's onHitEffect to the TempStatusModifier of all characters within the range of the attackCoordinate
     * @param gameState current gameState
     * @param attacker character doing the attacking
     * @param attackCoordinate coordinate to attack
     */
    public static void processAttack(GameState gameState, Character attacker, Position attackCoordinate) {
        Board board = gameState.getBoard(attackCoordinate.getBoardID());
        List<Monster> monsters = gameState.getMonstersOnBoard(attackCoordinate.getBoardID());
        List<Player> players = gameState.getPlayersOnBoard(attackCoordinate.getBoardID());
        Map<Position, Integer> affectedPositions = returnAffectedPositions(gameState, attacker, attackCoordinate);

        Weapon attackerWeapon = attacker.getWeapon();

        // Character gave invalid attack position
        if (affectedPositions == null || affectedPositions.isEmpty()) {
            return;
        }

        for (Player player: players) {
            if (player == attacker) {
                continue;
            }
            Position playerPos = player.getPosition();
            if (affectedPositions.containsKey(playerPos)) {
                // SPECIAL CASE: Hat effect TRIPLED_ON_HIT
                if(attacker instanceof Player && ((Player) attacker).getHat() != null
                        && ((Player) attacker).getHat().getHatEffect().equals(HatEffect.TRIPLED_ON_HIT)) {
                    Weapon zeroDamageVersion = new Weapon(new StatusModifier(attackerWeapon.getStats()),
                            attackerWeapon.getRange(), attackerWeapon.getSplashRadius(), 0,
                            new TempStatusModifier(attackerWeapon.getOnHitEffect()));
                    player.hitByWeapon(attacker.getName(), true, zeroDamageVersion, attacker.getAttack());
                    player.hitByWeapon(attacker.getName(), true, zeroDamageVersion, attacker.getAttack());
                    player.hitByWeapon(attacker.getName(), true, zeroDamageVersion, attacker.getAttack());
                } else {
                    // Decide whether to add to taggedPlayersDamage
                    if(attacker instanceof Player) {
                        player.hitByWeapon(attacker.getName(), true, attackerWeapon, attacker.getAttack());
                    }
                    else{
                        player.hitByWeapon(attacker.getName(), false, attackerWeapon, attacker.getAttack());
                    }
                }
            }
        }

        for (Monster monster: monsters) {
            if (monster == attacker) {
                continue;
            }
            Position playerPos = monster.getPosition();
            if (affectedPositions.containsKey(playerPos)) {
                // Decide whether to add to taggedPlayersDamage
                if(attacker instanceof Player) {
                    monster.hitByWeapon(attacker.getName(), true, attackerWeapon, attacker.getAttack());
                }
                else{
                    monster.hitByWeapon(attacker.getName(), false, attackerWeapon, attacker.getAttack());
                }
            }
        }

        return;
    }


    // ============================= ITEM FUNCTIONS ==================================================================== //

    /**
     * Adds an item to the Player's inventory and removes it from the tile.
     * @param gameState current gameState
     * @param player player to give the item to
     * @param index index of the item in the tile's items that the player is picking up
     * @return true if successful
     */
    public static boolean pickUpItem(GameState gameState, Player player, int index) {
        Tile tile = getTileAtPosition(gameState, player.getPosition());
        if (tile == null) {
            return false;
        }
        if (index < 0 || index >= tile.getItems().size()) {
            return false;
        }
        int playerInventoryIndex = player.getFreeInventoryIndex();
        if (playerInventoryIndex == -1) {
            return false;
        }

        Item item = tile.getItems().get(index);
        tile.removeItem(index);
        player.setInventory(playerInventoryIndex, item);
        return true;
    }

    /**
     * Removes one item from a Player's inventory and adds it to the items on a tile.
     * @param gameState current gameState
     * @param player the player dropping items
     * @param index the index of the item in the player's inventory which is being dropped
     * @return true if successful
     */
    public static boolean dropItem(GameState gameState, Player player, int index) {
        Tile currentTile = getTileAtPosition(gameState, player.getPosition());
        if (index < 0 || index >= player.getInventorySize()) {
            return false;
        }

        if (player.getInventory()[index] == null) {
            return false;
        }

        Item item = player.getInventory()[index];
        player.setInventory(index, null);
        currentTile.addItem(item);
        return true;
    }

    // ============================= GENERAL HELPER FUNCTIONS ========================================================== //

    /**
     * Provides the Tile at a given Position.
     * @param gameState current gameState
     * @param position position of the Tile to be retrieved
     * @return the Tile at the given position
     */
    public static Tile getTileAtPosition(GameState gameState, Position position) {
        if (!validatePosition(gameState, position)) {
            return null;
        }
        return gameState.getBoard(position.getBoardID()).getGrid()[position.getX()][position.getY()];
    }

    /**
     * Checks whether position is within the bounds of the board
     * @param gameState
     * @param position position to validate
     * @return true, if position is valid
     */
    public static boolean validatePosition(GameState gameState, Position position) {
        Board board = gameState.getBoard(position.getBoardID());
        if(board == null){return false;}

        if (position.getX() > board.getGrid()[0].length || position.getX() < 0) {
            return false;
        }

        if (position.getY() > board.getGrid().length || position.getY() < 0) {
            return false;
        }

        return board.getGrid()[position.getY()][position.getX()].getType() != Tile.TileType.VOID;
    }

    /**
     * @param pos1 first position
     * @param pos2 second position
     * @return Manhattan Distance between pos1 and pos2
     */
    public static int calculateManhattanDistance(Position pos1, Position pos2) {
        if(pos1.getBoardID().equals(pos2.getBoardID())){
            return Math.abs(pos1.getX() - pos2.getX()) + Math.abs(pos1.getY() - pos2.getY());
        }
        return Integer.MAX_VALUE;
    }
}