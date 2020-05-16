package mech.mania.engine.game.items;

public class StatusModifier {
    private int flatSpeedChange;
    private double percentSpeedChange;
    /* speedChange is a flat bonus to the number of spaces that can be traversed in a single move action.
     * percentSpeedChange is a percentage speed modifier:
     * for example, if you can move 10 spaces (base of 5 + a speedChange of 5),
     * a percentSpeedChange of 0.1 makes that 11 i.e. (5 + 5) * (1 + 0.1).
     */
    private int flatHealthChange;
    private double percentHealthChange;
    /* healthChange is a flat bonus to the maximum hit points of a character.
     * percentHealthChange is a percentage max HP modifier:
     * for example, if you can have 100 HP (base of 75 + a healthChange of 25),
     * a percentHealthChange of 0.2 makes that 120 i.e. (75 + 25) * (1 + 0.2).
     */
    private int flatExperienceChange;
    private double percentExperienceChange;
    /* experienceChange is a flat bonus to the experience gained per kill.
     * percentExperienceChange is a percentage EXP modifier:
     * for example, if you gain 525 XP (base kill XP of 500 + an experienceChange of 25),
     * a percentExperienceChange of 0.5 makes that 788 i.e. (500 + 25) * (1 + 0.5).
     */
    private int flatMagicDamageChange;
    private double percentMagicDamageChange;
    /* magicDamageChange is a flat bonus to the magic damage dealt per hit.
     * percentMagicDamageChange is a percentage magic damage modifier:
     * for example, if you deal 30 magic damage (NO BASE MAGIC DAMAGE),
     * a percentMagicDamageChange of 0.15 makes that 35 i.e. (30) * (1 + 0.15).
     */
    private int flatPhysicalDamageChange;
    private double percentPhysicalDamageChange;
    /* physicalDamageChange is a flat bonus to the physical damage dealt per hit.
     * percentPhysicalDamageChange is a percentage physical damage modifier:
     * for example, if you deal 70 physical damage (NO BASE PHYSICAL DAMAGE),
     * a percentPhysicalDamageChange of 0.2 makes that 84 i.e. (70) * (1 + 0.2).
     */
    private int flatMagicDefenseChange;
    private double percentMagicDefenseChange;
    /* magicDefenseChange is a flat modifier to the magic damage taken per hit.
     * percentMagicDefenseChange is a percentage magic damage taken modifier:
     * for example, if you take 50 magic damage (base damage of 75 - a magicDefenseChange of 25),
     * a percentMagicDefenseChange of 0.2 makes that 40 i.e. (75 - 25) * (1 - 0.2).
     */
    private int flatPhysicalDefenseChange;
    private double percentPhysicalDefenseChange;
    /* physicalDefenseChange is a flat modifier to the physical damage taken per hit.
     * percentPhysicalDefenseChange is a percentage physical damage taken modifier:
     * for example, if you take 300 physical damage (base damage of 425 - a physicalDamageChange of 125),
     * a percentPhysicalDamageChange of 0.75 makes that 75 i.e. (425 - 125) * (1 - 0.75).
     */
    private int flatRegenPerTurn;
    // regenPerTurn is a flat amount of HP added to the current HP every turn.


    public StatusModifier(int speedChange, double percentSpeedChange, int healthChange, double percentHealthChange, int experienceChange, double percentExperienceChange, int magicDamageChange, double percentMagicDamageChange, int physicalDamageChange, double percentPhysicalDamageChange, int magicDefenseChange, double percentMagicDefenseChange, int physicalDefenseChange, double percentPhysicalDefenseChange, int regenPerTurn) {
        this.flatSpeedChange = speedChange;
        this.percentSpeedChange = percentSpeedChange;
        this.flatHealthChange = healthChange;
        this.percentHealthChange = percentHealthChange;
        this.flatExperienceChange = experienceChange;
        this.percentExperienceChange = percentExperienceChange;
        this.flatMagicDamageChange = magicDamageChange;
        this.percentMagicDamageChange = percentMagicDamageChange;
        this.flatPhysicalDamageChange = physicalDamageChange;
        this.percentPhysicalDamageChange = percentPhysicalDamageChange;
        this.flatMagicDefenseChange = magicDefenseChange;
        this.percentMagicDefenseChange = percentMagicDefenseChange;
        this.flatPhysicalDefenseChange = physicalDefenseChange;
        this.percentPhysicalDefenseChange = percentPhysicalDefenseChange;
        this.flatRegenPerTurn = regenPerTurn;
    }


