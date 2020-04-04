package mech.mania.engine.game.items;

public class StatusModifier {
    private int speedChange;
    private double healthChange;
    private double experienceChange;
    private double magicDamageChange;
    private double physicalDamageChange;
    private double magicDefenseChange;
    private double physicalDefenseChange;

    public StatusModifier(int speedChange, double healthChange, double experienceChange, double magicDamageChange,
                          double physicalDamageChange, double magicDefenseChange, double physicalDefenseChange) {
        this.speedChange = speedChange;
        this.healthChange = healthChange;
        this.experienceChange = experienceChange;
        this.magicDamageChange = magicDamageChange;
        this.physicalDamageChange = physicalDamageChange;
        this.magicDefenseChange = magicDefenseChange;
        this.physicalDefenseChange = physicalDefenseChange;
    }

    public int getSpeedChange() {
        return speedChange;
    }

    public void setSpeedChange(int speedChange) {
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
}
