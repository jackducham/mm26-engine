package mech.mania.engine.domain.game.enemies;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;

import java.util.*;

public class FindEnemies {
    public static List<Character> findEnemies(GameState gameState, String playerName) {
        Player player = gameState.getPlayer(playerName);

        List<AbstractMap.SimpleEntry<Double, Character>> enemiesDist = new ArrayList<>();
        for (Map.Entry<String, Player> entry : gameState.getAllPlayers().entrySet()) {
            String otherName = entry.getKey();
            Player other = entry.getValue();
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName)) {
                enemiesDist.add(new AbstractMap.SimpleEntry<Double, Character>(player.getPosition().distance(other.getPosition()), (Character) other));
            }
        }
        for (Map.Entry<String, Monster> entry : gameState.getAllMonsters().entrySet()) {
            String otherName = entry.getKey();
            Monster other = entry.getValue();
            if (other.getPosition().getBoardID() == player.getPosition().getBoardID() && !otherName.equals(playerName)) {
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
}
