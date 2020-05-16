package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    private int duration;
    private int flatMagicDamagePerTurn;
    private int flatPhysicalDamagePerTurn;
    /* damagePerTurn is a flat amount of damage delt to the character each turn,
     * which is still affected by damageChange and percentDamageChange.
     * (either physical [for bleeding] or magic [for poison], whichever set applies)
     */

    public TempStatusModifier(int flatSpeedChange, double percentSpeedChange, int flatHealthChange, double percentHealthChange,
                              int flatExperienceChange, double percentExperienceChange, int flatMagicDamageChange,
                              double percentMagicDamageChange, int flatPhysicalDamageChange,
                              double percentPhysicalDamageChange, int flatMagicDefenseChange,
                              double percentMagicDefenseChange, int flatPhysicalDefenseChange,
                              double percentPhysicalDefenseChange, int flatRegenPerTurn, int duration, int flatMagicDamagePerTurn, int flatPhysicalDamagePerTurn) {
        super(flatSpeedChange, percentSpeedChange, flatHealthChange, percentHealthChange, flatExperienceChange, percentExperienceChange,
                flatMagicDamageChange, percentMagicDamageChange, flatPhysicalDamageChange, percentPhysicalDamageChange,
                flatMagicDefenseChange, percentMagicDefenseChange, flatPhysicalDefenseChange, percentPhysicalDefenseChange, flatRegenPerTurn);
        this.duration = duration;
        this.flatMagicDamagePerTurn = flatMagicDamagePerTurn;
        this.flatPhysicalDamagePerTurn = flatPhysicalDamagePerTurn;
    }

    public void updateTurnsLeft() {
        duration--;
    }

    public int getDuration() {
        return duration;
    }

    public TempStatusModifier(ItemProtos.TempStatusModifier tempStatusModifierProto) {
        super(
                tempStatusModifierProto.getStats().getFlatSpeedChange(),
                tempStatusModifierProto.getStats().getPercentSpeedChange(),
                tempStatusModifierProto.getStats().getFlatHealthChange(),
                tempStatusModifierProto.getStats().getPercentHealthChange(),
                tempStatusModifierProto.getStats().getFlatExperienceChange(),
                tempStatusModifierProto.getStats().getPercentExperienceChange(),
                tempStatusModifierProto.getStats().getFlatMagicDamageChange(),
                tempStatusModifierProto.getStats().getPercentMagicDamageChange(),
                tempStatusModifierProto.getStats().getFlatPhysicalDamageChange(),
                tempStatusModifierProto.getStats().getPercentPhysicalDamageChange(),
                tempStatusModifierProto.getStats().getFlatMagicDefenseChange(),
                tempStatusModifierProto.getStats().getPercentMagicDefenseChange(),
                tempStatusModifierProto.getStats().getFlatPhysicalDefenseChange(),
                tempStatusModifierProto.getStats().getPercentPhysicalDefenseChange(),
                tempStatusModifierProto.getStats().getFlatRegenPerTurn()
        );
        this.duration = tempStatusModifierProto.getDuration();
        this.flatMagicDamagePerTurn = tempStatusModifierProto.getFlatMagicalDamagePerTurn();
        this.flatPhysicalDamagePerTurn = tempStatusModifierProto.getFlatPhysicalDamagePerTurn();
    }

    public ItemProtos.TempStatusModifier buildProtoClassTemp() {
        ItemProtos.TempStatusModifier.Builder tempStatusModifierBuilder = ItemProtos.TempStatusModifier.newBuilder();
        tempStatusModifierBuilder.setDuration(duration);

        tempStatusModifierBuilder.setFlatMagicalDamagePerTurn(flatMagicDamagePerTurn);
        tempStatusModifierBuilder.setFlatPhysicalDamagePerTurn(flatPhysicalDamagePerTurn);

        ItemProtos.StatusModifier statusModifierProtoClass = super.buildProtoClass();

        tempStatusModifierBuilder.setStats(statusModifierProtoClass);
        return tempStatusModifierBuilder.build();
    }

    public int getFlatMagicDamagePerTurn() {
        return flatMagicDamagePerTurn;
    }

    public int getFlatPhysicalDamagePerTurn() {
        return flatPhysicalDamagePerTurn;
    }
}
