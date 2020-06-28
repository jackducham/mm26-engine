package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Clothes extends Wearable {
    public Clothes(StatusModifier stats) {
        super(stats);
    }

    public Clothes(ItemProtos.Clothes clothesProto) {
        super(new StatusModifier(clothesProto.getStats()));
    }

    public ItemProtos.Clothes buildProtoClassClothes() {
        ItemProtos.Clothes.Builder clothesBuilder = ItemProtos.Clothes.newBuilder();
        clothesBuilder.setStats(stats.buildProtoClass());

        return clothesBuilder.build();
    }

    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Clothes clothesProtoClass = buildProtoClassClothes();

        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setClothes(clothesProtoClass);

        return itemBuilder.build();
    }
}
