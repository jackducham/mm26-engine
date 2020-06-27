package mech.mania.engine.domain.game.items;

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

    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Weapon protoWeaponClass = buildProtoClassWeapon();
        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setWeapon(protoWeaponClass);

        return itemBuilder.build();
    }

    public ItemProtos.Weapon buildProtoClassWeapon() {
        ItemProtos.Weapon.Builder weaponBuilder = ItemProtos.Weapon.newBuilder();
        weaponBuilder.setStats(stats.buildProtoClass());
        weaponBuilder.setRange(range);
        weaponBuilder.setSplashRadius(splashRadius);
        weaponBuilder.setOnHitEffect(onHitEffect.buildProtoClassTemp());

        return weaponBuilder.build();
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