    public StatusModifier(ItemProtos.StatusModifier statusModifierProto) {
        this.flatRegenPerTurn = statusModifierProto.getFlatRegenPerTurn();
        this.flatSpeedChange = statusModifierProto.getFlatSpeedChange();
        this.flatHealthChange = statusModifierProto.getFlatHealthChange();
        this.flatExperienceChange = statusModifierProto.getFlatExperienceChange();

        this.flatMagicDamageChange = statusModifierProto.getFlatMagicDamageChange();
        this.flatPhysicalDamageChange = statusModifierProto.getFlatPhysicalDamageChange();
        this.flatMagicDefenseChange = statusModifierProto.getFlatMagicDefenseChange();
        this.flatPhysicalDefenseChange = statusModifierProto.getFlatPhysicalDefenseChange();

        this.percentSpeedChange = statusModifierProto.getPercentSpeedChange();
        this.percentHealthChange = statusModifierProto.getPercentHealthChange();
        this.percentExperienceChange = statusModifierProto.getPercentExperienceChange();

        this.percentMagicDamageChange = statusModifierProto.getPercentMagicDamageChange();
        this.percentPhysicalDamageChange = statusModifierProto.getPercentPhysicalDamageChange();
        this.percentMagicDefenseChange = statusModifierProto.getPercentMagicDefenseChange();
        this.flatPhysicalDefenseChange = statusModifierProto.getFlatPhysicalDefenseChange();
    }

    public ItemProtos.StatusModifier buildProtoClass() {
        ItemProtos.StatusModifier.Builder statusModifierBuilder = ItemProtos.StatusModifier.newBuilder();

        statusModifierBuilder.setFlatSpeedChange(flatSpeedChange);
        statusModifierBuilder.setFlatHealthChange(flatHealthChange);
        statusModifierBuilder.setFlatExperienceChange(flatExperienceChange);
        statusModifierBuilder.setFlatMagicDamageChange(flatMagicDamageChange);
        statusModifierBuilder.setFlatPhysicalDamageChange(flatPhysicalDamageChange);
        statusModifierBuilder.setFlatMagicDefenseChange(flatMagicDefenseChange);
        statusModifierBuilder.setFlatPhysicalDefenseChange(flatPhysicalDefenseChange);

        return statusModifierBuilder.build();
    }

    public int getFlatSpeedChange() {
        return flatSpeedChange;
    }

    public void setFlatSpeedChange(int flatSpeedChange) {
        this.flatSpeedChange = flatSpeedChange;
    }

    public double getPercentSpeedChange() {
        return percentSpeedChange;
    }

    public void setPercentSpeedChange(double percentSpeedChange) {
        this.percentSpeedChange = percentSpeedChange;
    }

    public int getFlatHealthChange() {
        return flatHealthChange;
    }

    public void setFlatHealthChange(int flatHealthChange) {
        this.flatHealthChange = flatHealthChange;
    }

    public double getPercentHealthChange() {
        return percentHealthChange;
    }

    public void setPercentHealthChange(double percentHealthChange) {
        this.percentHealthChange = percentHealthChange;
    }

    public int getFlatExperienceChange() {
        return flatExperienceChange;
    }

    public void setFlatExperienceChange(int flatExperienceChange) {
        this.flatExperienceChange = flatExperienceChange;
    }

    public double getPercentExperienceChange() {
        return percentExperienceChange;
    }

    public void setPercentExperienceChange(double percentExperienceChange) {
        this.percentExperienceChange = percentExperienceChange;
    }

    public int getFlatMagicDamageChange() {
        return flatMagicDamageChange;
    }

    public void setFlatMagicDamageChange(int flatMagicDamageChange) {
        this.flatMagicDamageChange = flatMagicDamageChange;
    }

    public double getPercentMagicDamageChange() {
        return percentMagicDamageChange;
    }

    public void setPercentMagicDamageChange(double percentMagicDamageChange) {
        this.percentMagicDamageChange = percentMagicDamageChange;
    }

    public int getFlatPhysicalDamageChange() {
        return flatPhysicalDamageChange;
    }

    public void setFlatPhysicalDamageChange(int flatPhysicalDamageChange) {
        this.flatPhysicalDamageChange = flatPhysicalDamageChange;
    }

    public double getPercentPhysicalDamageChange() {
        return percentPhysicalDamageChange;
    }

    public void setPercentPhysicalDamageChange(double percentPhysicalDamageChange) {
        this.percentPhysicalDamageChange = percentPhysicalDamageChange;
    }

    public int getFlatMagicDefenseChange() {
        return flatMagicDefenseChange;
    }

    public void setFlatMagicDefenseChange(int flatMagicDefenseChange) {
        this.flatMagicDefenseChange = flatMagicDefenseChange;
    }

    public double getPercentMagicDefenseChange() {
        return percentMagicDefenseChange;
    }

    public void setPercentMagicDefenseChange(double percentMagicDefenseChange) {
        this.percentMagicDefenseChange = percentMagicDefenseChange;
    }

    public int getFlatPhysicalDefenseChange() {
        return flatPhysicalDefenseChange;
    }

    public void setFlatPhysicalDefenseChange(int flatPhysicalDefenseChange) {
        this.flatPhysicalDefenseChange = flatPhysicalDefenseChange;
    }

    public double getPercentPhysicalDefenseChange() {
        return percentPhysicalDefenseChange;
    }

    public void setPercentPhysicalDefenseChange(double percentPhysicalDefenseChange) {
        this.percentPhysicalDefenseChange = percentPhysicalDefenseChange;
    }

    public int getFlatRegenPerTurn() {
        return flatRegenPerTurn;
    }

    public void setFlatRegenPerTurn(int flatRegenPerTurn) {
        this.flatRegenPerTurn = flatRegenPerTurn;
    }
}
