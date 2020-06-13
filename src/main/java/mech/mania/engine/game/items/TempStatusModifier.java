package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    private int duration;
    private int flatMagicDamagePerTurn;
    private int flatPhysicalDamagePerTurn;
    private int flatRegenPerTurn;
    // Concept: poison type of damage that effects player over time

    public TempStatusModifier(int flatSpeedChange, float percentSpeedChange,
                              int flatHealthChange, float percentHealthChange,
                              int flatExperienceChange, float percentExperienceChange,
                              int flatPhysicalDamageChange, float percentPhysicalDamageChange,
                              int flatMagicDamageChange, float percentMagicDamageChange,
                              int flatPhysicalDefenseChange, float percentPhysicalDefenseChange,
                              int flatMagicDefenseChange, float percentMagicDefenseChange,
                              int duration, int flatMagicDamagePerTurn,
                              int flatPhysicalDamagePerTurn, int flatRegenPerTurn) {
        super(flatSpeedChange, percentSpeedChange,
                flatHealthChange, percentHealthChange,
                flatExperienceChange, percentExperienceChange,
                flatPhysicalDamageChange, percentPhysicalDamageChange,
                flatMagicDamageChange, percentMagicDamageChange,
                flatPhysicalDefenseChange, percentPhysicalDefenseChange,
                flatMagicDefenseChange, percentMagicDefenseChange);
        this.duration = duration;
        this.flatMagicDamagePerTurn = flatMagicDamagePerTurn;
        this.flatPhysicalDamagePerTurn = flatPhysicalDamagePerTurn;
        this.flatRegenPerTurn = flatRegenPerTurn;
    }

    public TempStatusModifier(ItemProtos.TempStatusModifier tempStatusModifierProto) {
        super(
                tempStatusModifierProto.getStats().getFlatSpeedChange(),
        tempStatusModifierProto.getStats().getPercentSpeedChange(),
        tempStatusModifierProto.getStats().getFlatHealthChange(),
        tempStatusModifierProto.getStats().getPercentHealthChange(),
        tempStatusModifierProto.getStats().getFlatExperienceChange(),
        tempStatusModifierProto.getStats().getPercentExperienceChange(),
        tempStatusModifierProto.getStats().getFlatPhysicalDamageChange(),
        tempStatusModifierProto.getStats().getPercentPhysicalDamageChange(),
        tempStatusModifierProto.getStats().getFlatMagicDamageChange(),
        tempStatusModifierProto.getStats().getPercentMagicDamageChange(),
        tempStatusModifierProto.getStats().getFlatPhysicalDefenseChange(),
        tempStatusModifierProto.getStats().getPercentPhysicalDefenseChange(),
        tempStatusModifierProto.getStats().getFlatMagicDefenseChange(),
        tempStatusModifierProto.getStats().getPercentMagicDefenseChange()
        );
        this.duration = tempStatusModifierProto.getDuration();
        this.flatRegenPerTurn = tempStatusModifierProto.getFlatRegenPerTurn();
        this.flatPhysicalDamagePerTurn = tempStatusModifierProto.getFlatPhysicalDamagePerTurn();
        this.flatMagicDamagePerTurn = tempStatusModifierProto.getFlatMagicalDamagePerTurn();
    }

    public ItemProtos.TempStatusModifier buildProtoClassTemp() {
        ItemProtos.TempStatusModifier.Builder tempStatusModifierBuilder = ItemProtos.TempStatusModifier.newBuilder();
        tempStatusModifierBuilder.setDuration(duration);

        tempStatusModifierBuilder.setFlatPhysicalDamagePerTurn(flatPhysicalDamagePerTurn);
        tempStatusModifierBuilder.setFlatMagicalDamagePerTurn(flatMagicDamagePerTurn);
        tempStatusModifierBuilder.setFlatRegenPerTurn(flatRegenPerTurn);

        ItemProtos.StatusModifier superClass = super.buildProtoClass();

        tempStatusModifierBuilder.setStats(superClass);
        return tempStatusModifierBuilder.build();
    }
}
