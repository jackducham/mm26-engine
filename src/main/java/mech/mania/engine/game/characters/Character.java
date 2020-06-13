package mech.mania.engine.game.characters;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;

import mech.mania.engine.game.GameState;

import mech.mania.engine.game.items.StatusModifier;
import mech.mania.engine.game.items.TempStatusModifier;
import mech.mania.engine.game.items.Weapon;

public abstract class Character {
    private String name;

    /** Character's base stats */
    protected final int baseSpeed;
    protected final int baseMaxHealth;
    protected final int baseAttack;
    protected final int baseDefense;

    /** Character's ongoing stats */
    protected double currentHealth;
    protected int experience;

    /** Death parameters */
    private static final int reviveTicks = 1;
    private int ticksSinceDeath;
    private boolean isDead;

    /** Position parameters */
    protected Position position;
    protected Position spawnPoint;

    /** Attack/damage parameters */
    protected Weapon weapon;
    List<String> activeAttackers;

    // list of TempStatusModifiers for each Weapon, maps to String in currentAttackers
    List<TempStatusModifier> activeEffects;

    // map of attackers to amount of actual damage done
    protected Map<String, Double> taggedPlayersDamage;

    // cumulative effects from all currentEffects. These are reset every turn
    StatusModifier temporaryEffects;

    /**
     * Constructor for Characters
     */
    public Character(String name, int baseSpeed, int baseMaxHealth, int baseAttack, int baseDefense,
                     int experience, Position spawnPoint, Weapon weapon) {
        this.name = name;

        this.baseSpeed = baseSpeed;
        this.baseMaxHealth = baseMaxHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;

        this.currentHealth = baseMaxHealth;
        this.experience = experience;

        this.ticksSinceDeath = -1;
        this.isDead = false;

        this.position = spawnPoint;
        this.spawnPoint = spawnPoint;

        this.weapon = weapon;
        this.activeAttackers = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
        this.taggedPlayersDamage = new HashMap<>();
        this.temporaryEffects = new StatusModifier();

    }

    // @TODO: CharacterProtos need to be updated
    public CharacterProtos.Character buildProtoClassCharacter() {
        CharacterProtos.Character.Builder characterBuilder = CharacterProtos.Character.newBuilder();

        characterBuilder.setName(name);
        characterBuilder.setBaseSpeed(baseSpeed);
        characterBuilder.setBaseMaxHealth(baseMaxHealth);
        characterBuilder.setBaseAttack(baseAttack);
        characterBuilder.setBaseDefense(baseDefense);
        characterBuilder.setCurrentHealth(currentHealth);
        characterBuilder.setExperience(experience);

        characterBuilder.setTicksSinceDeath(ticksSinceDeath);
        characterBuilder.setIsDead(isDead);

        characterBuilder.setPosition(position.buildProtoClass());
        characterBuilder.setSpawnPoint(spawnPoint.buildProtoClass());
        characterBuilder.setWeapon(weapon.buildProtoClassWeapon());

        for (int i = 0; i < activeAttackers.size(); i++) {
//            characterBuilder.setCurrentAttackers(i, currentAttackers.get(i).buildProtoClassTemp());
        }

        for (int i = 0; i < activeEffects.size(); i++) {
            characterBuilder.setActiveEffects(i, activeEffects.get(i).buildProtoClassTemp());
        }

        characterBuilder.putAllTaggedPlayersDamage(taggedPlayersDamage);

        return characterBuilder.build();
    }

    /**
     * This function is called ONE TIME ONLY when a Character has been attacked
     * @param attacker name of the attacking player
     * @param weapon weapon the player attacked with
     * @param attackerATK the ATK of the attacker for calculating true attack damage
     */
    public void hitByWeapon(String attacker, Weapon weapon, int attackerATK) {
        if (weapon.getOnHitEffect() != null) {
            activeAttackers.add(attacker);
            activeEffects.add(weapon.getOnHitEffect());
        }
        double actualDamage = calculateActualDamage(attacker, weapon, attackerATK);
        applyDamage(attacker, actualDamage);
    }

    /**
     * This function calculates the damage that the attacker does to the victim
     * @param attacker name of the attacking player
     * @param weapon weapon the player attacked with
     * @param attackerATK the ATK of the attacker for calculating true attack damage
     */
    public double calculateActualDamage(String attacker, Weapon weapon, int attackerATK) {
        double attackDamage = weapon.getDamage() * (0.25 * attackerATK / 100);
        double minDamage = weapon.getDamage() * 0.20;

        double actualDamage = max(minDamage, attackDamage - getDefense());

        return actualDamage;
    }

    /**
     * Keeps track of the actual damage done per attacker and decreases health of victim
     * @param attacker Character name of attacker
     * @param actualDamage calculated damage done to health
     */
    public void applyDamage(String attacker, double actualDamage) {
        if (taggedPlayersDamage.containsKey(attacker)) {
            taggedPlayersDamage.put(attacker, taggedPlayersDamage.get(attacker) + actualDamage);
        } else {
            taggedPlayersDamage.put(attacker, actualDamage);
        }

        updateCurrentHealth(-actualDamage);
    }

