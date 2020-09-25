package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public abstract class Item {
    protected int maxStack;
    protected String name;
    protected int turnsToDeletion;

    public static final int ITEM_LIFETIME = 30;

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

    public int getTurnsToDeletion() {
        return turnsToDeletion;
    }

    public void setTurnsToDeletion(int turnsToDeletion) {
        this.turnsToDeletion = turnsToDeletion;
    }

    public void decTurnsToDeletion(){this.turnsToDeletion--;}
}
