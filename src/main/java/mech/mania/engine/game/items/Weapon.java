package mech.mania.engine.game.items;

public class Weapon extends Wearable {
    protected int range = 0;
    protected int splashRadius = 0;
    protected TempStatusModifier onHitEffect;

    public Weapon(double buyPrice, double sellPrice, StatusModifier stats, int range, int splashRadius,
                  TempStatusModifier onHitEffect) {
        super(buyPrice, sellPrice, stats);
        this.range = range;
        this.splashRadius = splashRadius;
        this.onHitEffect = onHitEffect;
    }

    public int getRange() {
        return range;
    }

    public int getSplashRadius() {
        return splashRadius;
    }

    public TempStatusModifier getOnHitEffect() {
        return onHitEffect;
    }
}
