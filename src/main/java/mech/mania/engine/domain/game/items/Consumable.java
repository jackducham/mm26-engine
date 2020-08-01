package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Consumable extends Item {
    protected TempStatusModifier effect;
    private int stacks;

    /**
     * Creates a consumable item with a given maximum stacks (uses) and a temporary status modifier which it will
     * apply to the player on use.
     * @param maxStack the maximum of this item that can be kept in one inventory slot
     * @param effect the effect this consumable will apply on use
     */
    public Consumable(int maxStack, TempStatusModifier effect) {
        super(maxStack);
        this.effect = effect;
        this.stacks = 1;
    }

    /**
     * Creates a Consumable based on a Protocol Buffer with a given maximum number of stacks.
     * @param maxStack the maximum of this item that can be kept in one inventory slot
     * @param consumableProto the protocol buffer to be copied
     */
    public Consumable(int maxStack, ItemProtos.Consumable consumableProto) {
        super(maxStack);
        this.effect = new TempStatusModifier(consumableProto.getEffect());
        this.stacks = consumableProto.getStacks();
    }

    /**
     * Creates a consumable Protocol Buffer based on the instance of Consumable this function is called on.
     * @return a protocol buffer based on the Consumable
     */
    public ItemProtos.Item buildProtoClass() {
        ItemProtos.Consumable.Builder consumableBuilder = ItemProtos.Consumable.newBuilder();
        consumableBuilder.setStacks(stacks);
        consumableBuilder.setEffect(effect.buildProtoClassTemp());

        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setMaxStack(maxStack);
        itemBuilder.setConsumable(consumableBuilder.build());

        return itemBuilder.build();
    }

    /**
     * Getter for the current number of stacks remaining on this item. (the stacks represent a number of the same
     * consumable being stored in a single inventory slot).
     * @return the number of stacks left
     */
    public int getStacks() {
        return stacks;
    }

    /**
     * Setter for the remaining stacks.
     * @param stacks the number the stacks will be set to
     */
    public void setStacks(int stacks) {
        this.stacks = stacks;
    }

    /**
     * Getter for the effect this consumable applies on use.
     * @return the effect
     */
    public TempStatusModifier getEffect() {
        return effect;
    }

    /**
     * Creates a default Consumable for use in testing. NOTE: Each default item provides a particular stat increase;
     * the default consumable provides a 50% bonus to defense and 2 health per turn for 5 turns.
     * This consumable holds up to 5 stacks.
     * @return a default Clothes object
     */
    public static Consumable createDefaultConsumable() {
        TempStatusModifier defaultTempStatusModifier = new TempStatusModifier(0, 0,
        0, 0,
        0, 0,
        0, 0,
        0, 0.5,
        2, 5, 0);
        Consumable defaultConsumable = new Consumable(5, defaultTempStatusModifier);
        return defaultConsumable;
    }
}
