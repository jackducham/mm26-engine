package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.*;


public class Player extends Character {
    private static final int INVENTORY_SIZE = 16;
    private Hat hat;
    private Clothes clothes;
    private Shoes shoes;
    private Item[] inventory;

    public Player(Position spawnPoint, String name) {
        super(0, spawnPoint, null, name);
        hat = null;
        clothes = null;
        shoes = null;
        inventory = new Item[INVENTORY_SIZE];
    }

    public Player(CharacterProtos.Player playerProto) {
        super(
                playerProto.getCharacter().getExperience(),
                new Position(playerProto.getCharacter().getSpawnPoint()),
                new Weapon(playerProto.getCharacter().getWeapon()),
                playerProto.getCharacter().getName()
        );
        hat = new Hat(playerProto.getHat());
        clothes = new Clothes(playerProto.getClothes());
        shoes = new Shoes(playerProto.getShoes());
        inventory = new Item[INVENTORY_SIZE];

        for (int i = 0; i < playerProto.getInventoryCount(); i++) {
            ItemProtos.Item protoItem = playerProto.getInventory(i);
            switch(protoItem.getItemCase()) {
                case CLOTHES:
                    inventory[i] = new Clothes(protoItem.getClothes());
                    break;
                case HAT:
                    inventory[i] = new Hat(protoItem.getHat());
                    break;
                case SHOES:
                    inventory[i] = new Shoes(protoItem.getShoes());
                    break;
                case WEAPON:
                    inventory[i] = new Weapon(protoItem.getWeapon());
                    break;
                case CONSUMABLE:
                    inventory[i] = new Consumable(protoItem.getMaxStack(), protoItem.getConsumable());
            }
        }
    }

