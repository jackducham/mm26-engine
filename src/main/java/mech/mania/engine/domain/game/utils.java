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

    public static List<Character> findEnemies(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);

        List<AbstractMap.SimpleEntry<Integer, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID()) && !otherName.equals(playerName)) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(player.getPosition().distance(other.getPosition()), (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID())) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(player.getPosition().distance(other.getPosition()), (Character) other));
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

    public static List<Character> findEnemiesInRange(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);
        Weapon weapon = player.getWeapon();
        if (weapon == null) {
            return new ArrayList<>();
        }

        List<AbstractMap.SimpleEntry<Integer, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            int distance = player.getPosition().distance(other.getPosition());
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID()) && !otherName.equals(playerName) && distance <= weapon.getRange() + weapon.getSplashRadius()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(distance, (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            int distance = player.getPosition().distance(other.getPosition());
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID()) && !otherName.equals(playerName) && distance <= weapon.getRange() + weapon.getSplashRadius()) {
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

    public static List<Character> findAllEnemiesHit(GameState gameState, String playerName, Position targetSpot) {
        Player player = gameState.getPlayer(playerName);
        Weapon weapon = player.getWeapon();
        if (weapon == null) {
            return new ArrayList<>();
        }

        List<AbstractMap.SimpleEntry<Integer, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            int distance = targetSpot.distance(other.getPosition());
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName) && distance <= (Integer) weapon.getSplashRadius()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Integer, Character>(distance, (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            Integer distance = targetSpot.distance(other.getPosition());
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName) && distance <= (Integer) weapon.getSplashRadius()) {
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

    public static List<Monster> findMonsters(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);

        List<Monster> monsters = new ArrayList<>();
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID()) {
                monsters.add(other);
            }
        }

        Comparator<Monster> compareByExp = (Monster m1, Monster m2)
                -> Integer.valueOf(m1.getExperience()).compareTo( Integer.valueOf(m2.getExperience()) );
        Collections.sort(monsters, compareByExp.reversed());

        return monsters;
    }

    public static boolean canBeAttacked(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);

        List<Character> enemies = findEnemies(gameState, playerName);
        for (Character enemy : enemies) {
            Integer dist = player.getPosition().distance(enemy.getPosition());
            if (enemy.getWeapon() == null) {
                continue;
            }
            else if (dist <= enemy.getWeapon().getRange() + enemy.getWeapon().getSplashRadius()) {
                return true;
            }
        }
        return false;
    }

    public static Position findClosestPortal(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);
        Board board = gameState.getBoard(player.getPosition().getBoardID());
        List<Position> portals = board.getPortals();

        Integer minDist = null;
        Position closestPortal = null;
        for (Position portal : portals) {
            Integer dist = portal.distance(player.getPosition());
            if (minDist == null) {
                minDist = dist;
                closestPortal = portal;
            } else if (dist < minDist) {
                minDist = dist;
                closestPortal = portal;
            }
        }
        return closestPortal;
    }

    public static List<Player> leaderBoard(GameState gameState) {
        List<Player> players = new ArrayList<>(gameState.getAllPlayers().values());
        Comparator<Player> compareByExp = (Player p1, Player p2)
                -> Integer.valueOf(p1.getExperience()).compareTo( Integer.valueOf(p2.getExperience()) );
        Collections.sort(players, compareByExp.reversed());

        return players;
    }

    public static List<Item> itemsInRange(GameState gameState, String playerName, int range) {
        Position pos = gameState.getPlayer(playerName).getPosition();
        int playerX = pos.getX();
        int playerY = pos.getY();
        Tile[][] grid = gameState.getBoard(pos.getBoardID()).getGrid();
        range = Math.min(range, 2 * gameState.getPlayer(playerName).getSpeed());
        List<Item> res = new ArrayList<>();

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
                        res.addAll(items);
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
}
