package mech.mania.engine.domain.game;

import mech.mania.engine.domain.game.board.Board;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Weapon;

import java.util.*;

public class utils {

    public static List<Character> findEnemies(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);

        List<AbstractMap.SimpleEntry<Double, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID()) && !otherName.equals(playerName)) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Double, Character>(player.getPosition().distance(other.getPosition()), (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID())) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Double, Character>(player.getPosition().distance(other.getPosition()), (Character) other));
            }
        }

        Comparator<AbstractMap.SimpleEntry<Double, Character>> compareByDistance = (AbstractMap.SimpleEntry<Double, Character> d1, AbstractMap.SimpleEntry<Double, Character> d2)
                -> d1.getKey().compareTo( d2.getKey() );
        Collections.sort(enemiesDist, compareByDistance);
        List<Character> enemies = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Double, Character> dist : enemiesDist) {
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

        List<AbstractMap.SimpleEntry<Double, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            Double distance = player.getPosition().distance(other.getPosition());
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID()) && !otherName.equals(playerName) && distance <= (double) weapon.getRange()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Double, Character>(distance, (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            Double distance = player.getPosition().distance(other.getPosition());
            if (other.getPosition().getBoardID().equals(player.getPosition().getBoardID()) && !otherName.equals(playerName) && distance <= (double) weapon.getRange()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Double, Character>(distance, (Character) other));
            }
        }

        Comparator<AbstractMap.SimpleEntry<Double, Character>> compareByDistance = (AbstractMap.SimpleEntry<Double, Character> d1, AbstractMap.SimpleEntry<Double, Character> d2)
                -> d1.getKey().compareTo( d2.getKey() );
        Collections.sort(enemiesDist, compareByDistance);
        List<Character> enemies = new ArrayList<>();
        for (AbstractMap.SimpleEntry<Double, Character> dist : enemiesDist) {
            enemies.add(dist.getValue());
        }
        return enemies;
    }

    public static List<Character> findAllEnemiesHit(GameState gameState, String playerName, Position targetSpot) {
        Player player = gameState.getPlayer(playerName);
        Weapon weapon = player.getWeapon();

        List<Character> enemies = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            Double distance = targetSpot.distance(other.getPosition());
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName) && distance <= (double) weapon.getRange()) {
                enemies.add(other);
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            Double distance = targetSpot.distance(other.getPosition());
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName) && distance <= (double) weapon.getRange()) {
                enemies.add(other);
            }
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
            double dist = player.getPosition().distance(enemy.getPosition());
            if (dist <= enemy.getWeapon().getRange()) {
                return true;
            }
        }
        return false;
    }

    public static Position findClosestPortal(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);
        Board board = gameState.getBoard(player.getPosition().getBoardID());
        List<Position> portals = board.getPortals();

        Double minDist = null;
        Position closestPortal = null;
        for (Position portal : portals) {
            Double dist = portal.distance(player.getPosition());
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
}
