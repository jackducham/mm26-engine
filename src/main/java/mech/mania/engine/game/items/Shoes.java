package mech.mania.engine.game.items;

public class Shoes extends Wearable {
    public Shoes(StatusModifier stats) {
        super(stats);
    }

    public Shoes(ItemProtos.Shoes shoesProto) {
        super(new StatusModifier(shoesProto.getStats()));
    }
}
