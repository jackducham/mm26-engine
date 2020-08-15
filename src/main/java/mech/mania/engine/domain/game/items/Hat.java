package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Hat extends Wearable {
    private HatEffect hatEffect;

    /**
     * Creates a hat with given stats and hat effect.
     * @param stats a StatusModifier storing the stats the Hat will be created with
     * @param hatEffect the effect the Hat will have
     */
    public Hat(StatusModifier stats, HatEffect hatEffect) {
        super(stats);
        this.hatEffect = hatEffect;
    }

    /**
     * Creates a Hat object based on a given Protocol Buffer.
     * @param hatProto the protocol buffer to be copied
     */
    public Hat(ItemProtos.Hat hatProto) {
        super(new StatusModifier(hatProto.getStats()));
        // TODO: copy HatEffect
    }

    /**
     * Creates a hat Protocol Buffer based on the Hat instance this function is called on.
     * @return a hat protocol buffer representing the Hat
     */
    public ItemProtos.Hat buildProtoClassHat() {
        ItemProtos.Hat.Builder hatBuilder = ItemProtos.Hat.newBuilder();

        hatBuilder.setStats(stats.buildProtoClass());
        // TODO: add HatEffect

        return hatBuilder.build();
    }

    /**
     * Creates an item Protocol Buffer based on the Hat instance this function is called on.
     * @return an item protocol buffer representing the Hat
     */
    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Hat hatProtoClass = buildProtoClassHat();
        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setHat(hatProtoClass);

        return itemBuilder.build();
    }

    /**
     * Getter for the Hat's effect.
     * @return the Hat's effect
     */
    public HatEffect getHatEffect() {
        return hatEffect;
    }

    /**
     * Creates a default Hat for use in testing. NOTE: Each default item provides a particular stat increase;
     * the default hat provides 20% increased max health and does not yet have a Hat Effect.
     * @return a default Hat object
     */
    public static Hat createDefaultHat() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 0, 0, 0.2, 0, 0, 0, 0, 0, 0, 0);
        Hat defaultHat = new Hat(defaultStatusModifier, null);
        return defaultHat;
    }
}
