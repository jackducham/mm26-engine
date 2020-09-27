package mech.mania.engine.domain.game.characters;

import kotlin.Triple;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.game.utils;
import mech.mania.engine.domain.model.CharacterProtos;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class Character {
    private final Logger LOGGER = Logger.getLogger(Character.class.getName());

    private String name;
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
    protected int ticksSinceDeath;  // need access in Player to determine whether player just died
    private boolean isDead;
    private int reviveTicks;

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
                     int level, Position spawnPoint, Weapon weapon, int reviveTicks) {
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
        this.reviveTicks = reviveTicks;

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
        this.level = character.getLevel();
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

        this.taggedPlayersDamage = new HashMap<>(character.getTaggedPlayersDamageMap());
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
        characterBuilder.setLevel(level);

        characterBuilder.setTicksSinceDeath(ticksSinceDeath);
        characterBuilder.setIsDead(isDead);

        characterBuilder.setPosition(position.buildProtoClass());
        characterBuilder.setSpawnPoint(spawnPoint.buildProtoClass());

        if(weapon != null) {
            characterBuilder.setWeapon(weapon.buildProtoClassWeapon());
        }

        for (int i = 0; i < activeEffects.size(); i++) {
            characterBuilder.addActiveEffectsTempStatusModifier(activeEffects.get(i).getFirst().buildProtoClassTemp());
            characterBuilder.addActiveEffectsSource(activeEffects.get(i).getSecond());
            characterBuilder.addActiveEffectsIsPlayer(activeEffects.get(i).getThird());
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
        double minDamage = max(1, weapon.getAttack() * 0.20);

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
        // character is already dead
        if (isDead) {
            ticksSinceDeath++;
            LOGGER.info(String.format("Character '%s' is dead. (ticksSinceDeath=%d, reviveTicks=%d)", getName(), ticksSinceDeath, reviveTicks));
            if (ticksSinceDeath >= reviveTicks) {
                LOGGER.info(String.format("Reviving character '%s'.", getName()));
                reviveCharacter();
            }
        } else if (getCurrentHealth() <= 0) { // character has just died
            LOGGER.info(String.format("Character '%s' just died.", getName()));
            ticksSinceDeath = 0;
            distributeRewards(gameState);
            removeFromEnemiesTPD(gameState);
            taggedPlayersDamage.clear();
            isDead = true;
        }
    }

    private void removeFromEnemiesTPD(GameState gameState) {
        for (Character character : gameState.getAllCharacters().values()) {
            if (!character.getName().equals(this.getName()) &&
                    character.getTaggedPlayersDamage().containsKey(this.getName())) {
                character.removePlayer(this.getName());
            }
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

            // player was just within aggrorange
            if (mapElement.getValue() == 0) {
                continue;
            }

            int attackingPlayerLevel = attackingPlayer.getLevel();
            int levelDiff = abs(attackingPlayerLevel  - this.getLevel());
            double expMultiplier = attackingPlayerLevel / (attackingPlayerLevel + (double)levelDiff);
            int expGain = (int)(10 * this.getLevel() * expMultiplier);
            attackingPlayer.addExperience(expGain);
        }
    }

    /**
     * @return sorted LinkedHashMap of players in order of damage done to this Character
     */
    protected LinkedHashMap<String, Integer> getPlayerWithMostDamage() {
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        taggedPlayersDamage.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }


    // ============================= GETTERS AND SETTERS ============================= //
    // For all getters: percent modifiers are de-buffs in (-1, 0) and buffs in (0, inf)
    // So actual = (base + flat) * (1 + percent) and all modifiers can be added together

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSprite() {
        return sprite;
    }

    public Map<String, Integer> getTaggedPlayersDamage() {
        return taggedPlayersDamage;
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

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void removePlayer(String toRemove) {
        taggedPlayersDamage.remove(toRemove);
    }
}