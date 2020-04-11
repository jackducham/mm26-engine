package mech.mania.engine.game.items;

public class StatusModifier {
    private int speedChange;
    private int healthChange;
    private int experienceChange;
    private int magicDamageChange;
    private int physicalDamageChange;
    private int magicDefenseChange;
    private int physicalDefenseChange;

    public StatusModifier(int speedChange, int healthChange, int experienceChange, int magicDamageChange,
                          int physicalDamageChange, int magicDefenseChange, int physicalDefenseChange) {
        this.speedChange = speedChange;
        this.healthChange = healthChange;
        this.experienceChange = experienceChange;
        this.magicDamageChange = magicDamageChange;
        this.physicalDamageChange = physicalDamageChange;
        this.magicDefenseChange = magicDefenseChange;
        this.physicalDefenseChange = physicalDefenseChange;
    }

    public StatusModifier(ItemProtos.StatusModifier statusModifierProto) {
        this.speedChange = statusModifierProto.getSpeedChange();
        this.healthChange = statusModifierProto.getHealthChange();
        this.experienceChange = statusModifierProto.getExperienceChange();
        this.magicDamageChange = statusModifierProto.getMagicDamageChange();
        this.physicalDamageChange = statusModifierProto.getPhysicalDamageChange();
        this.magicDefenseChange = statusModifierProto.getMagicDefenseChange();
        this.physicalDefenseChange = statusModifierProto.getPhysicalDefenseChange();
    }

    public ItemProtos.StatusModifier buildProtoClass() {
        ItemProtos.StatusModifier.Builder statusModifierBuilder = ItemProtos.StatusModifier.newBuilder();

        // TODO: fix int casts once type has been changed
        statusModifierBuilder.setSpeedChange(speedChange);
        statusModifierBuilder.setHealthChange(healthChange);
        statusModifierBuilder.setExperienceChange(experienceChange);
        statusModifierBuilder.setMagicDamageChange(magicDamageChange);
        statusModifierBuilder.setPhysicalDamageChange(physicalDamageChange);
        statusModifierBuilder.setMagicDefenseChange(magicDefenseChange);
        statusModifierBuilder.setPhysicalDefenseChange(physicalDefenseChange);

        return statusModifierBuilder.build();
    }

    public int getSpeedChange() {
        return speedChange;
    }

    public void setSpeedChange(int speedChange) {
        this.speedChange = speedChange;
    }

    public int getHealthChange() {
        return healthChange;
    }

    public void setHealthChange(int healthChange) {
        this.healthChange = healthChange;
    }

    public int getExperienceChange() {
        return experienceChange;
    }

    public void setExperienceChange(int experienceChange) {
        this.experienceChange = experienceChange;
    }

    public int getMagicDamageChange() {
        return magicDamageChange;
    }

    public void setMagicDamageChange(int magicDamageChange) {
        this.magicDamageChange = magicDamageChange;
    }

    public int getPhysicalDamageChange() {
        return physicalDamageChange;
    }

    public void setPhysicalDamageChange(int physicalDamageChange) {
        this.physicalDamageChange = physicalDamageChange;
    }

    public int getMagicDefenseChange() {
        return magicDefenseChange;
    }

    public void setMagicDefenseChange(int magicDefenseChange) {
        this.magicDefenseChange = magicDefenseChange;
    }

    public int getPhysicalDefenseChange() {
        return physicalDefenseChange;
    }

    public void setPhysicalDefenseChange(int physicalDefenseChange) {
        this.physicalDefenseChange = physicalDefenseChange;
    }
}
