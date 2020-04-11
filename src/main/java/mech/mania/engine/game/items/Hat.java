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

    public ItemProtos.Hat buildProtoClassHat() {
        ItemProtos.Hat.Builder hatBuilder = ItemProtos.Hat.newBuilder();

        hatBuilder.setStats(stats.buildProtoClass());
        // TODO: add HatEffect

        return hatBuilder.build();
    }

    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Hat hatProtoClass = buildProtoClassHat();
        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setHat(hatProtoClass);

        return itemBuilder.build();
    }

    public HatEffect getHatEffect() {
        return hatEffect;
    }
}
