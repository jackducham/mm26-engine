package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    private int duration;
    private int magicDamagePerTurn;
    private int physicalDamagePerTurn;
    /* damagePerTurn is a flat amount of damage delt to the character each turn,
     * which is still affected by damageChange and percentDamageChange.
     * (either physical [for bleeding] or magic [for poison], whichever set applies)
     */

    public TempStatusModifier(int speedChange, double percentSpeedChange, int healthChange, double percentHealthChange,
                              int experienceChange, double percentExperienceChange, int magicDamageChange,
                              double percentMagicDamageChange, int physicalDamageChange,
                              double percentPhysicalDamageChange, int magicDefenseChange,
                              double percentMagicDefenseChange, int physicalDefenseChange,
                              double percentPhysicalDefenseChange, int regenPerTurn, int duration, int magicDamagePerTurn, int physicalDamagePerTurn) {
        super(speedChange, percentSpeedChange, healthChange, percentHealthChange, experienceChange, percentExperienceChange,
                magicDamageChange, percentMagicDamageChange, physicalDamageChange, percentPhysicalDamageChange,
                magicDefenseChange, percentMagicDefenseChange, physicalDefenseChange, percentPhysicalDefenseChange, regenPerTurn);
        this.duration = duration;
        this.magicDamagePerTurn = magicDamagePerTurn;
        this.physicalDamagePerTurn = physicalDamagePerTurn;
    }

    public void updateTurnsLeft() {
        duration--;
    }

    public int getDuration() {
        return duration;
    }

    public TempStatusModifier(ItemProtos.TempStatusModifier tempStatusModifierProto) {
        super(
                tempStatusModifierProto.getStats().getSpeedChange(),
                tempStatusModifierProto.getStats().getPercentSpeedChange(),
                tempStatusModifierProto.getStats().getHealthChange(),
                tempStatusModifierProto.getStats().getPercentHealthChange(),
                tempStatusModifierProto.getStats().getExperienceChange(),
                tempStatusModifierProto.getStats().getPercentExperienceChange(),
                tempStatusModifierProto.getStats().getMagicDamageChange(),
                tempStatusModifierProto.getStats().getPercentMagicDamageChange(),
                tempStatusModifierProto.getStats().getPhysicalDamageChange(),
                tempStatusModifierProto.getStats().getPercentPhysicalDamageChange(),
                tempStatusModifierProto.getStats().getMagicDefenseChange(),
                tempStatusModifierProto.getStats().getPercentMagicDefenseChange(),
                tempStatusModifierProto.getStats().getPhysicalDefenseChange()
                tempStatusModifierProto.getStats().getPercentPhysicalDefenseChange(),
                tempStatusModifierProto.getStats().getRegenPerTurn()
        );
        this.duration = tempStatusModifierProto.getDuration();
        this.magicDamagePerTurn = tempStatusModifierProto.getMagicDamagePerTurn();
        this.physicalDamagePerTurn = tempStatusModifierProto.getPhysicalDamagePerTurn();
    }

    public ItemProtos.TempStatusModifier buildProtoClassTemp() {
        ItemProtos.TempStatusModifier.Builder tempStatusModifierBuilder = ItemProtos.TempStatusModifier.newBuilder();
        tempStatusModifierBuilder.setDuration(duration);

        // TODO: remove cast once type is fixed
        tempStatusModifierBuilder.setDamagePerTurn((int) damagePerTurn);

        ItemProtos.StatusModifier statusModifierProtoClass = super.buildProtoClass();

        tempStatusModifierBuilder.setStats(statusModifierProtoClass);
        return tempStatusModifierBuilder.build();
    }
}
