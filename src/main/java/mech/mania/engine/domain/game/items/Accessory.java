package mech.mania.engine.domain.game.items;

import mech.mania.engine.domain.model.ItemProtos;

public class Accessory extends Wearable {
    private MagicEffect magicEffect;

    /**
     * Creates an accessory with given stats and hat effect.
     * @param stats a StatusModifier storing the stats the Accessory will be created with
     * @param magicEffect the effect the Accessory will have
     */
    public Accessory(StatusModifier stats, MagicEffect magicEffect, String sprite) {
        super(stats, sprite);
        this.magicEffect = magicEffect;
    }

    /**
     * Creates a Hat object based on a given Protocol Buffer.
     * @param accessoryProto the protocol buffer to be copied
     */
    public Accessory(ItemProtos.Accessory accessoryProto) {
        super(new StatusModifier(accessoryProto.getStats()), accessoryProto.getSprite());
        this.turnsToDeletion = accessoryProto.getTurnsToDeletion();
        switch (accessoryProto.getMagicEffect()) {
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
     * Creates an accessory Protocol Buffer based on the Accessory instance this function is called on.
     * @return a accessory protocol buffer representing the Accessory
     */
    public ItemProtos.Accessory buildProtoClassAccessory() {
        ItemProtos.Accessory.Builder accessoryBuilder = ItemProtos.Accessory.newBuilder();
        accessoryBuilder.setTurnsToDeletion(turnsToDeletion);
        accessoryBuilder.setStats(stats.buildProtoClass());
        switch (magicEffect) {
            case SHOES_BOOST:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.SHOES_BOOST);
                break;
            case WEAPON_BOOST:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.WEAPON_BOOST);
                break;
            case CLOTHES_BOOST:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.CLOTHES_BOOST);
                break;
            case STACKING_BONUS:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.STACKING_BONUS);
                break;
            case TRIPLED_ON_HIT:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.TRIPLED_ON_HIT);
                break;
            case LINGERING_POTIONS:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.LINGERING_POTIONS);
                break;
            case NONE:
                accessoryBuilder.setMagicEffect(ItemProtos.MagicEffect.NONE);
        }
        return accessoryBuilder.build();
    }

    /**
     * Creates an item Protocol Buffer based on the Accessory instance this function is called on.
     * @return an item protocol buffer representing the Accessory
     */
    public ItemProtos.Item buildProtoClassItem() {
        ItemProtos.Accessory accessoryProtoClass = buildProtoClassAccessory();
        ItemProtos.Item.Builder itemBuilder = ItemProtos.Item.newBuilder();
        itemBuilder.setAccessory(accessoryProtoClass);

        return itemBuilder.build();
    }

    public Item copy(){
        return new Accessory(new StatusModifier(this.stats), this.magicEffect, this.sprite);
    }

    /**
     * Getter for the Accessory's effect.
     * @return the Accessory's effect
     */
    public MagicEffect getMagicEffect() {
        return magicEffect;
    }
}
