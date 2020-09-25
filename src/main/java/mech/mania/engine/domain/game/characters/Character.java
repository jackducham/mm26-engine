package mech.mania.engine.domain.game.characters;

import kotlin.Triple;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.model.CharacterProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character {
    private final String name;
    private final String sprite;

    /** Character's base stats */
    protected final int baseSpeed;
    protected final int baseMaxHealth;
    protected final int baseAttack;
    protected final int baseDefense;

    /** Character's ongoing stats */
    protected int currentHealth;
    protected int experience; // experience gained on that level
    protected int level;

    /** Death parameters */
    private static final int reviveTicks = 15;
    protected int ticksSinceDeath;  // need access in Player to determine whether player just died
    private boolean isDead;

    /** Position parameters */
    protected Position position;
    protected Position spawnPoint;

    /** Attack/damage parameters */
    protected Weapon weapon;

    // List of active status effects, their source Character, and an isPlayer flag
    List<Triple<TempStatusModifier, String, Boolean>> activeEffects;

    // map of attackers to amount of actual damage done
    protected Map<String, Integer> taggedPlayersDamage;

    /**
     * Constructor for Characters
     */
    public Character(String name, String sprite, int baseSpeed, int baseMaxHealth, int baseAttack, int baseDefense,
                     int level, Position spawnPoint, Weapon weapon) {
        this.name = name;
        this.sprite = sprite;

        this.baseSpeed = baseSpeed;
        this.baseMaxHealth = baseMaxHealth;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;

        this.currentHealth = baseMaxHealth;
        this.experience = 0;
        this.level = level;

        this.ticksSinceDeath = -1;
        this.isDead = false;

        this.position = spawnPoint;
        this.spawnPoint = spawnPoint;

        this.weapon = weapon;
        this.activeEffects = new ArrayList<>();
        this.taggedPlayersDamage = new HashMap<>();
    }

    /**
     * Constructor for Characters built from protos
     */
    public Character(CharacterProtos.Character character) {
        this.name = character.getName();
        this.sprite = character.getSprite();

        this.baseSpeed = character.getBaseSpeed();
        this.baseMaxHealth = character.getBaseMaxHealth();
        this.baseAttack = character.getBaseAttack();
        this.baseDefense = character.getBaseDefense();

        this.currentHealth = character.getCurrentHealth();
        this.experience = character.getExperience();

        this.ticksSinceDeath = character.getTicksSinceDeath();
        this.isDead = character.getIsDead();

        this.position = new Position(character.getPosition());
        this.spawnPoint = new Position(character.getSpawnPoint());

        this.weapon = new Weapon(character.getWeapon());

        // Build activeEffects triple
        this.activeEffects = new ArrayList<>();
        for(int i = 0; i < character.getActiveEffectsTempStatusModifierCount(); i++){
            activeEffects.add(new Triple<>(
                    new TempStatusModifier(character.getActiveEffectsTempStatusModifier(i)),
                    character.getActiveEffectsSource(i),
                    character.getActiveEffectsIsPlayer(i)
            ));
        }

        this.taggedPlayersDamage = character.getTaggedPlayersDamageMap();
    }

    public CharacterProtos.Character buildProtoClassCharacter() {
        CharacterProtos.Character.Builder characterBuilder = CharacterProtos.Character.newBuilder();

        characterBuilder.setName(name);
        characterBuilder.setSprite(sprite);
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

        for (int i = 0; i < activeEffects.size(); i++) {
            characterBuilder.setActiveEffectsTempStatusModifier(i, activeEffects.get(i).getFirst().buildProtoClassTemp());
            characterBuilder.setActiveEffectsSource(i, activeEffects.get(i).getSecond());
            characterBuilder.setActiveEffectsIsPlayer(i, activeEffects.get(i).getThird());
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
    public void hitByWeapon(String attacker, boolean isPlayer, Weapon weapon, int attackerATK) {
        if (weapon.getOnHitEffect() != null) {
            applyEffect(attacker, isPlayer, weapon.getOnHitEffect());
        }
        int actualDamage = (int) calculateActualDamage(weapon, attackerATK);
        applyDamage(attacker, isPlayer, actualDamage);
    }

    /**
     * Adds a new temporary status modifier to the Player's list of modifiers.
     *
     * @param sourcePlayer the name of the source player
     * @param effect the status which will be added to the Player's list
     * @return true if successful
     */
    public boolean applyEffect(String sourcePlayer, boolean isPlayer, TempStatusModifier effect) {
        if(effect == null || sourcePlayer == null) {
            return false;
        }

        // activeEffects and activeEffectsSources are parallel lists
        activeEffects.add(new Triple<>(effect, sourcePlayer, isPlayer));

        return true;
    }

    /**
     * This function calculates the damage that the attacker does to the victim
     * Formula from: https://github.com/jackducham/mm26-design/wiki/Your-Character
     * @param weapon weapon the player attacked with
     * @param attackerATK the ATK of the attacker for calculating true attack damage
     */
    public double calculateActualDamage(Weapon weapon, int attackerATK) {
        double attackDamage = weapon.getAttack() * (0.25 + attackerATK / 100.0);
        double minDamage = weapon.getAttack() * 0.20;

        return max(minDamage, attackDamage - getDefense());
    }

    /**
     * Keeps track of the actual damage done per attacker and decreases health of victim
     * @param attacker Character name of attacker
     * @param actualDamage calculated damage done to health
     */
    public void applyDamage(String attacker, boolean isPlayer, int actualDamage) {
        updateCurrentHealth(-actualDamage);

        // Only update taggedPlayersDamage for damage from players
        if(!isPlayer) return;

        if (taggedPlayersDamage.containsKey(attacker)) {
            taggedPlayersDamage.put(attacker, taggedPlayersDamage.get(attacker) + actualDamage);
        } else {
            taggedPlayersDamage.put(attacker, actualDamage);
        }
    }

    /**
     * Applies active effects and updates the Character level and death state
     * This should be called once a turn
     */
    public void updateCharacter(GameState gameState) {
        updateActiveEffects();
        updateLevel();
        updateDeathState(gameState);
    }

    /**
     * This function updates the activeEffects turns left and removes inactive effects
     * It also calculates and applies the permanent damage per turn done by each weapon
     */
    public void updateActiveEffects() {
        for(int i = 0; i < activeEffects.size(); i++){
            TempStatusModifier effect = activeEffects.get(i).getFirst();
            String source = activeEffects.get(i).getSecond();
            Boolean isPlayer = activeEffects.get(i).getThird();

            if (effect.getTurnsLeft() <= 0) { // remove inactive effects
                activeEffects.remove(i);
                i--; // Don't skip next effect on removal!
            } else {
                // applies change to currentHealth of Character
                // this can ONLY be called once per turn for correct calculations
                // this also applies the raw damage intentionally
                effect.updateTurnsLeft();
                applyDamage(source, isPlayer, effect.getDamagePerTurn());
                updateCurrentHealth(effect.getFlatRegenPerTurn());
            }
        }
    }

    /**
     * This updates both the Character experience and level with the formula
     *      exp_to_level = 100 * level
     * Experience should NEVER be negative
     */
    public void updateLevel() {
        int expToNextLevel = 100 * level;
        while (experience >= expToNextLevel) {
            experience -= expToNextLevel;
            level++;
            expToNextLevel = 100 * level;
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
            taggedPlayersDamage.clear();
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
        activeEffects.clear();
        taggedPlayersDamage.clear();
        isDead = false;
    }

    /**
     * After Character dies, give XP points to attacking Player according to formula
     * @param gameState The current gameState
     */
    public void distributeRewards(GameState gameState) {
        for (Map.Entry<String, Integer> mapElement : taggedPlayersDamage.entrySet()) {
            String attackerName = mapElement.getKey();

            Player attackingPlayer = gameState.getPlayer(attackerName);

            // if attacker is Monster or self, don't give rewards
            if (attackingPlayer == null || attackingPlayer == this) {
                continue;
            }

            if (attackingPlayer.isDead()) {
                continue;
            }

            int attackingPlayerLevel = attackingPlayer.getLevel();
            int levelDiff = abs(attackingPlayerLevel  - this.getLevel());
            double expMultiplier = attackingPlayerLevel / (attackingPlayerLevel + (double)levelDiff);
            int expGain = (int)(10 * this.getLevel() * expMultiplier);
            attackingPlayer.addExperience(expGain);
            attackingPlayer.removePlayer(this.name);
        }
    }

    /**
     * @return name of the Player (NOT Monster) with most damage done to this Character
     */
    protected String getPlayerWithMostDamage() {
        String highestDamagePlayer = null;
        int highestDamage = -1;
        for (String name : taggedPlayersDamage.keySet()) {
            if (taggedPlayersDamage.get(name) > highestDamage) {
                highestDamagePlayer = name;
                highestDamage = taggedPlayersDamage.get(name);
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

    public String getSprite() {
        return sprite;
    }

    public int getSpeed() {
        int flatChange = 0;
        double percentChange = 0;
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

    public int getMaxHealth() {
        int flatChange = 0;
        double percentChange = 0;
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

    public int getExperience() {
        return experience;
    }

    public int getAttack() {
        int flatChange = 0;
        double percentChange = 0;
        for (Triple<TempStatusModifier, String, Boolean> effect: activeEffects) {
            flatChange += effect.getFirst().getFlatAttackChange();
            percentChange += effect.getFirst().getPercentAttackChange();
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

    public int getCurrentHealth() {
        currentHealth = min(currentHealth, getMaxHealth());
        return currentHealth;
    }

    public void updateCurrentHealth(int healthChange) {
        this.currentHealth += healthChange;
        this.currentHealth = min(this.currentHealth, this.getMaxHealth());
    }

    public int getLevel() {
        return level;
    }

    public int getTotalExperience() {
        return getLevel() * (getLevel() - 1) * 100 / 2 + getExperience();
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