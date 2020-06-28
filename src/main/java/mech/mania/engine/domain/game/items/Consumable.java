package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Consumable extends Item {
    protected TempStatusModifier effect;
    private int stacks;

    public Consumable(int maxStack, TempStatusModifier effect) {
        super(maxStack);
        this.effect = effect;
        this.stacks = 1;
    }

    public Consumable(int maxStack, ItemProtos.Consumable consumableProto) {
        super(maxStack);
        this.effect = new TempStatusModifier(consumableProto.getEffect());
        this.stacks = consumableProto.getStacks();
    }

    public ItemProtos.Item buildProtoClass() {
        ItemProtos.Consumable.Builder consumableBuilder = ItemProtos.Consumable.newBuilder();
        consumableBuilder.setStacks(stacks);
        consumableBuilder.setEffect(effect.buildProtoClassTemp());

        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setMaxStack(maxStack);
        itemBuilder.setConsumable(consumableBuilder.build());

        return itemBuilder.build();
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
