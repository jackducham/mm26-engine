package mech.mania.engine.game.items;

public class Consumable extends Item {
    protected TempStatusModifier effect;

    public Consumable(int maxStack, TempStatusModifier effect) {
        super(maxStack);
        this.effect = effect;
    }

    public TempStatusModifier getEffect() {
        return effect;
    }
}
