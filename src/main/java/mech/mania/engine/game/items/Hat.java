package mech.mania.engine.game.items;

public class Hat extends Wearable {
    private HatEffect hatEffect;

    public Hat(StatusModifier stats, HatEffect hatEffect) {
        super(stats);
        this.hatEffect = hatEffect;
    }

    public Hat(ItemProtos.Hat hatProto) {
        super(new StatusModifier(hatProto.getStats()));
        // TODO: copy HatEffect
    }

    public HatEffect getHatEffect() {
        return hatEffect;
    }
}
