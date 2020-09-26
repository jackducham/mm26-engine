package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Weapon extends Wearable {
    protected int range = 0;
    protected int splashRadius = 0;
    protected int attack;

    protected TempStatusModifier onHitEffect;

    public Weapon(StatusModifier stats, int range, int splashRadius, int attack,
                  TempStatusModifier onHitEffect, String sprite) {
        super(stats, sprite);
        this.range = range;
        this.splashRadius = splashRadius;
        this.onHitEffect = onHitEffect;
        this.attack = attack;
    }

    public Weapon(Weapon other) {
        super(new StatusModifier(other.stats), other.sprite);
        this.range = other.range;
        this.splashRadius = other.splashRadius;
        this.onHitEffect = new TempStatusModifier(other.onHitEffect);
        this.attack = other.attack;
    }

    public Weapon(ItemProtos.Weapon weaponProto) {
        super(new StatusModifier(weaponProto.getStats()), weaponProto.getSprite());
        this.range = weaponProto.getRange();
        this.splashRadius = weaponProto.getSplashRadius();
        this.onHitEffect = new TempStatusModifier(weaponProto.getOnHitEffect());
        this.attack = weaponProto.getAttack();
        this.turnsToDeletion = weaponProto.getTurnsToDeletion();
    }

    /**
     * Creates a Weapon with a range of 1, damage of 4
     * Given to all Players at start of game
     */
    public static Weapon createStarterWeapon() {
        StatusModifier statusModifier = new StatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        TempStatusModifier tempStatusModifier = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Weapon starterWeapon = new Weapon(statusModifier, 1, 0, 4, tempStatusModifier, "mm26_wearables/weapons/swords/1.png");

        return starterWeapon;
    }

    public static Weapon createDefaultWeapon() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0);
        TempStatusModifier defaultTempStatusModifier = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Weapon defaultWeapon = new Weapon(defaultStatusModifier, 1, 0, 1, defaultTempStatusModifier, "");

        return defaultWeapon;
    }

    public static Weapon createStrongerDefaultWeapon() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0);
        TempStatusModifier defaultTempStatusModifier = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 5);
        Weapon defaultWeapon = new Weapon(defaultStatusModifier, 10, 5, 10, defaultTempStatusModifier, "");

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
        weaponBuilder.setAttack(attack);
        weaponBuilder.setSprite(sprite);
        weaponBuilder.setTurnsToDeletion(turnsToDeletion);

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

    public int getAttack() {
        return attack;
    }

    public Item copy(){
        return new Weapon(new StatusModifier(this.stats), this.range, this.splashRadius, this.attack, new TempStatusModifier(onHitEffect), this.sprite);
    }
}
