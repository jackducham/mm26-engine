package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public abstract class Item {
    protected int maxStack;
    protected String name;
    private int turnsTilDeletion = 30;

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

    public int getTurnsTilDeletion() {
        return turnsTilDeletion;
    }

    public void setTurnsTilDeletion(int turnsTilDeletion) {
        this.turnsTilDeletion = turnsTilDeletion;
    }
}
