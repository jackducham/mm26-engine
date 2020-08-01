package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

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
    private int flatAttackChange;
    private double percentAttackChange;
    /* attackChange is a flat bonus to the attack dealt per hit.
     * percentAttackChange is a percentage damage modifier:
     * for example, if you deal 70 damage (NO BASE DAMAGE),
     * a percentAttackChange of 0.2 makes that 84 i.e. (70) * (1 + 0.2).
     */
    private int flatDefenseChange;
    private double percentDefenseChange;
    /* defenseChange is a flat modifier to the defense per hit.
     * percentDefenseChange is a percentage damage taken modifier:
     * for example, if you take 50 damage (base damage of 75 - a DefenseChange of 25),
     * a percentDefenseChange of 0.2 makes that 40 i.e. (75 - 25) * (1 - 0.2).
     */
    private int flatRegenPerTurn;
    // regenPerTurn is a flat amount of HP added to the current HP every turn.


    public StatusModifier(int speedChange, double percentSpeedChange, int healthChange, double percentHealthChange,
                          int experienceChange, double percentExperienceChange, int attackChange, double percentAttackChange,
                          int defenseChange, double percentDefenseChange, int regenPerTurn) {
        this.flatSpeedChange = speedChange;
        this.percentSpeedChange = percentSpeedChange;
        this.flatHealthChange = healthChange;
        this.percentHealthChange = percentHealthChange;
        this.flatExperienceChange = experienceChange;
        this.percentExperienceChange = percentExperienceChange;
        this.flatAttackChange = attackChange;
        this.percentAttackChange = percentAttackChange;
        this.flatDefenseChange = defenseChange;
        this.percentDefenseChange = percentDefenseChange;
        this.flatRegenPerTurn = regenPerTurn;
    }

    public StatusModifier(ItemProtos.StatusModifier statusModifierProto) {
        this.flatRegenPerTurn = statusModifierProto.getFlatRegenPerTurn();
        this.flatSpeedChange = statusModifierProto.getFlatSpeedChange();
        this.flatHealthChange = statusModifierProto.getFlatHealthChange();
        this.flatExperienceChange = statusModifierProto.getFlatExperienceChange();

        this.flatAttackChange = statusModifierProto.getFlatDamageChange();
        this.flatDefenseChange = statusModifierProto.getFlatDefenseChange();

        this.percentSpeedChange = statusModifierProto.getPercentSpeedChange();
        this.percentHealthChange = statusModifierProto.getPercentHealthChange();
        this.percentExperienceChange = statusModifierProto.getPercentExperienceChange();

        this.percentAttackChange = statusModifierProto.getPercentDamageChange();
        this.percentDefenseChange = statusModifierProto.getPercentDefenseChange();
    }

    public ItemProtos.StatusModifier buildProtoClass() {
        ItemProtos.StatusModifier.Builder statusModifierBuilder = ItemProtos.StatusModifier.newBuilder();

        statusModifierBuilder.setFlatSpeedChange(flatSpeedChange);
        statusModifierBuilder.setPercentSpeedChange(percentSpeedChange);
        statusModifierBuilder.setFlatHealthChange(flatHealthChange);
        statusModifierBuilder.setPercentHealthChange(percentHealthChange);
        statusModifierBuilder.setFlatExperienceChange(flatExperienceChange);
        statusModifierBuilder.setPercentExperienceChange(percentExperienceChange);
        statusModifierBuilder.setFlatDamageChange(flatAttackChange);
        statusModifierBuilder.setPercentDamageChange(percentAttackChange);
        statusModifierBuilder.setFlatDefenseChange(flatDefenseChange);
        statusModifierBuilder.setPercentDefenseChange(percentDefenseChange);

        return statusModifierBuilder.build();
    }

    public void resetStatusModifier() {
        this.flatSpeedChange = 0;
        this.percentSpeedChange = 0;
        this.flatHealthChange = 0;
        this.percentHealthChange = 0;
        this.flatExperienceChange = 0;
        this.percentExperienceChange = 0;
        this.flatAttackChange = 0;
        this.percentAttackChange = 0;
        this.flatDefenseChange = 0;
        this.percentDefenseChange = 0;
        this.flatRegenPerTurn = 0;
    }

    public int getFlatSpeedChange() {
        return flatSpeedChange;
    }

    public void setFlatSpeedChange(int flatSpeedChange) {
        this.flatSpeedChange = flatSpeedChange;
    }

    public void addFlatSpeedChange(int flatSpeedChange) {
        this.flatSpeedChange += flatSpeedChange;
    }

    public double getPercentSpeedChange() {
        return percentSpeedChange;
    }

    public void setPercentSpeedChange(double percentSpeedChange) {
        this.percentSpeedChange = percentSpeedChange;
    }

    public void addPercentSpeedChange(double percentSpeedChange) {
        this.percentSpeedChange += percentSpeedChange;
    }

    public int getFlatHealthChange() {
        return flatHealthChange;
    }

    public void setFlatHealthChange(int flatHealthChange) {
        this.flatHealthChange = flatHealthChange;
    }

    public void addFlatHealthChange(int flatHealthChange) {
        this.flatHealthChange += flatHealthChange;
    }

    public double getPercentHealthChange() {
        return percentHealthChange;
    }

    public void setPercentHealthChange(double percentHealthChange) {
        this.percentHealthChange = percentHealthChange;
    }

    public void addPercentHealthChange(double percentHealthChange) {
        this.percentHealthChange += percentHealthChange;
    }

    public int getFlatExperienceChange() {
        return flatExperienceChange;
    }

    public void setFlatExperienceChange(int flatExperienceChange) {
        this.flatExperienceChange = flatExperienceChange;
    }

    public void addFlatExperienceChange(int flatExperienceChange) {
        this.flatExperienceChange += flatExperienceChange;
    }

    public double getPercentExperienceChange() {
        return percentExperienceChange;
    }

    public void setPercentExperienceChange(double percentExperienceChange) {
        this.percentExperienceChange = percentExperienceChange;
    }

    public void addPercentExperienceChange(double percentExperienceChange) {
        this.percentExperienceChange += percentExperienceChange;
    }

    public int getFlatAttackChange() {
        return flatAttackChange;
    }

    public void setFlatAttackChange(int flatAttackChange) {
        this.flatAttackChange = flatAttackChange;
    }

    public void addFlatAttackChange(int flatAttackChange) {
        this.flatAttackChange += flatAttackChange;
    }

    public double getPercentAttackChange() {
        return percentAttackChange;
    }

    public void setPercentAttackChange(double percentAttackChange) {
        this.percentAttackChange = percentAttackChange;
    }

    public void addPercentAttackChange(double percentAttackChange) {
        this.percentAttackChange += percentAttackChange;
    }

    public int getFlatDefenseChange() {
        return flatDefenseChange;
    }

    public void setFlatDefenseChange(int flatDefenseChange) {
        this.flatDefenseChange = flatDefenseChange;
    }

    public void addFlatDefenseChange(int flatDefenseChange) {
        this.flatDefenseChange += flatDefenseChange;
    }

    public double getPercentDefenseChange() {
        return percentDefenseChange;
    }

    public void setPercentDefenseChange(double percentDefenseChange) {
        this.percentDefenseChange = percentDefenseChange;
    }

    public void addPercentDefenseChange(double percentDefenseChange) {
        this.percentDefenseChange += percentDefenseChange;
    }

    public int getFlatRegenPerTurn() {
        return flatRegenPerTurn;
    }

    public void setFlatRegenPerTurn(int flatRegenPerTurn) {
        this.flatRegenPerTurn = flatRegenPerTurn;
    }

    public void addFlatRegenPerTurn(int flatRegenPerTurn) {
        this.flatRegenPerTurn += flatRegenPerTurn;
    }

}
