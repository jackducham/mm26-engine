package mech.mania.engine.game.items;

public class Hat extends Wearable {
    private HatEffect hatEffect;

    public Hat(double buyPrice, double sellPrice, HatEffect hatEffect) {
        super(buyPrice, sellPrice);
        this.hatEffect = hatEffect;
    }

    public HatEffect getHatEffect() {
        return hatEffect;
    }
}
