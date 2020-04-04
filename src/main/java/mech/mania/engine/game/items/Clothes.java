package mech.mania.engine.game.items;

public class Clothes extends Wearable {
    public Clothes(StatusModifier stats) {
        super(stats);
    }

    public Clothes(ItemProtos.Clothes clothesProto) {
        super(new StatusModifier(clothesProto.getStats()));
    }
}
