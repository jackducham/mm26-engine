package mech.mania.engine.game.items;

import java.util.List;

public class Weapon extends Wearable {
    protected int range = 0;
    protected double physicalDamage = 0;
    protected double magicalDamage = 0;
    protected double[][] baseAttackPattern;
    protected List<WeaponEffect> weaponEffects;

    public Weapon(double buyPrice, double sellPrice, int range, double physicalDamage, double magicalDamage, double[][] baseAttackPattern, List<WeaponEffect> weaponEffects) {
        super(buyPrice, sellPrice);
        this.range = range;
        this.physicalDamage = physicalDamage;
        this.magicalDamage = magicalDamage;
        this.baseAttackPattern = baseAttackPattern;
        this.weaponEffects = weaponEffects;
    }

    public int getRange() {
        return range;
    }

    public double getPhysicalDamage() {
        return physicalDamage;
    }

    public double getMagicalDamage() {
        return magicalDamage;
    }

    public double[][] getAttackPattern() {
        double[][] attackPattern = baseAttackPattern;

        /* Note that we may have to expand the size of attackPattern if
            weapon effects apply outside of it's bounds */
        for(WeaponEffect effect : this.weaponEffects) {
            switch (effect){
                // TODO: Apply weapon effects
            }
        }

        return attackPattern;
    }

    public List<WeaponEffect> getWeaponEffects() {
        return weaponEffects;
    }
}
