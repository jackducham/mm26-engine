package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    private int duration;
    private double damagePerTurn;               // Concept: poison type of damage that effects player over time

    public TempStatusModifier(double speedChange, double healthChange, double experienceChange,
                              double magicDamageChange, double physicalDamageChange, double magicDefenseChange,
                              double physicalDefenseChange, int duration, double damagePerTurn) {
        super(speedChange, healthChange, experienceChange, magicDamageChange, physicalDamageChange, magicDefenseChange,
                physicalDefenseChange);
        this.duration = duration;
        this.damagePerTurn = damagePerTurn;
    }

    public TempStatusModifier(ItemProtos.TempStatusModifier tempStatusModifierProto) {
        super(
                tempStatusModifierProto.getStats().getSpeedChange(),
                tempStatusModifierProto.getStats().getHealthChange(),
                tempStatusModifierProto.getStats().getExperienceChange(),
                tempStatusModifierProto.getStats().getMagicDamageChange(),
                tempStatusModifierProto.getStats().getPhysicalDamageChange(),
                tempStatusModifierProto.getStats().getMagicDefenseChange(),
                tempStatusModifierProto.getStats().getPhysicalDefenseChange()
        );
        this.duration = tempStatusModifierProto.getDuration();
        this.damagePerTurn = tempStatusModifierProto.getDamagePerTurn();
    }
}
