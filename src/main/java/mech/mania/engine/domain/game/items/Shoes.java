package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Shoes extends Wearable {
    public Shoes(StatusModifier stats) {
        super(stats);
    }

    public Shoes(ItemProtos.Shoes shoesProto) {
        super(new StatusModifier(shoesProto.getStats()));
    }

    public ItemProtos.Shoes buildProtoClassShoes() {
        ItemProtos.Shoes.Builder shoesBuilder = ItemProtos.Shoes.newBuilder();
        shoesBuilder.setStats(stats.buildProtoClass());

        return shoesBuilder.build();
    }

    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Shoes shoesProtoClass = buildProtoClassShoes();

        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setShoes(shoesProtoClass);

        return itemBuilder.build();
    }
}
