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

    public void updateTurnsLeft() {
        duration--;
    }

    public int getDuration() {
        return duration;
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
