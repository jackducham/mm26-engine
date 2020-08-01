package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class TempStatusModifier extends StatusModifier {
    private int turnsLeft;
    private int damagePerTurn; // flat amount of damage dealt to the character each turn,


    public TempStatusModifier(int flatSpeedChange, double percentSpeedChange,
                              int flatHealthChange, double percentHealthChange,
                              int flatExperienceChange, double percentExperienceChange,
                              int flatAttackChange, double percentAttackChange,
                              int flatDefenseChange, double percentDefenseChange,
                              int flatRegenPerTurn, int duration, int damagePerTurn) {
        super(flatSpeedChange, percentSpeedChange, flatHealthChange, percentHealthChange, flatExperienceChange, percentExperienceChange,
                flatAttackChange, percentAttackChange, flatDefenseChange, percentDefenseChange, flatRegenPerTurn);
        this.turnsLeft = duration;
        this.damagePerTurn = damagePerTurn;
    }

    // @TODO: Update ItemProtos
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
        this.turnsLeft = tempStatusModifierProto.getTurnsLeft();
        this.damagePerTurn = tempStatusModifierProto.getFlatDamagePerTurn();
    }

    public ItemProtos.TempStatusModifier buildProtoClassTemp() {
        ItemProtos.TempStatusModifier.Builder tempStatusModifierBuilder = ItemProtos.TempStatusModifier.newBuilder();
        tempStatusModifierBuilder.setTurnsLeft(turnsLeft);
        tempStatusModifierBuilder.setFlatDamagePerTurn(damagePerTurn);

        ItemProtos.StatusModifier statusModifierProtoClass = super.buildProtoClass();

        tempStatusModifierBuilder.setStats(statusModifierProtoClass);
        return tempStatusModifierBuilder.build();
    }

    public void updateTurnsLeft() {
        turnsLeft--;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public int getDamagePerTurn() {
        return damagePerTurn;
    }
}
