package mech.mania.engine.game.items;

public abstract class Item {
    protected double buyPrice;
    protected double sellPrice;
    protected int maxStack;

    public Item(double buyPrice, double sellPrice, int maxStack) {
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.maxStack = maxStack;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }
}