    public Hat getHat() {
        return hat;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public int getInventorySize() {
        return INVENTORY_SIZE;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public void setInventory(int index, Item item) {
        if (index < 0 || index > INVENTORY_SIZE) {
            return;
        }
        inventory[index] = item;
    }

    @Override
    public double getMaxHealth() {
        double maxHealth = super.getMaxHealth();

        // Add wearable effects
        if (hat != null) {
            maxHealth += hat.getStats().getHealthChange();
        }
        if (clothes != null) {
            maxHealth += clothes.getStats().getHealthChange();
        }
        if (shoes != null) {
            maxHealth += shoes.getStats().getHealthChange();
        }

        // Add active effects
        for (TempStatusModifier mod : activeEffects) {
            maxHealth += mod.getHealthChange();
        }

        return maxHealth;
    }

    @Override
    public double getSpeed() {
        double speed = super.getSpeed();

        // Add wearable effects
        if (hat != null) {
            speed += hat.getStats().getSpeedChange();
        }
        if (clothes != null) {
            speed += clothes.getStats().getSpeedChange();
        }
        if (shoes != null) {
            speed += shoes.getStats().getSpeedChange();
        }

        // Add active effects
        for (TempStatusModifier mod : activeEffects) {
            speed += mod.getSpeedChange();
        }

        return speed;
    }

    @Override
    public double getPhysicalDamage() {
        double physicalDamage = super.getPhysicalDamage();

        // Add wearable effects
        if (hat != null) {
            physicalDamage += hat.getStats().getPhysicalDamageChange();
        }
        if (clothes != null) {
            physicalDamage += clothes.getStats().getPhysicalDamageChange();
        }
        if (shoes != null) {
            physicalDamage += shoes.getStats().getPhysicalDamageChange();
        }

        // Add active effects
        for (TempStatusModifier mod : activeEffects) {
            physicalDamage += mod.getPhysicalDamageChange();
        }

        return physicalDamage;
    }

    @Override
    public double getMagicalDamage() {
        double magicalDamage = super.getMagicalDamage();

        // Add wearable effects
        if (hat != null) {
            magicalDamage += hat.getStats().getMagicDamageChange();
        }
        if (clothes != null) {
            magicalDamage += clothes.getStats().getMagicDamageChange();
        }
        if (shoes != null) {
            magicalDamage += shoes.getStats().getMagicDamageChange();
        }

        // Add active effects
        for (TempStatusModifier mod : activeEffects) {
            magicalDamage += mod.getMagicDamageChange();
        }

        return magicalDamage;
    }

    @Override
    public double getMagicalDefense() {
        double magicalDefense = super.getMagicalDefense();

        // Add wearable effects
        if (hat != null) {
            magicalDefense += hat.getStats().getMagicDefenseChange();
        }
        if (clothes != null) {
            magicalDefense += clothes.getStats().getMagicDefenseChange();
        }
        if (shoes != null) {
            magicalDefense += shoes.getStats().getMagicDefenseChange();
        }

        // Add active effects
        for (TempStatusModifier mod : activeEffects) {
            magicalDefense += mod.getMagicDefenseChange();
        }

        return magicalDefense;
    }

    @Override
    public double getPhysicalDefense() {
        double physicalDefense = super.getPhysicalDefense();

        // Add wearable effects
        if (hat != null) {
            physicalDefense += hat.getStats().getPhysicalDefenseChange();
        }
        if (clothes != null) {
            physicalDefense += clothes.getStats().getPhysicalDefenseChange();
        }
        if (shoes != null) {
            physicalDefense += shoes.getStats().getPhysicalDefenseChange();
        }

        // Add active effects
        for (TempStatusModifier mod : activeEffects) {
            physicalDefense += mod.getPhysicalDefenseChange();
        }

        return physicalDefense;
    }


    /**
     * Adds a new temporary status modifier to the Player's list of modifiers.
     *
     * @param statusToApply the status which will be added to the Player's list
     * @return true if successful
     */
    public boolean applyStatus(TempStatusModifier statusToApply) {
        if(statusToApply == null) {
            return false;
        }
        activeEffects.add(statusToApply);
        return true;
    }


    //Equip Item and Helper Functions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Exchanges an item in the Player's inventory with an equipped.
     *
     * @param index the index of the inventory which contains the item to be equipped.
     * @return true if successful
     */
    public boolean equipItem(int index) {
        Item itemToEquip;
        if (inventory[index] != null) {
            itemToEquip = inventory[index];
        } else {
            return false;
        }
        if (itemToEquip instanceof Hat) {
            return equipHat((Hat)itemToEquip, index);
        } else if (itemToEquip instanceof Clothes) {
            return equipClothes((Clothes)itemToEquip, index);
        } else if (itemToEquip instanceof Shoes) {
            return equipShoes((Shoes)itemToEquip, index);
        } else if (itemToEquip instanceof Weapon) {
            return equipWeapon((Weapon)itemToEquip, index);
        } else if (itemToEquip instanceof Consumable) {
            return useConsumable((Consumable)itemToEquip, index);
        }
        return false;
    }

    /**
     * Exchanges hat in Player parameters with hat in inventory
     *
     * @param index index of the inventory to which the currently equipped hat will be returned
     * @param hatToEquip the hat which will replace the currently equipped hat
     * @return true if hat was successfully equipped
     */
    private boolean equipHat(Hat hatToEquip, int index) {
        Hat temp = hat;
        hat = hatToEquip;
        inventory[index] = temp;
        return true;
    }

    /**
     * Exchanges clothes in Player parameters with clothes in inventory
     *
     * @param index index of the inventory to which the currently equipped clothes will be returned
     * @param clothesToEquip the clothes which will replace the currently equipped clothes
     * @return true if clothes were successfully equipped
     */
    private boolean equipClothes(Clothes clothesToEquip, int index) {
        Clothes temp = clothes;
        clothes = clothesToEquip;
        inventory[index] = temp;
        return true;
    }

    /**
     * Exchanges shoes in Player parameters with shoes in inventory
     *
     * @param index index of the inventory to which the currently equipped shoes will be returned
     * @param shoesToEquip the shoes which will replace the currently equipped shoes
     * @return true if shoes were successfully equipped
     */
    private boolean equipShoes(Shoes shoesToEquip, int index) {
        Shoes temp = shoes;
        shoes = shoesToEquip;
        inventory[index] = temp;
        return true;
    }

    /**
     * Exchanges weapon in Player parameters with weapon in inventory
     *
     * @param index index of the inventory to which the currently equipped weapon will be returned
     * @param weaponToEquip the weapon which will replace the currently equipped weapon
     * @return true if weapon was successfully equipped
     */
    private boolean equipWeapon(Weapon weaponToEquip, int index) {
        Weapon temp = weapon;
        weapon = weaponToEquip;
        inventory[index] = temp;
        return true;
    }

    /**
     * Removes one stack of the consumable and applies its statusModifier to the player.
     * Also deletes the consumable if it has no stacks remaining.
     *
     * @param consumableToConsume the consumable which will be consumed
     * @param index the index in the inventory the consumable is located at
     */
    private boolean useConsumable(Consumable consumableToConsume, int index) {
        int stacks = consumableToConsume.getStacks();
        applyStatus(consumableToConsume.getEffect());
        if(stacks == 1) {
            inventory[index] = null;
        } else {
            consumableToConsume.setStacks(stacks - 1);
        }
        return true;
    }

    /**
     * @return index of first null inventory space, -1 if none
     */
    public int getFreeInventoryIndex() {
        for (int i = 0; i < INVENTORY_SIZE; i++) {
            Item item = inventory[i];
            if (item == null) {
                return i;
            }
        }
        return -1;
    }
}
