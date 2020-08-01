package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Clothes extends Wearable {
    /**
     * Creates a Clothes object with the given stats.
     * @param stats the stats the Clothes will be created with
     */
    public Clothes(StatusModifier stats) {
        super(stats);
    }

    /**
     * Creates a Clothes object based on a Protocol Buffer.
     * @param clothesProto the protocol buffer to be copied
     */
    public Clothes(ItemProtos.Clothes clothesProto) {
        super(new StatusModifier(clothesProto.getStats()));
    }

    /**
     * Creates a clothes Protocol Buffer based on the Clothes instance this function is called on.
     * @return a clothes protocol buffer representing the Clothes object.
     */
    public ItemProtos.Clothes buildProtoClassClothes() {
        ItemProtos.Clothes.Builder clothesBuilder = ItemProtos.Clothes.newBuilder();
        clothesBuilder.setStats(stats.buildProtoClass());

        return clothesBuilder.build();
    }

    /**
     * Creates an item Protocol Buffer based on the Clothes instance this function is called on.
     * @return an item protocol buffer representing the Clothes object.
     */
    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Clothes clothesProtoClass = buildProtoClassClothes();

        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setClothes(clothesProtoClass);

        return itemBuilder.build();
    }

    /**
     * Creates a default Clothes for use in testing. NOTE: Each default item provides a particular stat increase;
     * the default clothes provides a bonus of 8 to the defense stat.
     * @return a default Clothes object
     */
    public static Clothes createDefaultClothes() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0);
        Clothes defaultClothes = new Clothes(defaultStatusModifier);
        return defaultClothes;
    }
}
