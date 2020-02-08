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
}
