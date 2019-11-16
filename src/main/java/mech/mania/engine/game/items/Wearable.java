package mech.mania.engine.game.items;

public abstract class Wearable extends Item {
    protected StatusModifier stats;

    public Wearable(double buyPrice, double sellPrice, StatusModifier stats) {
        super(buyPrice, sellPrice, 1); // Wearables can't stack in inventory
        this.stats = stats;
    }

    public StatusModifier getStats() {
        return stats;
    }
}
