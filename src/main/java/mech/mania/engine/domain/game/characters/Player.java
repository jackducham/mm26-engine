package mech.mania.engine.domain.game.characters;

import kotlin.Triple;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.*;
import mech.mania.engine.domain.model.CharacterProtos;
import mech.mania.engine.domain.model.ItemProtos;

import java.util.ArrayList;

import static java.lang.Math.max;


public class Player extends Character {
    private static final int INVENTORY_SIZE = 16;
    private Hat hat;
    private Clothes clothes;
    private Shoes shoes;
    private Accessory accessory;
    private ArrayList<Item> inventory;
    private Stats playerStats = new Stats();

    public String bottom_sprite;
    public String top_sprite;
    public String head_sprite;

    public static final int REVIVE_TICKS = 4;
    public static final int BASE_SPEED = 1;
    public static final int BASE_MAX_HEALTH = 80;
    public static final int BASE_ATTACK = 0;
    public static final int BASE_DEFENSE = 0;
    private static final Weapon starterWeapon = Weapon.createStarterWeapon();

    public static final int SPAWN_X = 2;
    public static final int SPAWN_Y = 3;

    public static final String DEFAULT_BODY_SPRITE = "mm26_wearables/_defaults/default_bod_2.png";
    public static final String DEFAULT_BOTTOM_SPRITE = "mm26_wearables/_defaults/default_bottom.png";
    public static final String DEFAULT_TOP_SPRITE = "mm26_wearables/_defaults/default_top.png";
    public static final String DEFAULT_HEAD_SPRITE = "mm26_wearables/_defaults/default_head.png";

    /**
     * Standard Constructor which uses default static values for speed, hp, atk, and def.
     * @param name Player's name
     * @param spawnPoint Player's spawn point
     */
    public Player(String name, Position spawnPoint) {
        super(name, DEFAULT_BODY_SPRITE, BASE_SPEED, BASE_MAX_HEALTH, BASE_ATTACK, BASE_DEFENSE, 1, spawnPoint, starterWeapon, REVIVE_TICKS);
        hat = null;
        clothes = null;
        shoes = null;
        inventory = new ArrayList<>();

        bottom_sprite = DEFAULT_BOTTOM_SPRITE;
        top_sprite = DEFAULT_TOP_SPRITE;
        head_sprite = DEFAULT_HEAD_SPRITE;
    }

    public Player(CharacterProtos.Player playerProto) {
        super(playerProto.getCharacter(), REVIVE_TICKS);

        hat = new Hat(playerProto.getHat());
        accessory = new Accessory(playerProto.getAccessory());
        clothes = new Clothes(playerProto.getClothes());
        shoes = new Shoes(playerProto.getShoes());
        inventory = new ArrayList<>();

        for (int i = 0; i < playerProto.getInventoryCount(); i++) {
            ItemProtos.Item protoItem = playerProto.getInventory(i);
            switch(protoItem.getItemCase()) {
                case CLOTHES:
                    inventory.add(new Clothes(protoItem.getClothes()));
                    break;
                case HAT:
                    inventory.add(new Hat(protoItem.getHat()));
                    break;
                case ACCESSORY:
                    inventory.add(new Accessory(protoItem.getAccessory()));
                case SHOES:
                    inventory.add(new Shoes(protoItem.getShoes()));
                    break;
                case WEAPON:
                    inventory.add(new Weapon(protoItem.getWeapon()));
                    break;
                case CONSUMABLE:
                    inventory.add(new Consumable(protoItem.getConsumable()));
            }
        }

        bottom_sprite = playerProto.getBottomSprite();
        top_sprite = playerProto.getTopSprite();
        head_sprite = playerProto.getHeadSprite();
    }