    /**
     * Applies active effects and updates the death state
     * This should be called once a turn
     */
    public void updateCharacter(GameState gameState) {
        applyActiveEffects();
        updateDeathState(gameState);
    }

    /**
     * This function calculates the permanent damage per turn done by each weapon
     *      and compiles the temp effects of the Weapon
     */
    public void applyActiveEffects() {
        int i = 0;
        Iterator<TempStatusModifier> itr = activeEffects.iterator();
        while (itr.hasNext()) {
            TempStatusModifier effect = itr.next();
            if (effect.getDuration() <= 0) { // remove inactive effects
                itr.remove();
                activeAttackers.remove(i);
            } else {
                applyDamage(activeAttackers.get(i), effect.getDamagePerTurn());
                temporaryEffects.resetStatusModifier();

                temporaryEffects.addFlatSpeedChange(effect.getFlatSpeedChange());
                temporaryEffects.addFlatHealthChange(effect.getFlatHealthChange());
                temporaryEffects.addFlatExperienceChange(effect.getFlatExperienceChange());
                temporaryEffects.addFlatAttackChange(effect.getFlatAttackChange());
                temporaryEffects.addFlatDefenseChange(effect.getFlatDefenseChange());

                temporaryEffects.addPercentSpeedChange(effect.getPercentSpeedChange());
                temporaryEffects.addPercentHealthChange(effect.getPercentHealthChange());
                temporaryEffects.addPercentExperienceChange(effect.getPercentExperienceChange());
                temporaryEffects.addPercentAttackChange(effect.getPercentAttackChange());
                temporaryEffects.addPercentDefenseChange(effect.getPercentDefenseChange());

                // @TODO: is flatRegenPerTurn permanent current health change or temporary?
                // Currently implemented as a permanent health change
                updateCurrentHealth(temporaryEffects.getFlatRegenPerTurn());
            }
            effect.updateTurnsLeft();
            i++;
        }
    }

    /**
     * Check if Character is dead and revives them and distributes rewards accordingly
     * @param gameState gameState to give rewards to
     */
    public void updateDeathState(GameState gameState) {
        // player is already dead
        if (isDead) {
            ticksSinceDeath++;
            if (ticksSinceDeath == reviveTicks) {
                reviveCharacter();
            }
        } else if (getCurrentHealth() <= 0) { // player has just died
            ticksSinceDeath = 0;
            distributeRewards(gameState);
            isDead = true;
        }
    }

    /**
     * Reset Character parameters after death
     */
    protected void reviveCharacter() {
        position = spawnPoint;
        currentHealth = getMaxHealth();
        ticksSinceDeath = -1;
        activeAttackers.clear();
        activeEffects.clear();
        taggedPlayersDamage.clear();
        isDead = false;
    }

    /**
     * After Character dies, give actualDamage XP points to attacking Player
     * @param gameState
     */
    protected void distributeRewards(GameState gameState) {
        for (Map.Entry mapElement : taggedPlayersDamage.entrySet()) {
            String attackerName = (String) mapElement.getKey();
            Double damage = (Double) mapElement.getValue();

            Player player = gameState.getPlayer(attackerName);
            if (player != null) { // attacker is Monster
                player.experience += damage;
            }
        }
    }

    /**
     * @return name of the Character with most damage done to Character
     */
    protected String getPlayerWithMostDamage() {
        String highestDamagePlayer = null;
        double highestDamage = -1;
        for (String playerName : taggedPlayersDamage.keySet()) {
            if (taggedPlayersDamage.get(playerName) > highestDamage) {
                highestDamagePlayer = playerName;
                highestDamage = taggedPlayersDamage.get(playerName);
            }
        }

        return highestDamagePlayer;
    }


    // ============================= GETTERS AND SETTERS ================================================================== //

    public String getName() {
        return name;
    }

    // @TODO: Discuss double vs int
    // Since we have percent values, Java wants us to make everything a double
    public double getSpeed() {
        return (baseSpeed + temporaryEffects.getFlatSpeedChange()) * temporaryEffects.getPercentSpeedChange();
    }

    public double getMaxHealth() {
        return (baseMaxHealth + temporaryEffects.getFlatHealthChange()) * temporaryEffects.getPercentHealthChange();
    }

    public double getExperience() {
        return (experience + temporaryEffects.getFlatExperienceChange()) * temporaryEffects.getPercentExperienceChange();
    }

    public double getAttack() {
        return (baseAttack + temporaryEffects.getFlatAttackChange()) * temporaryEffects.getPercentAttackChange();
    }

    public double getDefense() {
        return (baseDefense + temporaryEffects.getFlatDefenseChange()) * temporaryEffects.getPercentDefenseChange();
    }

    public double getCurrentHealth() {
        return min(currentHealth, getMaxHealth());
    }

    public void updateCurrentHealth(double currentHealth) {
        this.currentHealth += currentHealth;
    }

    public int getLevel() {
        return (int) getExperience() % 10; // @TODO: Replace with actual level equation
    }

    public boolean isDead() {
        return isDead;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getSpawnPoint() {
        return spawnPoint;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void removePlayer(String toRemove) {
        taggedPlayersDamage.remove(toRemove);
    }
}