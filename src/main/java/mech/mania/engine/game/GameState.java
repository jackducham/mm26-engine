package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.characters.Enemy;
import mech.mania.engine.game.characters.Player;

import java.util.List;

public class GameState {
    private Board board; // TODO: probably change this to a map?
    private List<Player> players;
    private List<Enemy> enemies;
}