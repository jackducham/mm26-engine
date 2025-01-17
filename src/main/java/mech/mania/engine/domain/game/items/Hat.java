package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Hat extends Wearable {
    private MagicEffect magicEffect;

    /**
     * Creates a hat with given stats and hat effect.
     * @param stats a StatusModifier storing the stats the Hat will be created with
     * @param magicEffect the effect the Hat will have
     */
    public Hat(StatusModifier stats, MagicEffect magicEffect, String sprite) {
        super(stats, sprite);
        this.magicEffect = magicEffect;
    }

    /**
     * Creates a Hat object based on a given Protocol Buffer.
     * @param hatProto the protocol buffer to be copied
     */
    public Hat(ItemProtos.Hat hatProto) {
        super(new StatusModifier(hatProto.getStats()), hatProto.getSprite());

        this.turnsToDeletion = hatProto.getTurnsToDeletion();

        switch (hatProto.getMagicEffect()) {
            case SHOES_BOOST:
                this.magicEffect = MagicEffect.SHOES_BOOST;
                break;
            case WEAPON_BOOST:
                this.magicEffect = MagicEffect.WEAPON_BOOST;
                break;
            case CLOTHES_BOOST:
                this.magicEffect = MagicEffect.CLOTHES_BOOST;
                break;
            case STACKING_BONUS:
                this.magicEffect = MagicEffect.STACKING_BONUS;
                break;
            case TRIPLED_ON_HIT:
                this.magicEffect = MagicEffect.TRIPLED_ON_HIT;
                break;
            case LINGERING_POTIONS:
                this.magicEffect = MagicEffect.LINGERING_POTIONS;
                break;
            case NONE:
                this.magicEffect = MagicEffect.NONE;
                break;
        }
    }

    /**
     * Creates a hat Protocol Buffer based on the Hat instance this function is called on.
     * @return a hat protocol buffer representing the Hat
     */
    public ItemProtos.Hat buildProtoClassHat() {
        ItemProtos.Hat.Builder hatBuilder = ItemProtos.Hat.newBuilder();

        hatBuilder.setTurnsToDeletion(turnsToDeletion);
        hatBuilder.setStats(stats.buildProtoClass());
        hatBuilder.setSprite(sprite);
        switch (magicEffect) {
            case SHOES_BOOST:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.SHOES_BOOST);
                break;
            case WEAPON_BOOST:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.WEAPON_BOOST);
                break;
            case CLOTHES_BOOST:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.CLOTHES_BOOST);
                break;
            case STACKING_BONUS:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.STACKING_BONUS);
                break;
            case TRIPLED_ON_HIT:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.TRIPLED_ON_HIT);
                break;
            case LINGERING_POTIONS:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.LINGERING_POTIONS);
                break;
            case NONE:
                hatBuilder.setMagicEffect(ItemProtos.MagicEffect.NONE);
        }
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
    public MagicEffect getMagicEffect() {
        return magicEffect;
    }

    /**
     * Creates a default Hat for use in testing. NOTE: Each default item provides a particular stat increase;
     * the default hat provides 20% increased max health and does not yet have a Hat Effect.
     * @return a default Hat object
     */
    public static Hat createDefaultHat() {
        StatusModifier defaultStatusModifier = new StatusModifier(0, 0, 0,
                0.2, 0, 0, 0, 0,
                0, 0, 0);

        Hat defaultHat = new Hat(defaultStatusModifier, MagicEffect.NONE, "");
        return defaultHat;
    }

    public Item copy(){
        return new Hat(new StatusModifier(this.stats), this.magicEffect, this.sprite);
    }
}
