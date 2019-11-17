package mech.mania.engine.game.characters;

import mech.mania.engine.game.board.Position;
import mech.mania.engine.game.items.AttackPattern;

public abstract class Character {
    protected double currentHealth;
    protected int experience;
    protected Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public int getLevel() {
        // TODO: experience formula
        return 0;
    }

    public double getPercentExpToNextLevel(){
        // TODO: experience formula
        return 0.0;
    }

    public int getTotalExperience(){
        return experience;
    }

    /* Stat getter methods */
    static final double baseMaxHealth = 0;
    static final double maxHealthScaling = 0;
    public double getMaxHealth() {
        return baseMaxHealth + getLevel()*maxHealthScaling;
    }

    static final double baseSpeed = 0;
    static final double speedScaling = 0;
    public double getSpeed() {
        return baseSpeed + getLevel()*speedScaling;
    }

    static final double basePhysicalDamage = 0;
    static final double physicalDamageScaling = 0;
    public double getPhysicalDamage() {
        return basePhysicalDamage + getLevel()*physicalDamageScaling;
    }

    static final double baseMagicalDamage = 0;
    static final double magicalDamageScaling = 0;
    public double getMagicalDamage() {
        return baseMagicalDamage + getLevel()*magicalDamageScaling;
    }

    static final double basePhysicalDefense = 0;
    static final double physicalDefenseScaling = 0;
    public double getPhysicalDefense() {
        return basePhysicalDefense + getLevel()*physicalDefenseScaling;
    }

    static final double baseMagicalDefense = 0;
    static final double magicalDefenseScaling = 0;
    public double getMagicalDefense() {
        return baseMagicalDefense + getLevel()*magicalDefenseScaling;
    }

    public abstract AttackPattern getAttackPattern();
}
