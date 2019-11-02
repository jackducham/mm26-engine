package mech.mania.engine.game.items;

public class Shoes extends Wearable {
    private int speedModifier = 0;

    public Shoes(double buyPrice, double sellPrice, int speedModifier) {
        super(buyPrice, sellPrice);
        this.speedModifier = speedModifier;
    }
}
