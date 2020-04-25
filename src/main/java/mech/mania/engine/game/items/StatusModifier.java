package mech.mania.engine.game.items;

public class StatusModifier {
    private int speedChange;
    private double percentSpeedChange;
    /* speedChange is a flat bonus to the number of spaces that can be traversed in a single move action.
     * percentSpeedChange is a percentage speed modifier:
     * for example, if you can move 10 spaces (base of 5 + a speedChange of 5),
     * a percentSpeedChange of 0.1 makes that 11 i.e. (5 + 5) * (1 + 0.1).
     */
    private int healthChange;
    private double percentHealthChange;
    /* healthChange is a flat bonus to the maximum hit points of a character.
     * percentHealthChange is a percentage max HP modifier:
     * for example, if you can have 100 HP (base of 75 + a healthChange of 25),
     * a percentHealthChange of 0.2 makes that 120 i.e. (75 + 25) * (1 + 0.2).
     */
    private int experienceChange;
    private double percentExperienceChange;
    /* experienceChange is a flat bonus to the experience gained per kill.
     * percentExperienceChange is a percentage EXP modifier:
     * for example, if you gain 525 XP (base kill XP of 500 + an experienceChange of 25),
     * a percentExperienceChange of 0.5 makes that 788 i.e. (500 + 25) * (1 + 0.5).
     */
    private int magicDamageChange;
    private double percentMagicDamageChange;
    /* magicDamageChange is a flat bonus to the magic damage dealt per hit.
     * percentMagicDamageChange is a percentage magic damage modifier:
     * for example, if you deal 30 magic damage (NO BASE MAGIC DAMAGE),
     * a percentMagicDamageChange of 0.15 makes that 35 i.e. (30) * (1 + 0.15).
     */
    private int physicalDamageChange;
    private double percentPhysicalDamageChange;
    /* physicalDamageChange is a flat bonus to the physical damage dealt per hit.
     * percentPhysicalDamageChange is a percentage physical damage modifier:
     * for example, if you deal 70 physical damage (NO BASE PHYSICAL DAMAGE),
     * a percentPhysicalDamageChange of 0.2 makes that 84 i.e. (70) * (1 + 0.2).
     */
    private int magicDefenseChange;
    private double percentMagicDefenseChange;
    /* magicDefenseChange is a flat modifier to the magic damage taken per hit.
     * percentMagicDefenseChange is a percentage magic damage taken modifier:
     * for example, if you take 50 magic damage (base damage of 75 - a magicDefenseChange of 25),
     * a percentMagicDefenseChange of 0.2 makes that 40 i.e. (75 - 25) * (1 - 0.2).
     */
    private int physicalDefenseChange;
    private double percentPhysicalDefenseChange;
    /* physicalDefenseChange is a flat modifier to the physical damage taken per hit.
     * percentPhysicalDefenseChange is a percentage physical damage taken modifier:
     * for example, if you take 300 physical damage (base damage of 425 - a physicalDamageChange of 125),
     * a percentPhysicalDamageChange of 0.75 makes that 75 i.e. (425 - 125) * (1 - 0.75).
     */
    private int regenPerTurn;
    // regenPerTurn is a flat amount of HP added to the current HP every turn.


    public StatusModifier(int speedChange, double percentSpeedChange, int healthChange, double percentHealthChange, int experienceChange, double percentExperienceChange, int magicDamageChange, double percentMagicDamageChange, int physicalDamageChange, double percentPhysicalDamageChange, int magicDefenseChange, double percentMagicDefenseChange, int physicalDefenseChange, double percentPhysicalDefenseChange, int regenPerTurn) {
        this.speedChange = speedChange;
        this.percentSpeedChange = percentSpeedChange;
        this.healthChange = healthChange;
        this.percentHealthChange = percentHealthChange;
        this.experienceChange = experienceChange;
        this.percentExperienceChange = percentExperienceChange;
        this.magicDamageChange = magicDamageChange;
        this.percentMagicDamageChange = percentMagicDamageChange;
        this.physicalDamageChange = physicalDamageChange;
        this.percentPhysicalDamageChange = percentPhysicalDamageChange;
        this.magicDefenseChange = magicDefenseChange;
        this.percentMagicDefenseChange = percentMagicDefenseChange;
        this.physicalDefenseChange = physicalDefenseChange;
        this.percentPhysicalDefenseChange = percentPhysicalDefenseChange;
        this.regenPerTurn = regenPerTurn;
    }

    //TODO: add the percent modifiers to the proto-constructor

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

    public double getPercentSpeedChange() {
        return percentSpeedChange;
    }

    public void setPercentSpeedChange(double percentSpeedChange) {
        this.percentSpeedChange = percentSpeedChange;
    }

    public int getHealthChange() {
        return healthChange;
    }

    public void setHealthChange(int healthChange) {
        this.healthChange = healthChange;
    }

    public double getPercentHealthChange() {
        return percentHealthChange;
    }

    public void setPercentHealthChange(double percentHealthChange) {
        this.percentHealthChange = percentHealthChange;
    }

    public int getExperienceChange() {
        return experienceChange;
    }

    public void setExperienceChange(int experienceChange) {
        this.experienceChange = experienceChange;
    }

    public double getPercentExperienceChange() {
        return percentExperienceChange;
    }

    public void setPercentExperienceChange(double percentExperienceChange) {
        this.percentExperienceChange = percentExperienceChange;
    }

    public int getMagicDamageChange() {
        return magicDamageChange;
    }

    public void setMagicDamageChange(int magicDamageChange) {
        this.magicDamageChange = magicDamageChange;
    }

    public double getPercentMagicDamageChange() {
        return percentMagicDamageChange;
    }

    public void setPercentMagicDamageChange(double percentMagicDamageChange) {
        this.percentMagicDamageChange = percentMagicDamageChange;
    }

    public int getPhysicalDamageChange() {
        return physicalDamageChange;
    }

    public void setPhysicalDamageChange(int physicalDamageChange) {
        this.physicalDamageChange = physicalDamageChange;
    }

    public double getPercentPhysicalDamageChange() {
        return percentPhysicalDamageChange;
    }

    public void setPercentPhysicalDamageChange(double percentPhysicalDamageChange) {
        this.percentPhysicalDamageChange = percentPhysicalDamageChange;
    }

    public int getMagicDefenseChange() {
        return magicDefenseChange;
    }

    public void setMagicDefenseChange(int magicDefenseChange) {
        this.magicDefenseChange = magicDefenseChange;
    }

    public double getPercentMagicDefenseChange() {
        return percentMagicDefenseChange;
    }

    public void setPercentMagicDefenseChange(double percentMagicDefenseChange) {
        this.percentMagicDefenseChange = percentMagicDefenseChange;
    }

    public int getPhysicalDefenseChange() {
        return physicalDefenseChange;
    }

    public void setPhysicalDefenseChange(int physicalDefenseChange) {
        this.physicalDefenseChange = physicalDefenseChange;
    }

    public double getPercentPhysicalDefenseChange() {
        return percentPhysicalDefenseChange;
    }

    public void setPercentPhysicalDefenseChange(double percentPhysicalDefenseChange) {
        this.percentPhysicalDefenseChange = percentPhysicalDefenseChange;
    }

    public int getRegenPerTurn() {
        return regenPerTurn;
    }

    public void setRegenPerTurn(int regenPerTurn) {
        this.regenPerTurn = regenPerTurn;
    }
}
