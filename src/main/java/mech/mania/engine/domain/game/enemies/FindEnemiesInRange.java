package mech.mania.engine.domain.game.enemies;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;
import mech.mania.engine.domain.game.items.Weapon;

import java.util.*;

public class FindEnemiesInRange {
    public static List<Character> findEnemiesInRange(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);
        Weapon weapon = player.getWeapon();

        List<AbstractMap.SimpleEntry<Double, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            Double distance = player.getPosition().distance(other.getPosition());
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName) && distance <= (double) weapon.getRange()) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Double, Character>(distance, (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            Double distance = player.getPosition().distance(other.getPosition());
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName) && distance <= (double) weapon.getRange()) {
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

}
