package mech.mania.engine.game.items;

public abstract class Wearable extends Item {
    public Wearable(double buyPrice, double sellPrice) {
        super(buyPrice, sellPrice, 1); // Wearables can't stack in inventory
    }
}
