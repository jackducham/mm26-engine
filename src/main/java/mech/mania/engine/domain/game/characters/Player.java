package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.ItemProtos;


public class Player extends Character {
    private static final int INVENTORY_SIZE = 16;
    private Hat hat;
    private Clothes clothes;
    private Shoes shoes;
    private Item[] inventory;

    private static final int BASE_SPEED = 5;
    private static final int BASE_MAX_HEALTH = 20;
    private static final int BASE_ATTACK = 0;
    private static final int BASE_DEFENSE = 0;

    /**
     * Standard Constructor which uses default static values for speed, hp, atk, and def.
     * @param name Player's name
     * @param spawnPoint Player's spawn point
     */
    public Player(String name, Position spawnPoint) {
        super(name, BASE_SPEED, BASE_MAX_HEALTH, BASE_ATTACK, BASE_DEFENSE, 0, spawnPoint, null);
        hat = null;
        clothes = null;
        shoes = null;
        inventory = new Item[INVENTORY_SIZE];
    }

    public Player(CharacterProtos.Player playerProto) {
        super(
                playerProto.getCharacter().getName(),
                playerProto.getCharacter().getBaseSpeed(),
                playerProto.getCharacter().getBaseMaxHealth(),
                playerProto.getCharacter().getBaseAttack(),
                playerProto.getCharacter().getBaseDefense(),
                playerProto.getCharacter().getExperience(),
                new Position(playerProto.getCharacter().getSpawnPoint()),
                new Weapon(playerProto.getCharacter().getWeapon())
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
    public int getSpeed() {
        double speed = super.getSpeed();

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
        if (weapon != null) {
            speed += weapon.getStats().getFlatSpeedChange();
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
        if (weapon != null) {
            speed *= weapon.getStats().getPercentSpeedChange();
        }

        return (int) speed;
    }

    @Override
    public int getMaxHealth() {
        double maxHealth = super.getMaxHealth();

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
        if (weapon != null) {
            maxHealth += weapon.getStats().getFlatHealthChange();
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
        if (weapon != null) {
            maxHealth *= weapon.getStats().getPercentHealthChange();
        }

        return (int) maxHealth;
    }

    @Override
    public int getExperience() {
        double experience = super.getExperience();

        // Add flat wearable effects
        if (hat != null) {
            experience += hat.getStats().getFlatExperienceChange();
        }
        if (clothes != null) {
            experience += clothes.getStats().getFlatExperienceChange();
        }
        if (shoes != null) {
            experience += shoes.getStats().getFlatExperienceChange();
        }
        if (weapon != null) {
            experience += weapon.getStats().getFlatExperienceChange();
        }

        // Add percent effects
        if (hat != null) {
            experience *= hat.getStats().getPercentExperienceChange();
        }
        if (clothes != null) {
            experience *= clothes.getStats().getPercentExperienceChange();
        }
        if (shoes != null) {
            experience *= shoes.getStats().getPercentExperienceChange();
        }
        if (weapon != null) {
            experience *= weapon.getStats().getPercentExperienceChange();
        }

        return (int) experience;
    }

    @Override
    public int getAttack() {
        double attack = super.getAttack();

        // Add flat wearable effects
        if (hat != null) {
            attack += hat.getStats().getFlatAttackChange();
        }
        if (clothes != null) {
            attack += clothes.getStats().getFlatAttackChange();
        }
        if (shoes != null) {
            attack += shoes.getStats().getFlatAttackChange();
        }
        if (weapon != null) {
            attack += weapon.getStats().getFlatAttackChange();
        }

        // Add percent effects
        if (hat != null) {
            attack *= hat.getStats().getPercentAttackChange();
        }
        if (clothes != null) {
            attack *= clothes.getStats().getPercentAttackChange();
        }
        if (shoes != null) {
            attack *= shoes.getStats().getPercentAttackChange();
        }
        if (weapon != null) {
            attack *= weapon.getStats().getPercentAttackChange();
        }

        return (int) attack;
    }

    @Override
    public int getDefense() {
        double defense = super.getDefense();

        // Add flat wearable effects
        if (hat != null) {
            defense += hat.getStats().getFlatDefenseChange();
        }
        if (clothes != null) {
            defense += clothes.getStats().getFlatDefenseChange();
        }
        if (shoes != null) {
            defense += shoes.getStats().getFlatDefenseChange();
        }
        if (weapon != null) {
            defense += weapon.getStats().getFlatDefenseChange();
        }

        // Add percent effects
        if (hat != null) {
            defense *= hat.getStats().getPercentDefenseChange();
        }
        if (clothes != null) {
            defense *= clothes.getStats().getPercentDefenseChange();
        }
        if (shoes != null) {
            defense *= shoes.getStats().getPercentDefenseChange();
        }
        if (weapon != null) {
            defense *= weapon.getStats().getPercentDefenseChange();
        }

        return (int) defense;
    }


    /**
     * Adds a new temporary status modifier to the Player's list of modifiers.
     *
     * @param effect the status which will be added to the Player's list
     * @return true if successful
     */
    public boolean applyEffect(TempStatusModifier effect) {
        if(effect == null) {
            return false;
        }
        activeEffects.add(effect);
        activeAttackers.add(""); // add empty String to keep index matching
        return true;
    }


    // Equip Item and Helper Functions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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
        applyEffect(consumableToConsume.getEffect());
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
