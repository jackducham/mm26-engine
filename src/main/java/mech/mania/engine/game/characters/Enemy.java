package mech.mania.engine.game.characters;

public class Enemy extends Character {
    private int goldReward;
    private double[][] attackPattern;

    public Enemy(int goldReward, double[][] attackPattern) {
        this.goldReward = goldReward;
        this.attackPattern = attackPattern;
    }

    @Override
    public double[][] getAttackPattern() {
        return attackPattern;
    }
}
