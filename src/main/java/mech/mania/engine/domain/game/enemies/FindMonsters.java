package mech.mania.engine.domain.game.enemies;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.characters.Player;
import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.Monster;

import java.util.*;

public class FindMonsters {
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

        Comparator<Monster> compareByDistance = (Monster m1, Monster m2)
                -> Integer.valueOf(m1.getExperience()).compareTo( Integer.valueOf(m2.getExperience()) );
        Collections.sort(monsters, compareByDistance.reversed());

        return monsters;
    }

}
