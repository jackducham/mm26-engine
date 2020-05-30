package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    private int duration;
    private int flatDamagePerTurn;
    private double percentDamagePerTurn;
    /* damagePerTurn is a flat amount of damage delt to the character each turn,
     * which is still affected by damageChange and percentDamageChange.
     */

    public TempStatusModifier(int flatSpeedChange, double percentSpeedChange, int flatHealthChange, double percentHealthChange,
                              int flatExperienceChange, double percentExperienceChange, int flatDamageChange,
                              double percentDamageChange, int flatDefenseChange,
                              double percentDefenseChange, int flatRegenPerTurn, int duration,
                              int flatDamagePerTurn, double percentDamagePerTurn) {
        super(flatSpeedChange, percentSpeedChange, flatHealthChange, percentHealthChange, flatExperienceChange, percentExperienceChange,
                flatDamageChange, percentDamageChange, flatDefenseChange, percentDefenseChange, flatRegenPerTurn);
        this.duration = duration;
        this.flatDamagePerTurn = flatDamagePerTurn;
        this.percentDamagePerTurn = percentDamagePerTurn;
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
                tempStatusModifierProto.getStats().getFlatDamageChange(),
                tempStatusModifierProto.getStats().getPercentDamageChange(),
                tempStatusModifierProto.getStats().getFlatDefenseChange(),
                tempStatusModifierProto.getStats().getPercentDefenseChange(),
                tempStatusModifierProto.getStats().getFlatRegenPerTurn()
        );
        this.duration = tempStatusModifierProto.getDuration();
        this.flatDamagePerTurn = tempStatusModifierProto.getFlatDamagePerTurn();
        this.percentDamagePerTurn = tempStatusModifierProto.getPercentDamagePerTurn();
    }

    public ItemProtos.TempStatusModifier buildProtoClassTemp() {
        ItemProtos.TempStatusModifier.Builder tempStatusModifierBuilder = ItemProtos.TempStatusModifier.newBuilder();
        tempStatusModifierBuilder.setDuration(duration);

        tempStatusModifierBuilder.setFlatDamagePerTurn(flatDamagePerTurn);
        tempStatusModifierBuilder.setPercentDamagePerTurn(percentDamagePerTurn);

        ItemProtos.StatusModifier statusModifierProtoClass = super.buildProtoClass();

        tempStatusModifierBuilder.setStats(statusModifierProtoClass);
        return tempStatusModifierBuilder.build();
    }

    public int getFlatDamagePerTurn() {
        return flatDamagePerTurn;
    }

    public double getPercentDamagePerTurn() {
        return percentDamagePerTurn;
    }
}
