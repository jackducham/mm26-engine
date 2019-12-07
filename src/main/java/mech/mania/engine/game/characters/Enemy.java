package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.AttackPattern;

public class Enemy extends Character {
    private int goldReward;
    private AttackPattern attackPattern;

    public Enemy(int goldReward, AttackPattern attackPattern) {
        this.goldReward = goldReward;
        this.attackPattern = attackPattern;
    }

    @Override
    public AttackPattern getAttackPattern() {
        return attackPattern;
    }
}
