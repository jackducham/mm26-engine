package mech.mania.engine.game.items;

public class Weapon extends Wearable {
    protected int range = 0;
    protected int splashRadius = 0;
    protected TempStatusModifier onHitEffect;

    public Weapon(StatusModifier stats, int range, int splashRadius,
                  TempStatusModifier onHitEffect) {
        super(stats);
        this.range = range;
        this.splashRadius = splashRadius;
        this.onHitEffect = onHitEffect;
    }

    public Weapon(ItemProtos.Weapon weaponProto) {
        super(new StatusModifier(weaponProto.getStats()));
        this.range = weaponProto.getRange();
        this.splashRadius = weaponProto.getSplashRadius();
        this.onHitEffect = new TempStatusModifier(weaponProto.getOnHitEffect());
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
