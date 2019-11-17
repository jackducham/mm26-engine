package mech.mania.engine.game.items;

public class Hat extends Wearable {
    private HatEffect hatEffect;

    public Hat(double buyPrice, double sellPrice, StatusModifier stats, HatEffect hatEffect) {
        super(buyPrice, sellPrice, stats);
        this.hatEffect = hatEffect;
    }

    public HatEffect getHatEffect() {
        return hatEffect;
    }
}
