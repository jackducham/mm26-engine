package mech.mania.engine.game.items;

public class StatusModifier {
    private double speedChange;
    private double healthChange;
    private double experienceChange;
    private double magicDamageChange;
    private double physicalDamageChange;
    private double magicDefenseChange;
    private double physicalDefenseChange;
    private double damagePerTurn;               // Concept: poison type of damage that effects player over time

    public StatusModifier(double speedChange, double healthChange, double experienceChange, double magicDamageChange,
                          double physicalDamageChange, double magicDefenseChange, double physicalDefenseChange,
                          double damagePerTurn) {
        this.speedChange = speedChange;
        this.healthChange = healthChange;
        this.experienceChange = experienceChange;
        this.magicDamageChange = magicDamageChange;
        this.physicalDamageChange = physicalDamageChange;
        this.magicDefenseChange = magicDefenseChange;
        this.physicalDefenseChange = physicalDefenseChange;
        this.damagePerTurn = damagePerTurn;
    }

    public double getSpeedChange() {
        return speedChange;
    }

    public void setSpeedChange(double speedChange) {
        this.speedChange = speedChange;
    }

    public double getHealthChange() {
        return healthChange;
    }

    public void setHealthChange(double healthChange) {
        this.healthChange = healthChange;
    }

    public double getExperienceChange() {
        return experienceChange;
    }

    public void setExperienceChange(double experienceChange) {
        this.experienceChange = experienceChange;
    }

    public double getMagicDamageChange() {
        return magicDamageChange;
    }

    public void setMagicDamageChange(double magicDamageChange) {
        this.magicDamageChange = magicDamageChange;
    }

    public double getPhysicalDamageChange() {
        return physicalDamageChange;
    }

    public void setPhysicalDamageChange(double physicalDamageChange) {
        this.physicalDamageChange = physicalDamageChange;
    }

    public double getMagicDefenseChange() {
        return magicDefenseChange;
    }

    public void setMagicDefenseChange(double magicDefenseChange) {
        this.magicDefenseChange = magicDefenseChange;
    }

    public double getPhysicalDefenseChange() {
        return physicalDefenseChange;
    }

    public void setPhysicalDefenseChange(double physicalDefenseChange) {
        this.physicalDefenseChange = physicalDefenseChange;
    }

    public double getDamagePerTurn() {
        return damagePerTurn;
    }

    public void setDamagePerTurn(double damagePerTurn) {
        this.damagePerTurn = damagePerTurn;
    }
}
