package mech.mania.engine.game.items;

public class Consumable extends Item {
    protected TempStatusModifier effect;

    public Consumable(double buyPrice, double sellPrice, int maxStack, TempStatusModifier effect) {
        super(buyPrice, sellPrice, maxStack);
        this.effect = effect;
    }

    public TempStatusModifier getEffect() {
        return effect;
    }
}
