package mech.mania.engine.game.items;

public class TempStatusModifier extends StatusModifier {
    int duration = 0;

    public TempStatusModifier(double speedChange, double healthChange, double experienceChange,
                              double magicDamageChange, double physicalDamageChange, double magicDefenseChange,
                              double physicalDefenseChange, double damagePerTurn, int duration) {
        super(speedChange, healthChange, experienceChange, magicDamageChange, physicalDamageChange, magicDefenseChange,
                physicalDefenseChange, damagePerTurn);
        this.duration = duration;
    }
}
