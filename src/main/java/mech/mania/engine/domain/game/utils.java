package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.board.Tile;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.items.Item;

import java.util.*;

public class utils {

    public static List<Character> findEnemiesByDistance(GameState gameState, Position position, String playerName) {
        List<AbstractMap.SimpleEntry<Character, Integer>> enemiesDist = new ArrayList<>();
        for (Character character : gameState.getCharactersOnBoard(position.getBoardID())) {
            if (!character.getName().equals(playerName)) {
                enemiesDist.add(new AbstractMap.SimpleEntry<>(character, position.manhattanDistance(character.getPosition())));
            }
        }

        Comparator<AbstractMap.SimpleEntry<Character, Integer>> compareByDistance = Comparator.comparing(AbstractMap.SimpleEntry<Character, Integer>::getValue);
        Collections.sort(enemiesDist, compareByDistance);
        List<Character> enemies = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Character, Integer> dist : enemiesDist) {
            enemies.add(dist.getKey());
        }
        return enemies;
    }

    public static List<Monster> findMonstersByExp(GameState gameState, Position position) {
        List<Monster> monsters = gameState.getMonstersOnBoard(position.getBoardID());
        Comparator<Monster> compareByExp = Comparator.comparing(Monster::getTotalExperience);
        Collections.sort(monsters, compareByExp.reversed());

        return monsters;
    }

    public static List<AbstractMap.SimpleEntry<Item, Position>> findItemsInRangeByDistance(GameState gameState, Position position, String playerName, int range) {
        Player player = gameState.getPlayer(playerName);
        if (player == null) {
            return null;
        }
        String boardId = position.getBoardID();
        int playerX = position.getX();
        int playerY = position.getY();
        Tile[][] grid = gameState.getBoard(boardId).getGrid();
        range = Math.min(range, 2 * gameState.getPlayer(playerName).getSpeed());
        List<AbstractMap.SimpleEntry<Item, Position>> res = new ArrayList<>();

        for (int i = 0; i <= range; i++) {
            int total;
            if (i == 0) {
                total = 1;
            } else {
                total = i*4;
            }
            int ctr = 0;

            int x_start = 0;
            boolean x_inc = true;
            int y_start = i;
            boolean y_inc = false;

            while(ctr < total) {
                int x_pos = x_start + playerX;
                int y_pos = y_start + playerY;
                if (x_pos >= 0 && x_pos < grid.length && y_pos >= 0 && y_pos < grid[0].length) {
                    List<Item> items = grid[x_pos][y_pos].getItems();
                    if (items != null && items.size() > 0) {
                        for (Item item : items) {
                            res.add(new AbstractMap.SimpleEntry<>(item, new Position(x_pos, y_pos, boardId)));
                        }
                    }
                }

                if (x_start == i) {
                    x_inc = false;
                }
                else if (x_start == -i) {
                    x_inc = true;
                }

                if (x_inc) {
                    x_start += 1;
                } else {
                    x_start -= 1;
                }

                if (y_start == i) {
                    y_inc = false;
                }
                else if (y_start == -i) {
                    y_inc = true;
                }
                if (y_inc) {
                    y_start += 1;
                } else {
                    y_start -= 1;
                }
                ctr += 1;
            }
        }
        return res;
    }

    public static List<Character> findEnemiesInRangeOfAttackByDistance(GameState gameState, Position position, String playerName) {
        Player player = gameState.getPlayer(playerName);
        if (player == null) {
            return null;
        }
        Weapon weapon = player.getWeapon();
        if (weapon == null) {
            return new ArrayList<>();
        }

        return findEnemiesInRangeOfAttackByDistance(gameState, position, playerName,weapon.getRange() + weapon.getSplashRadius());
    }

    public static List<Character> findEnemiesInRangeOfAttackByDistance(GameState gameState, Position position, String characterName, int range) {
        Character character = gameState.getCharacter(characterName);
        if (character == null) {
            return null;
        }

        List<Character> enemiesInRange = new ArrayList<>();
        for (Character other : findEnemiesByDistance(gameState, position, characterName)) {
            int distance = position.manhattanDistance(other.getPosition());
            if (distance <= range) {
                enemiesInRange.add(other);
            } else {
                break;
            }
        }
        return enemiesInRange;
    }

    public static List<Character> findAllEnemiesHit(GameState gameState, Position position, String playerName) {
        Player player = gameState.getPlayer(playerName);
        if (player == null) {
            return null;
        }
        Weapon weapon = player.getWeapon();
        if (weapon == null) {
            return new ArrayList<>();
        }

        List<Character> enemies = new ArrayList<>();
        for (Character other : gameState.getCharactersOnBoard(position.getBoardID())) {
            int distance = position.manhattanDistance(other.getPosition());
            if (!other.getName().equals(playerName) && distance <= weapon.getSplashRadius()) {
                enemies.add(other);
            }
        }
        return enemies;
    }

    public static boolean inRangeOfAttack(GameState gameState, Position position, String playerName) {
        List<Character> enemies = findEnemiesByDistance(gameState, position, playerName);
        for (Character enemy : enemies) {
            Integer dist = position.manhattanDistance(enemy.getPosition());
            if (enemy.getWeapon() == null) {
                continue;
            }
            else if (dist <= enemy.getWeapon().getRange() + enemy.getWeapon().getSplashRadius()) {
                return true;
            }
        }
        return false;
    }

    public static Position findClosestPortal(GameState gameState, Position pos) {
        Board board = gameState.getBoard(pos.getBoardID());
        List<Position> portals = board.getPortals();

        Integer minDist = null;
        Position closestPortal = null;
        for (Position portal : portals) {
            Integer dist = portal.manhattanDistance(pos);
            if (minDist == null) {
                minDist = dist;
                closestPortal = portal;
            } else if (dist < minDist) {
                minDist = dist;
                closestPortal = portal;
            }
        }
        if (closestPortal == null) {
            return new Position(-1, -1, "no_portal");
        }
        return closestPortal;
    }

    public static List<Player> leaderBoard(GameState gameState) {
        List<Player> players = new ArrayList<>(gameState.getAllPlayers().values());
        Comparator<Player> compareByExp = Comparator.comparing(Player::getTotalExperience);
        Collections.sort(players, compareByExp.reversed());

        return players;
    }
}
