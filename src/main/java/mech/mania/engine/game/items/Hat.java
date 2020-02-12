package mech.mania.engine.game.items;

public class Hat extends Wearable {
    private HatEffect hatEffect;

    public Hat(StatusModifier stats, HatEffect hatEffect) {
        super(stats);
        this.hatEffect = hatEffect;
    }

    public HatEffect getHatEffect() {
        return hatEffect;
    }
}