    public CharacterProtos.Player buildProtoClassPlayer() {
        CharacterProtos.Character characterProtoClass = super.buildProtoClassCharacter();
        CharacterProtos.Player.Builder playerBuilder = CharacterProtos.Player.newBuilder();

        playerBuilder.mergeCharacter(characterProtoClass);

        for (int i = 0; i < inventory.size(); i++) {
            Item curItem = inventory.get(i);
            if(curItem == null) continue;
            playerBuilder.addInventory(curItem.buildProtoClassItem());
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
        if (accessory != null){
            playerBuilder.setAccessory(accessory.buildProtoClassAccessory());
        }

        return playerBuilder.build();
    }

    public Hat getHat() {
        return hat;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public Clothes getClothes() {
        return clothes;
    }

    public Shoes getShoes() {
        return shoes;
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

    public int getMaxInventorySize() {
        return INVENTORY_SIZE;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void pickupItem(Item item) {
        if(item != null && inventory.size() < INVENTORY_SIZE){
            inventory.add(item);
        }
    }

    public void setPlayerStats(Stats playerStats) {
        this.playerStats = playerStats;
    }

    /**
     * Checks if either hat or accessory has the chosen MagicEffect
     */
    public boolean hasMagicEffect(MagicEffect effect){
        return (hat != null && hat.getMagicEffect() == effect)
                || (accessory != null && accessory.getMagicEffect() == effect);
    }

    /**
     * Applies active effects and updates the death state
     * This should be called once a turn
     * This overload also applies the regen from wearables (because players have wearables, but monsters do not)
     */
    @Override
    public void updateCharacter(GameState gameState) {
        if(hasMagicEffect(MagicEffect.STACKING_BONUS)) {
            TempStatusModifier hatStats = new TempStatusModifier(hat.getStats());
            hatStats.setTurnsLeft(4);
            applyEffect(this.getName(), true, hatStats);
        }
        updateActiveEffects();
        applyWearableRegen();
        updateLevel();
        healOnSpawnPoint();
        updateDeathState(gameState);
        playerStats.incrementTurnsSinceJoined();
    }

    /**
     * Applies the regeneration from wearable items to a player's health. Can only be called once per turn.
     */
    private void applyWearableRegen() {
        int regenFromWearables = 0;
        if(hat != null) {
            regenFromWearables += hat.getStats().getFlatRegenPerTurn();
        }
        if (accessory != null) {
            regenFromWearables += accessory.getStats().getFlatRegenPerTurn();
        }
        if(clothes != null) {
            regenFromWearables += clothes.getStats().getFlatRegenPerTurn();
        }
        if(shoes != null) {
            regenFromWearables += shoes.getStats().getFlatRegenPerTurn();
        }
        if(weapon != null) {
            regenFromWearables += weapon.getStats().getFlatRegenPerTurn();
        }

        updateCurrentHealth(regenFromWearables);
    }

    /**
     * If player's on spawn point, give them 5 extra health
     */
    public void healOnSpawnPoint() {
        if (getPosition().equals(getSpawnPoint())) {
            int healthGain = (int) Math.round(0.1 * getMaxHealth());
            updateCurrentHealth(healthGain);
        }
    }

    @Override
    public int getSpeed() {
        int flatChange = 0;
        double percentChange = 0;

        if (hat != null) {
            flatChange += hat.getStats().getFlatSpeedChange();
            percentChange += hat.getStats().getPercentSpeedChange();
        }
        if (accessory != null) {
            flatChange += accessory.getStats().getFlatSpeedChange();
            percentChange += accessory.getStats().getPercentSpeedChange();
        }
        if (clothes != null) {
            flatChange += clothes.getStats().getFlatSpeedChange();
            percentChange += clothes.getStats().getPercentSpeedChange();
        }
        if (shoes != null) {
            flatChange += shoes.getStats().getFlatSpeedChange();
            percentChange += shoes.getStats().getPercentSpeedChange();

            if(hasMagicEffect(MagicEffect.SHOES_BOOST)) {
                flatChange += shoes.getStats().getFlatSpeedChange();
            }

        }
        if (weapon != null) {
            flatChange += weapon.getStats().getFlatSpeedChange();
            percentChange += weapon.getStats().getPercentSpeedChange();
        }

        // Add active effects
        for (Triple<TempStatusModifier, String, Boolean> effect: activeEffects) {
            flatChange += effect.getFirst().getFlatSpeedChange();
            percentChange += effect.getFirst().getPercentSpeedChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseSpeed, flatChange);
        percentChange = max(-1, percentChange);

        double speed = (baseSpeed + flatChange) * (1 + percentChange);
        return max(1, (int) speed); // speed can't be below 1
    }

    @Override
    public int getMaxHealth() {
        int flatChange = 0;
        double percentChange = 0;

        if (hat != null) {
            flatChange += hat.getStats().getFlatHealthChange();
            percentChange += hat.getStats().getPercentHealthChange();
        }
        if (accessory != null) {
            flatChange += accessory.getStats().getFlatHealthChange();
            percentChange += accessory.getStats().getPercentHealthChange();
        }
        if (clothes != null) {
            flatChange += clothes.getStats().getFlatHealthChange();
            percentChange += clothes.getStats().getPercentHealthChange();
        }
        if (shoes != null) {
            flatChange += shoes.getStats().getFlatHealthChange();
            percentChange += shoes.getStats().getPercentHealthChange();
        }
        if (weapon != null) {
            flatChange += weapon.getStats().getFlatHealthChange();
            percentChange += weapon.getStats().getPercentHealthChange();
        }

        // Add active effects
        for (Triple<TempStatusModifier, String, Boolean> effect: activeEffects) {
            flatChange += effect.getFirst().getFlatHealthChange();
            percentChange += effect.getFirst().getPercentHealthChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseMaxHealth, flatChange);
        percentChange = max(-1, percentChange);

        double maxHealth = (baseMaxHealth + flatChange) * (1 + percentChange);
        return max(1, (int) maxHealth); // maxHealth can't be below 1
    }

    @Override
    public int getAttack() {
        int flatChange = 0;
        double percentChange = 0;

        if (hat != null) {
            flatChange += hat.getStats().getFlatAttackChange();
            percentChange += hat.getStats().getPercentAttackChange();
        }
        if (accessory != null) {
            flatChange += accessory.getStats().getFlatAttackChange();
            percentChange += accessory.getStats().getPercentAttackChange();
        }
        if (clothes != null) {
            flatChange += clothes.getStats().getFlatAttackChange();
            percentChange += clothes.getStats().getPercentAttackChange();
        }
        if (shoes != null) {
            flatChange += shoes.getStats().getFlatAttackChange();
            percentChange += shoes.getStats().getPercentAttackChange();
        }
        if (weapon != null) {
            flatChange += weapon.getStats().getFlatAttackChange();
            percentChange += weapon.getStats().getPercentAttackChange();

            if(hasMagicEffect(MagicEffect.WEAPON_BOOST)) {
                flatChange += (weapon.getStats().getFlatAttackChange() * 0.5);
            }

        }
        // Add active effects
        for (Triple<TempStatusModifier, String, Boolean> effect: activeEffects) {
            flatChange += effect.getFirst().getFlatAttackChange();
            percentChange += effect.getFirst().getPercentAttackChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseAttack, flatChange);
        percentChange = max(-1, percentChange);

        double attack = (baseAttack + flatChange) * (1 + percentChange);
        return max(1, (int) attack); // Attack can't be below 1
    }

    @Override
    public int getDefense() {
        int flatChange = 0;
        double percentChange = 0;

        // Add flat wearable effects
        if (hat != null) {
            flatChange += hat.getStats().getFlatDefenseChange();
            percentChange += hat.getStats().getPercentDefenseChange();
        }
        if (accessory != null) {
            flatChange += accessory.getStats().getFlatDefenseChange();
            percentChange += accessory.getStats().getPercentDefenseChange();
        }
        if (clothes != null) {
            flatChange += clothes.getStats().getFlatDefenseChange();
            percentChange += clothes.getStats().getPercentDefenseChange();

            if(hasMagicEffect(MagicEffect.CLOTHES_BOOST)) {
                flatChange += clothes.getStats().getFlatDefenseChange();
            }
        }
        if (shoes != null) {
            flatChange += shoes.getStats().getFlatDefenseChange();
            percentChange += shoes.getStats().getPercentDefenseChange();
        }
        if (weapon != null) {
            flatChange += weapon.getStats().getFlatDefenseChange();
            percentChange += weapon.getStats().getPercentDefenseChange();
        }

        // Add active effects
        for (Triple<TempStatusModifier, String, Boolean> effect: activeEffects) {
            flatChange += effect.getFirst().getFlatDefenseChange();
            percentChange += effect.getFirst().getPercentDefenseChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseDefense, flatChange);
        percentChange = max(-1, percentChange);

        double defense = (baseDefense + flatChange) * (1 + percentChange);
        return (int) defense;
    }

    /**
     * Adds to this player's experience amount by xp, but modified by the player's items and status effects
     * @param xp
     */
    public void addExperience(int xp){
        int flatChange = 0;
        double percentChange = 0;

        if (hat != null) {
            flatChange += hat.getStats().getFlatExperienceChange();
            percentChange += hat.getStats().getPercentExperienceChange();
        }
        if (accessory != null) {
            flatChange += accessory.getStats().getFlatExperienceChange();
            percentChange += accessory.getStats().getFlatExperienceChange();
        }
        if (clothes != null) {
            flatChange += clothes.getStats().getFlatExperienceChange();
            percentChange += clothes.getStats().getPercentExperienceChange();
        }
        if (shoes != null) {
            flatChange += shoes.getStats().getFlatExperienceChange();
            percentChange += shoes.getStats().getPercentExperienceChange();
        }
        if (weapon != null) {
            flatChange += weapon.getStats().getFlatExperienceChange();
            percentChange += weapon.getStats().getPercentExperienceChange();
        }


        // Add active effects
        for (Triple<TempStatusModifier, String, Boolean> effect: activeEffects) {
            flatChange += effect.getFirst().getFlatExperienceChange();
            percentChange += effect.getFirst().getPercentExperienceChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-xp, flatChange);
        percentChange = max(-1, percentChange);
        experience += (int)((xp + flatChange) * (1 + percentChange));
        updateLevel();
    }

    // Equip Item and Helper Functions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Exchanges an item in the Player's inventory with an equipped.
     *
     * @param index the index of the inventory which contains the item to be equipped.
     * @return the equipped item if successful, null otherwise
     */
    public Class<? extends Item> equipItem(int index) {
        Item itemToEquip;
        if (index < 0 || index >= inventory.size()) {
            return null;
        }
        if (inventory.get(index) != null) {
            itemToEquip = inventory.remove(index);
        } else {
            return null;
        }
        if (itemToEquip instanceof Hat) {
            return equipHat((Hat)itemToEquip) ? Hat.class : null ;
        } else if (itemToEquip instanceof Accessory) {
            return equipAccessory((Accessory) itemToEquip) ? Clothes.class : null;
        } else if (itemToEquip instanceof Clothes) {
            return equipClothes((Clothes)itemToEquip) ? Clothes.class : null;
        } else if (itemToEquip instanceof Shoes) {
            return equipShoes((Shoes)itemToEquip) ? Shoes.class : null;
        } else if (itemToEquip instanceof Weapon) {
            return equipWeapon((Weapon)itemToEquip) ? Weapon.class : null;
        } else if (itemToEquip instanceof Consumable) {
            return useConsumable((Consumable)itemToEquip) ? Consumable.class : null;
        }
        return null;
    }

    /**
     * Exchanges hat in Player parameters with hat in inventory
     *
     * @param hatToEquip the hat which will replace the currently equipped hat
     * @return true if hat was successfully equipped
     */
    private boolean equipHat(Hat hatToEquip) {
        Hat temp = hat;
        hat = hatToEquip;
        this.pickupItem(temp);
        return true;
    }

    /**
     * Exchanges accessory in Player parameters with accessory in inventory
     *
     * @param accessoryToEquip the accessory which will replace the currently equipped accessory
     * @return true if accessory was successfully equipped
     */
    private boolean equipAccessory(Accessory accessoryToEquip) {
        Accessory temp = accessory;
        accessory = accessoryToEquip;
        this.pickupItem(temp);
        return true;
    }

    /**
     * Exchanges clothes in Player parameters with clothes in inventory
     *
     * @param clothesToEquip the clothes which will replace the currently equipped clothes
     * @return true if clothes were successfully equipped
     */
    private boolean equipClothes(Clothes clothesToEquip) {
        Clothes temp = clothes;
        clothes = clothesToEquip;
        this.pickupItem(temp);
        return true;
    }

    /**
     * Exchanges shoes in Player parameters with shoes in inventory
     *
     * @param shoesToEquip the shoes which will replace the currently equipped shoes
     * @return true if shoes were successfully equipped
     */
    private boolean equipShoes(Shoes shoesToEquip) {
        Shoes temp = shoes;
        shoes = shoesToEquip;
        this.pickupItem(temp);
        return true;
    }

    /**
     * Exchanges weapon in Player parameters with weapon in inventory
     *
     * @param weaponToEquip the weapon which will replace the currently equipped weapon
     * @return true if weapon was successfully equipped
     */
    private boolean equipWeapon(Weapon weaponToEquip) {
        Weapon temp = weapon;
        weapon = weaponToEquip;
        this.pickupItem(temp);
        return true;
    }

    /**
     * Removes one stack of the consumable and applies its statusModifier to the player.
     * Also deletes the consumable if it has no stacks remaining.
     *
     * @param consumableToConsume the consumable which will be consumed
     */
    private boolean useConsumable(Consumable consumableToConsume) {
        int stacks = consumableToConsume.getStacks();
        TempStatusModifier effect = new TempStatusModifier(consumableToConsume.getEffect());

        //checks for LINGERING_POTIONS hat effect and doubles the duration if detected.
        if(this.hasMagicEffect(MagicEffect.LINGERING_POTIONS)) {
            effect.setTurnsLeft(2 * effect.getTurnsLeft());
        }
        applyEffect(this.getName(), true, effect);

        //deletes the used consumable if there are no stacks left after use, otherwise decrements the stacks remaining.
        if(stacks == 1) {
        } else {
            // Decrease stacks and add it back
            consumableToConsume.setStacks(stacks - 1);
            pickupItem(consumableToConsume);
        }
        return true;
    }

    /**
     * @return index of first null inventory space, -1 if none
     */
    public int getFreeInventoryIndex() {
        if(inventory.size() < INVENTORY_SIZE) {
            return inventory.size();
        }
        return -1;
    }

    /**
     * Gets all of the necessary player stats for sending to Infra
     * See https://github.com/jackducham/mm26-engine/issues/107
     * @return PlayerStats protobuf object representing all of the player stats to send to Infra
     */
    public CharacterProtos.PlayerStats getPlayerStats() {
        return CharacterProtos.PlayerStats.newBuilder()
                .setLevel(this.getLevel())
                .setExperience(this.getExperience())
                .setAttack(this.getAttack())
                .setDefense(this.getDefense())
                .setCurrentHealth(this.getCurrentHealth())
                .setMaxHealth(this.getMaxHealth())
                .setMonstersSlain(playerStats.getMonstersSlain())
                .setDeathCount(playerStats.getDeathCount())
                .setTurnsSinceJoined(playerStats.getTurnsSinceJoined())
                .build();
    }

    public void setPlayerStats(CharacterProtos.PlayerStats statsProto){
        playerStats = new Stats(statsProto);
    }

    /**
     * Gets the stats object within this Player to update any extra stats.
     * @return a Stats object (Player.Stats)
     */
    public Stats getExtraStats() {
        return playerStats;
    }

    /**
     * Update death count for the player by overriding updateDeathState and making
     * sure that the player <b>just</b> died.
     * @param gameState gameState to call Character.updateDeathState() with
     */
    @Override
    public void updateDeathState(GameState gameState) {
        super.updateDeathState(gameState);
        if (ticksSinceDeath == 0) {
            playerStats.incrementDeathCount();
        }
    }

    /**
     * Class of <b>extra</b> attributes that are required for infra's player
     * stat calculation
     */
    static class Stats {
        private int monstersSlain;
        private int deathCount;
        private int turnsSinceJoined;

        public Stats(){
            monstersSlain = 0;
            deathCount = 0;
            turnsSinceJoined = 0;
        }

        public Stats(CharacterProtos.PlayerStats stats){
            monstersSlain = stats.getMonstersSlain();
            deathCount = stats.getDeathCount();
            turnsSinceJoined = stats.getTurnsSinceJoined();
        }

        public void incrementMonstersSlain() {
            monstersSlain++;
        }

        public void incrementDeathCount() {
            deathCount++;
        }

        public void incrementTurnsSinceJoined() {
            turnsSinceJoined++;
        }

        public int getMonstersSlain() {
            return monstersSlain;
        }

        public int getDeathCount() {
            return deathCount;
        }

        public int getTurnsSinceJoined() {
            return turnsSinceJoined;
        }
    }

}
