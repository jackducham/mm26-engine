package mech.mania.engine.game.characters;

import javafx.util.Pair;
import mech.mania.engine.game.items.*;

import java.util.List;

public class Player extends Character {
    private static final int INVENTORY_SIZE = 16;
    private String id;
    private Weapon weapon;
    private Hat hat;
    private Clothes clothes;
    private Shoes shoes;
    private Item[] inventory;
    List<TempStatusModifier> activeEffects;

    public Player(){
        experience = 0;
        weapon = null;
        hat = null;
        clothes = null;
        shoes = null;
        inventory = new Item[INVENTORY_SIZE];
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

    @Override
    public AttackPattern getAttackPattern() {
        return this.weapon.getAttackPattern();
    }


}
