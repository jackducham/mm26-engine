package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    private int duration;
    private int damagePerTurn;               // Concept: poison type of damage that effects player over time

    public TempStatusModifier(int speedChange, int healthChange, int experienceChange,
                              int magicDamageChange, int physicalDamageChange, int magicDefenseChange,
                              int physicalDefenseChange, int duration, int damagePerTurn) {
        super(speedChange, healthChange, experienceChange, magicDamageChange, physicalDamageChange, magicDefenseChange,
                physicalDefenseChange);
        this.duration = duration;
        this.damagePerTurn = damagePerTurn;
    }
}
