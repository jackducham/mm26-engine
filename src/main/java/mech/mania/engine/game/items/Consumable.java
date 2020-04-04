package mech.mania.engine.game.items;

public class Consumable extends Item {
    protected TempStatusModifier effect;
    private int stacks;

    public Consumable(int maxStack, TempStatusModifier effect) {
        super(maxStack);
        this.effect = effect;
        this.stacks = 1;
    }

    public Consumable(int maxStack, ItemProtos.Consumable consumableProto) {
        // TODO: these should be fixed after Sahil's fixes to protos
        super(maxStack);
        this.effect = new TempStatusModifier(consumableProto.getEffect());
        this.stacks = consumableProto.getStacks();
    }

    public int getStacks() {
        return stacks;
    }

    public void setStacks(int stacks) {
        this.stacks = stacks;
    }

    public TempStatusModifier getEffect() {
        return effect;
    }
}
