package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.Hat;
import mech.mania.engine.game.items.Weapon;

public class Player extends Character {
    private int experience;
    private Weapon weapon;
    private Hat hat;

    @Override
    public double getMaxHealth() {
        double maxHealth = super.getMaxHealth();

        //TODO: Apply item effects

        return maxHealth;
    }

    @Override
    public double getSpeed() {
        double speed =  super.getSpeed();

        //TODO: Apply item effects

        return speed;
    }

    @Override
    public double getPhysicalDamage() {
        double physicalDamage = super.getPhysicalDamage();

        //TODO: Apply item effects

        return physicalDamage;
    }

    @Override
    public double getMagicalDamage() {
        double magicalDamage =  super.getMagicalDamage();

        //TODO: Apply item effects

        return magicalDamage;
    }

    @Override
    public double getMagicalDefense() {
        double magicalDefense =  super.getMagicalDefense();

        //TODO: Apply item effects

        return magicalDefense;
    }

    @Override
    public double getPhysicalDefense() {
        double PhysicalDefense = super.getPhysicalDefense();

        //TODO: Apply item effects

        return PhysicalDefense;
    }

    @Override
    public double[][] getAttackPattern() {
        double[][] attackpattern = this.weapon.getAttackPattern();

        /* Note that we may have to expand the size of attackPattern if
            hat effects apply outside of it's bounds */
        switch(this.hat.getHatEffect()) {
            //TODO: Apply hat effect
        }

        return attackpattern;
    }
}
