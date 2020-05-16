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

        // TODO: add taggedPlayersDamage
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

    public CharacterProtos.Player buildProtoClassPlayer() {
        CharacterProtos.Character characterProtoClass = super.buildProtoClassCharacter();
        CharacterProtos.Player.Builder playerBuilder = CharacterProtos.Player.newBuilder();

        playerBuilder.mergeCharacter(characterProtoClass);

        for (int i = 0; i < INVENTORY_SIZE; i++) {
            Item curItem = inventory[i];
            if (curItem instanceof Clothes) {
                playerBuilder.setInventory(i, ((Clothes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Hat) {
                playerBuilder.setInventory(i, ((Hat)curItem).buildProtoClassItem());
            } else if (curItem instanceof Shoes) {
                playerBuilder.setInventory(i, ((Shoes)curItem).buildProtoClassItem());
            } else if (curItem instanceof Weapon) {
                playerBuilder.setInventory(i, ((Weapon)curItem).buildProtoClassItem());
            } else if (curItem instanceof Consumable) {
                playerBuilder.setInventory(i, ((Consumable)curItem).buildProtoClass());
            }
        }

        if (hat != null) {
            playerBuilder.setHat(hat.buildProtoClassHat());
        }
        if (clothes != null) {
            playerBuilder.setClothes(clothes.buildProtoClassClothes());
        }
        if (shoes != null) {
            playerBuilder.setShoes(shoes.buildProtoClassShoes());
        }

        return playerBuilder.build();
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
    public int getMaxHealth() {
        // First add all flat values
        int maxHealth = baseMaxHealth + getLevel()*maxHealthScaling + flatHealthChange;

        // Add flat wearable effects
        if (hat != null) {
            maxHealth += hat.getStats().getFlatHealthChange();
        }
        if (clothes != null) {
            maxHealth += clothes.getStats().getFlatHealthChange();
        }
        if (shoes != null) {
            maxHealth += shoes.getStats().getFlatHealthChange();
        }

        // Add percent effects
        if (hat != null) {
            maxHealth *= hat.getStats().getPercentHealthChange();
        }
        if (clothes != null) {
            maxHealth *= clothes.getStats().getPercentHealthChange();
        }
        if (shoes != null) {
            maxHealth *= shoes.getStats().getPercentHealthChange();
        }
        maxHealth *= percentHealthChange;

        return maxHealth;
    }

    @Override
    public int getSpeed() {
        // First add all flat values
        int speed = baseSpeed + getLevel()*speedScaling + flatSpeedChange;

        // Add flat wearable effects
        if (hat != null) {
            speed += hat.getStats().getFlatSpeedChange();
        }
        if (clothes != null) {
            speed += clothes.getStats().getFlatSpeedChange();
        }
        if (shoes != null) {
            speed += shoes.getStats().getFlatSpeedChange();
        }

        // Add percent effects
        if (hat != null) {
            speed *= hat.getStats().getPercentSpeedChange();
        }
        if (clothes != null) {
            speed *= clothes.getStats().getPercentSpeedChange();
        }
        if (shoes != null) {
            speed *= shoes.getStats().getPercentSpeedChange();
        }
        speed *= percentSpeedChange;

        return speed;
    }

    @Override
    public int getPhysicalDamage() {
        // First add all flat values
        int physicalDamage = basePhysicalDamage + getLevel()*physicalDamageScaling + flatPhysicalDamageChange;

        // Add flat wearable effects
        if (hat != null) {
            physicalDamage += hat.getStats().getFlatPhysicalDamageChange();
        }
        if (clothes != null) {
            physicalDamage += clothes.getStats().getFlatPhysicalDamageChange();
        }
        if (shoes != null) {
            physicalDamage += shoes.getStats().getFlatPhysicalDamageChange();
        }

        // Add percent effects
        if (hat != null) {
            physicalDamage *= hat.getStats().getPercentPhysicalDamageChange();
        }
        if (clothes != null) {
            physicalDamage *= clothes.getStats().getPercentPhysicalDamageChange();
        }
        if (shoes != null) {
            physicalDamage *= shoes.getStats().getPercentPhysicalDamageChange();
        }
        physicalDamage *= percentPhysicalDamageChange;

        return physicalDamage;
    }

    @Override
    public int getMagicalDamage() {
        // First add all flat values
        int magicalDamage = baseMagicalDamage + getLevel()*magicalDamageScaling + flatMagicDamageChange;

        // Add flat wearable effects
        if (hat != null) {
            magicalDamage += hat.getStats().getFlatMagicDamageChange();
        }
        if (clothes != null) {
            magicalDamage += clothes.getStats().getFlatMagicDamageChange();
        }
        if (shoes != null) {
            magicalDamage += shoes.getStats().getFlatMagicDamageChange();
        }

        // Add percent effects
        if (hat != null) {
            magicalDamage *= hat.getStats().getPercentMagicDamageChange();
        }
        if (clothes != null) {
            magicalDamage *= clothes.getStats().getPercentMagicDamageChange();
        }
        if (shoes != null) {
            magicalDamage *= shoes.getStats().getPercentMagicDamageChange();
        }
        magicalDamage *= percentMagicDamageChange;

        return magicalDamage;
    }

    @Override
    public int getMagicalDefense() {
        // First add all flat values
        int magicalDefense = baseMagicalDefense + getLevel()*magicalDefenseScaling + flatMagicDefenseChange;

        // Add flat wearable effects
        if (hat != null) {
            magicalDefense += hat.getStats().getFlatMagicDefenseChange();
        }
        if (clothes != null) {
            magicalDefense += clothes.getStats().getFlatMagicDefenseChange();
        }
        if (shoes != null) {
            magicalDefense += shoes.getStats().getFlatMagicDefenseChange();
        }

        // Add percent effects
        if (hat != null) {
            magicalDefense *= hat.getStats().getPercentMagicDefenseChange();
        }
        if (clothes != null) {
            magicalDefense *= clothes.getStats().getPercentMagicDefenseChange();
        }
        if (shoes != null) {
            magicalDefense *= shoes.getStats().getPercentMagicDefenseChange();
        }
        magicalDefense *= percentMagicDefenseChange;

        return magicalDefense;
    }

    @Override
    public int getPhysicalDefense() {
        // First add all flat values
        int physicalDefense = basePhysicalDefense + getLevel()*physicalDefenseScaling + flatPhysicalDefenseChange;

        // Add flat wearable effects
        if (hat != null) {
            physicalDefense += hat.getStats().getFlatPhysicalDefenseChange();
        }
        if (clothes != null) {
            physicalDefense += clothes.getStats().getFlatPhysicalDefenseChange();
        }
        if (shoes != null) {
            physicalDefense += shoes.getStats().getFlatPhysicalDefenseChange();
        }

        // Add percent effects
        if (hat != null) {
            physicalDefense *= hat.getStats().getPercentPhysicalDefenseChange();
        }
        if (clothes != null) {
            physicalDefense *= clothes.getStats().getPercentPhysicalDefenseChange();
        }
        if (shoes != null) {
            physicalDefense *= shoes.getStats().getPercentPhysicalDefenseChange();
        }
        physicalDefense *= percentPhysicalDefenseChange;

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
