package mech.mania.engine.domain.game.items;

public abstract class Wearable extends Item {
    protected StatusModifier stats;

    public Wearable(StatusModifier stats, String sprite) {
        super(1, sprite); // Wearables can't stack in inventory
        this.stats = stats;
    }

    public StatusModifier getStats() {
        return stats;
    }
}
