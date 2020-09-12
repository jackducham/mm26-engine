package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public abstract class Item {
    protected int maxStack;
    protected String name;

    public Item(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public abstract ItemProtos.Item buildProtoClassItem();
}
