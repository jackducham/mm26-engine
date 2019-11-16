package mech.mania.engine.game.items;

public class Weapon extends Wearable {
    protected int range = 0;
    protected AttackPattern attackPattern;
    protected TempStatusModifier onHitEffect;

    public Weapon(double buyPrice, double sellPrice, StatusModifier stats, int range, AttackPattern attackPattern,
                  TempStatusModifier onHitEffect) {
        super(buyPrice, sellPrice, stats);
        this.range = range;
        this.attackPattern = attackPattern;
        this.onHitEffect = onHitEffect;
    }

    public int getRange() {
        return range;
    }

    public AttackPattern getAttackPattern() {
        return attackPattern;
    }

    public TempStatusModifier getOnHitEffect() {
        return onHitEffect;
    }
}
