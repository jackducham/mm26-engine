package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Shoes extends Wearable {
    /**
     * Creates a Shoes object with the given stats.
     * @param stats the stats the Shoes will be created with
     */
    public Shoes(StatusModifier stats) {
        super(stats);
    }

    /**
     * Creates a Shoes object based on a Protocol Buffer.
     * @param shoesProto the protocol buffer to be copied
     */
    public Shoes(ItemProtos.Shoes shoesProto) {
        super(new StatusModifier(shoesProto.getStats()));
    }

    /**
     * Creates a shoes Protocol Buffer based on the Shoes instance this function is called on.
     * @return a shoes protocol buffer representing the Shoes object.
     */
    public ItemProtos.Shoes buildProtoClassShoes() {
        ItemProtos.Shoes.Builder shoesBuilder = ItemProtos.Shoes.newBuilder();
        shoesBuilder.setStats(stats.buildProtoClass());

        return shoesBuilder.build();
    }

    /**
     * Creates an item Protocol Buffer based on the Shoes instance this function is called on.
     * @return an item protocol buffer representing the Shoes object.
     */
    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Shoes shoesProtoClass = buildProtoClassShoes();

        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setShoes(shoesProtoClass);

        return itemBuilder.build();
    }

    /**
     * Creates a default Shoes for use in testing. NOTE: Each default item provides a particular stat increase;
     * the default shoes provides a bonus of 5 to the maximum movement distance.
     * @return a default Shoes object
     */
    public static Shoes createDefaultShoes() {
        StatusModifier defaultStatusModifier = new StatusModifier(5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Shoes defaultShoes = new Shoes(defaultStatusModifier);
        return defaultShoes;
    }
}
