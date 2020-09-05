package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.model.CharacterProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character {
    private String name;

    /** Character's base stats */
    protected final int baseSpeed;
    protected final int baseMaxHealth;
    protected final int baseAttack;
    protected final int baseDefense;

    /** Character's ongoing stats */
    protected int currentHealth;
    protected int experience;

    /** Death parameters */
    private static final int reviveTicks = 1;
    protected int ticksSinceDeath;  // need access in Player to determine whether player just died
    private boolean isDead;

    /** Position parameters */
    protected Position position;
    protected Position spawnPoint;

    /** Attack/damage parameters */
    protected Weapon weapon;
    List<String> activeEffectsSources;

    // list of TempStatusModifiers for each Weapon, maps to String in currentAttackers
    List<TempStatusModifier> activeEffects;

    // map of attackers to amount of actual damage done
    protected Map<String, Integer> taggedPlayersDamage;

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
        this.activeEffectsSources = new ArrayList<>();
        this.activeEffects = new ArrayList<>();
        this.taggedPlayersDamage = new HashMap<>();
    }

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

        if(weapon != null) {
            characterBuilder.setWeapon(weapon.buildProtoClassWeapon());
        }

        for (int i = 0; i < activeEffectsSources.size(); i++) {
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
            applyEffect(weapon.getOnHitEffect(), attacker);
        }
        int actualDamage = (int) calculateActualDamage(attacker, weapon, attackerATK);
        applyDamage(attacker, actualDamage);
    }

    /**
     * Adds a new temporary status modifier to the Player's list of modifiers.
     *
     * @param effect the status which will be added to the Player's list
     * @param sourcePlayer the name of the source player
     * @return true if successful
     */
    public boolean applyEffect(TempStatusModifier effect, String sourcePlayer) {
        if(effect == null || sourcePlayer == null) {
            return false;
        }

        // activeEffects and activeEffectsSources are parallel lists
        activeEffects.add(effect);
        activeEffectsSources.add(sourcePlayer);

        return true;
    }

    /**
     * This function calculates the damage that the attacker does to the victim
     * Formula from: https://github.com/jackducham/mm26-design/wiki/Your-Character
     * @param attacker name of the attacking player
     * @param weapon weapon the player attacked with
     * @param attackerATK the ATK of the attacker for calculating true attack damage
     */
    public double calculateActualDamage(String attacker, Weapon weapon, int attackerATK) {
        double attackDamage = weapon.getAttack() * (0.25 + attackerATK / 100.);
        double minDamage = weapon.getAttack() * 0.20;

        double actualDamage = max(minDamage, attackDamage - getDefense());

        return actualDamage;
    }

    /**
     * Keeps track of the actual damage done per attacker and decreases health of victim
     * @param attacker Character name of attacker
     * @param actualDamage calculated damage done to health
     */
    public void applyDamage(String attacker, int actualDamage) {
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
        updateActiveEffects();
        updateDeathState(gameState);
    }

    /**
     * This function updates the activeEffects turns left and removes inactive effects
     * It also calculates and applies the permanent damage per turn done by each weapon
     */
    public void updateActiveEffects() {
        for(int i = 0; i < activeEffects.size(); i++){
            TempStatusModifier effect = activeEffects.get(i);

            // applies change to currentHealth of Character
            // this can ONLY be called once per turn for correct calculations
            // this also applies the raw damage intentionally
            applyDamage(activeEffectsSources.get(i), effect.getDamagePerTurn());
            updateCurrentHealth(effect.getFlatRegenPerTurn());
            effect.updateTurnsLeft();

            if (effect.getTurnsLeft() <= 0) { // remove inactive effects
                activeEffects.remove(i);
                activeEffectsSources.remove(i);
                i--; // Don't skip next effect on removal!
            }
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
        activeEffectsSources.clear();
        activeEffects.clear();
        taggedPlayersDamage.clear();
        isDead = false;
    }

    /**
     * After Character dies, give actualDamage XP points to attacking Player
     * @param gameState
     */
    protected void distributeRewards(GameState gameState) {
        for (Map.Entry<String, Integer> mapElement : taggedPlayersDamage.entrySet()) {
            String attackerName = mapElement.getKey();
            Integer damage = mapElement.getValue();

            Player player = gameState.getPlayer(attackerName);

            // Don't reward if tagged character is Monster or is self
            if (player != null && player != this) {
                player.addExperience(damage);
            }
        }
    }

    /**
     * @return name of the Character with most damage done to Character
     */
    protected String getPlayerWithMostDamage() {
        String highestDamagePlayer = null;
        int highestDamage = -1;
        for (String playerName : taggedPlayersDamage.keySet()) {
            if (taggedPlayersDamage.get(playerName) > highestDamage) {
                highestDamagePlayer = playerName;
                highestDamage = taggedPlayersDamage.get(playerName);
            }
        }

        return highestDamagePlayer;
    }


    // ============================= GETTERS AND SETTERS ============================= //
    // For all getters: percent modifiers are de-buffs in (-1, 0) and buffs in (0, inf)
    // So actual = (base + flat) * (1 + percent) and all modifiers can be added together

    public String getName() {
        return name;
    }

    public int getSpeed() {
        int flatChange = 0;
        double percentChange = 0;
        for (TempStatusModifier effect: activeEffects) {
            flatChange += effect.getFlatSpeedChange();
            percentChange += effect.getPercentSpeedChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseSpeed, flatChange);
        percentChange = max(-1, percentChange);

        double speed = (baseSpeed + flatChange) * (1 + percentChange);
        return max(1, (int) speed); // speed can't be below 1
    }

    public int getMaxHealth() {
        int flatChange = 0;
        double percentChange = 0;
        for (TempStatusModifier effect: activeEffects) {
            flatChange += effect.getFlatHealthChange();
            percentChange += effect.getPercentHealthChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseMaxHealth, flatChange);
        percentChange = max(-1, percentChange);

        double maxHealth = (baseMaxHealth + flatChange) * (1 + percentChange);
        return max(1, (int) maxHealth); // maxHealth can't be below 1
    }

    public int getExperience() {
        return experience;
    }

    public int getAttack() {
        int flatChange = 0;
        double percentChange = 0;
        for (TempStatusModifier effect: activeEffects) {
            flatChange += effect.getFlatAttackChange();
            percentChange += effect.getPercentAttackChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseAttack, flatChange);
        percentChange = max(-1, percentChange);

        double attack = (baseAttack + flatChange) * (1 + percentChange);
        return max(1, (int) attack); // attack can't be below 1
    }

    public int getDefense() {
        int flatChange = 0;
        double percentChange = 0;
        for (TempStatusModifier effect: activeEffects) {
            flatChange += effect.getFlatDefenseChange();
            percentChange += effect.getPercentDefenseChange();
        }

        // Make sure stat can't be negative
        flatChange = max(-baseDefense, flatChange);
        percentChange = max(-1, percentChange);

        double defense = (baseDefense + flatChange) * (1 + percentChange);
        return (int) defense;
    }

    public int getCurrentHealth() {
        currentHealth = min(currentHealth, getMaxHealth());
        return currentHealth;
    }

    public void updateCurrentHealth(int healthChange) {
        this.currentHealth += healthChange;
        this.currentHealth = min(this.currentHealth, this.getMaxHealth());
    }

    public int getLevel() {
        return getExperience() % 10; // @TODO: Replace with actual level equation
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