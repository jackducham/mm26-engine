package mech.mania.engine.game;

import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.characters.Enemy;
import mech.mania.engine.game.characters.Player;

import java.util.List;
import java.util.Map;

public class GameState {
    private Board pvpBoard;
    private Map<Player, Board> playerBoardMap;
}