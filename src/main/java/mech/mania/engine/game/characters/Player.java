package mech.mania.engine.game.characters;

import mech.mania.engine.game.items.*;

import java.util.List;

public class Player extends Character {
    private static final int INVENTORY_SIZE = 16;
    private String name;
    private Hat hat;
    private Clothes clothes;
    private Shoes shoes;
    private Item[] inventory;
    List<TempStatusModifier> activeEffects;

    public Player(){
        experience = 0;
        hat = null;
        clothes = null;
        shoes = null;
        inventory = new Item[INVENTORY_SIZE];
    }

    public String getName() {
        return name;
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

    public Item[] getInventory() {
        return inventory;
    }

    public List<TempStatusModifier> getActiveEffects() {
        return activeEffects;
    }

    @Override
    public double getMaxHealth() {
        double maxHealth = super.getMaxHealth();

        // Add wearable effects
        if(hat != null){
            maxHealth += hat.getStats().getHealthChange();
        }
        if(clothes != null){
            maxHealth += clothes.getStats().getHealthChange();
        }
        if(shoes != null){
            maxHealth += shoes.getStats().getHealthChange();
        }

        // Add active effects
        for(TempStatusModifier mod : activeEffects){
            maxHealth += mod.getHealthChange();
        }

        return maxHealth;
    }

    @Override
    public double getSpeed() {
        double speed =  super.getSpeed();

        // Add wearable effects
        if(hat != null){
            speed += hat.getStats().getSpeedChange();
        }
        if(clothes != null){
            speed += clothes.getStats().getSpeedChange();
        }
        if(shoes != null){
            speed += shoes.getStats().getSpeedChange();
        }

        // Add active effects
        for(TempStatusModifier mod : activeEffects){
            speed += mod.getSpeedChange();
        }

        return speed;
    }

    @Override
    public double getPhysicalDamage() {
        double physicalDamage = super.getPhysicalDamage();

        // Add wearable effects
        if(hat != null){
            physicalDamage += hat.getStats().getPhysicalDamageChange();
        }
        if(clothes != null){
            physicalDamage += clothes.getStats().getPhysicalDamageChange();
        }
        if(shoes != null){
            physicalDamage += shoes.getStats().getPhysicalDamageChange();
        }

        // Add active effects
        for(TempStatusModifier mod : activeEffects){
            physicalDamage += mod.getPhysicalDamageChange();
        }

        return physicalDamage;
    }

    @Override
    public double getMagicalDamage() {
        double magicalDamage =  super.getMagicalDamage();

        // Add wearable effects
        if(hat != null){
            magicalDamage += hat.getStats().getMagicDamageChange();
        }
        if(clothes != null){
            magicalDamage += clothes.getStats().getMagicDamageChange();
        }
        if(shoes != null){
            magicalDamage += shoes.getStats().getMagicDamageChange();
        }

        // Add active effects
        for(TempStatusModifier mod : activeEffects){
            magicalDamage += mod.getMagicDamageChange();
        }

        return magicalDamage;
    }

    @Override
    public double getMagicalDefense() {
        double magicalDefense =  super.getMagicalDefense();

        // Add wearable effects
        if(hat != null){
            magicalDefense += hat.getStats().getMagicDefenseChange();
        }
        if(clothes != null){
            magicalDefense += clothes.getStats().getMagicDefenseChange();
        }
        if(shoes != null){
            magicalDefense += shoes.getStats().getMagicDefenseChange();
        }

        // Add active effects
        for(TempStatusModifier mod : activeEffects){
            magicalDefense += mod.getMagicDefenseChange();
        }

        return magicalDefense;
    }

    @Override
    public double getPhysicalDefense() {
        double physicalDefense = super.getPhysicalDefense();

        // Add wearable effects
        if(hat != null){
            physicalDefense += hat.getStats().getPhysicalDefenseChange();
        }
        if(clothes != null){
            physicalDefense += clothes.getStats().getPhysicalDefenseChange();
        }
        if(shoes != null){
            physicalDefense += shoes.getStats().getPhysicalDefenseChange();
        }

        // Add active effects
        for(TempStatusModifier mod : activeEffects){
            physicalDefense += mod.getPhysicalDefenseChange();
        }

        return physicalDefense;
    }

    /**
     * Exchanges weapon in Player parameters with weapon in inventory
     * @param weaponToEquip
     * @return true if weapon was successfully equipped
     */
    public boolean equipWeapon(Weapon weaponToEquip) {
        int index = hasWeapon(weaponToEquip);

        if (index == -1) {
            return false;
        }

        Weapon temp = this.weapon;
        this.weapon = weaponToEquip;
        inventory[index] = temp;

        return true;
    }

    /**
     * Checks whether player has weapon in inventory
     * @param weapon weapon to check
     * @return inventory index, or -1 if weapon isn't in inventory
     */
    public int hasWeapon(Weapon weapon) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == weapon) {
                return i;
            }
        }
        return -1;
    }
}
