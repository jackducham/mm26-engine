package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Weapon extends Wearable {
    protected int range = 0;
    protected int splashRadius = 0;
    protected int damage;

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
        // TODO: these will compile after proto import
        this.damage = weaponProto.getDamage();
    }

    public static Weapon createDefaultWeapon() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0);
        TempStatusModifier defaultTempStatusModifier = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Weapon defaultWeapon = new Weapon(defaultStatusModifier, 1, 0, defaultTempStatusModifier);

        return defaultWeapon;
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
        // TODO: these will compile after proto import
        weaponBuilder.setDamage(damage);

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

    public int getDamage() {
        return damage;
    }
}
