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

        List<AbstractMap.SimpleEntry<Integer, Character>> enemiesDist = new ArrayList<>();
        for (Player player : gameState.getPlayersOnBoard(position.getBoardID())) {
            if (!player.getName().equals(playerName)) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(position.manhattanDistance(player.getPosition()), (Character) player));
            }
        }
        for (Monster monster : gameState.getMonstersOnBoard(position.getBoardID())) {
            enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(position.manhattanDistance(monster.getPosition()), (Character) monster));
        }

        Comparator<AbstractMap.SimpleEntry<Integer, Character>> compareByDistance = (AbstractMap.SimpleEntry<Integer, Character> d1, AbstractMap.SimpleEntry<Integer, Character> d2)
                -> d1.getKey().compareTo( d2.getKey() );
        Collections.sort(enemiesDist, compareByDistance);
        List<Character> enemies = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Integer, Character> dist : enemiesDist) {
            enemies.add(dist.getValue());
        }
        return enemies;
    }

    public static List<Monster> findMonstersByExp(GameState gameState, Position position) {

        List<Monster> monsters = gameState.getMonstersOnBoard(position.getBoardID());
        Comparator<Monster> compareByExp = (Monster m1, Monster m2)
                -> Integer.valueOf(m1.getTotalExperience()).compareTo( Integer.valueOf(m2.getTotalExperience()) );
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
                            res.add(new AbstractMap.SimpleEntry<Item, Position>(item, new Position(x_pos, y_pos, boardId)));
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

        List<AbstractMap.SimpleEntry<Integer, Character>> enemiesDist = new ArrayList<>();
        for (Player other : gameState.getPlayersOnBoard(position.getBoardID())) {
            int distance = position.manhattanDistance(other.getPosition());
            if (!other.getName().equals(playerName) && distance <= weapon.getRange() + weapon.getSplashRadius()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(distance, (Character) other));
            }
        }
        for (Monster other : gameState.getMonstersOnBoard(position.getBoardID())) {
            int distance = position.manhattanDistance(other.getPosition());
            if (distance <= weapon.getRange() + weapon.getSplashRadius()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(distance, (Character) other));
            }
        }

        Comparator<AbstractMap.SimpleEntry<Integer, Character>> compareByDistance = (AbstractMap.SimpleEntry<Integer, Character> d1, AbstractMap.SimpleEntry<Integer, Character> d2)
                -> d1.getKey().compareTo( d2.getKey() );
        Collections.sort(enemiesDist, compareByDistance);
        List<Character> enemies = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Integer, Character> dist : enemiesDist) {
            enemies.add(dist.getValue());
        }
        return enemies;
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
        for (Player other : gameState.getPlayersOnBoard(position.getBoardID())) {
            int distance = position.manhattanDistance(other.getPosition());
            if (!other.getName().equals(playerName) && distance <= weapon.getSplashRadius()) {
                enemies.add((Character) other);
            }
        }
        for (Monster other : gameState.getMonstersOnBoard(position.getBoardID())) {
            int distance = position.manhattanDistance(other.getPosition());
            if (distance <= weapon.getSplashRadius()) {
                enemies.add((Character) other);
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
        Comparator<Player> compareByExp = (Player p1, Player p2)
                -> Integer.valueOf(p1.getTotalExperience()).compareTo( Integer.valueOf(p2.getTotalExperience()) );
        Collections.sort(players, compareByExp.reversed());

        return players;
    }
}
