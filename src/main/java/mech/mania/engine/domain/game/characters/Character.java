package mech.mania.engine.domain.game.characters;

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mech.mania.engine.domain.game.GameState;
import mech.mania.engine.domain.game.items.TempStatusModifier;
import mech.mania.engine.domain.game.items.Weapon;
import mech.mania.engine.domain.model.CharacterProtos;

import java.util.List;

public abstract class Character {
    private static final int reviveTicks = 1;

    protected int currentHealth;
    protected int experience; // XP reward on death (monster & player) AND XP gained by player
    protected Position position;
    protected Position spawnPoint;
    protected Weapon weapon;
    List<TempStatusModifier> activeEffects;
    protected Map<String, Integer> taggedPlayersDamage;
    private int ticksSinceDeath;
    private String name;
    private boolean isDead;


    protected Character(int experience, Position spawnPoint, Weapon weapon, String name) {
        this.currentHealth = getMaxHealth();
        this.experience = experience;
        this.position = spawnPoint;
        this.spawnPoint = spawnPoint;
        this.name = name;
        this.weapon = weapon;
        this.activeEffects = new ArrayList<>();
        this.taggedPlayersDamage = new HashMap<>();
        this.ticksSinceDeath = -1;
    }

    public CharacterProtos.Character buildProtoClassCharacter() {
        CharacterProtos.Character.Builder characterBuilder = CharacterProtos.Character.newBuilder();
        characterBuilder.setCurrentHealth(currentHealth);
        characterBuilder.setExperience(experience);
        characterBuilder.setPosition(position.buildProtoClass());
        characterBuilder.setSpawnPoint(spawnPoint.buildProtoClass());
        characterBuilder.setWeapon(weapon.buildProtoClassWeapon());

        for (int i = 0; i < activeEffects.size(); i++) {
            characterBuilder.setActiveEffects(i, activeEffects.get(i).buildProtoClassTemp());
        }

        characterBuilder.putAllTaggedPlayersDamage(taggedPlayersDamage);

        characterBuilder.setTicksSinceDeath(ticksSinceDeath);
        characterBuilder.setName(name);
        characterBuilder.setIsDead(isDead);

        return characterBuilder.build();
    }

    public void takeDamage(int physicalDamage, int magicalDamage, String playerName) {
        int actualDamage = max(0, physicalDamage - getPhysicalDefense())
            + max(0, magicalDamage - getMagicalDefense());

        if (taggedPlayersDamage.containsKey(playerName)) {
            taggedPlayersDamage.put(playerName, taggedPlayersDamage.get(playerName) + actualDamage);
        } else {
            taggedPlayersDamage.put(playerName, actualDamage);
        }

        updateCurrentHealth(-actualDamage);
    }

    protected void distributeRewards(GameState gameState) {
        for (String playerName : taggedPlayersDamage.keySet()) {
            Player player = gameState.getPlayer(playerName);
            player.experience += this.getLevel();
        }
    }

    public void removePlayer(String toRemove) {
        taggedPlayersDamage.remove(toRemove);
    }
    public void updateCurrentHealth(int delta) {
        currentHealth += delta;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isDead() {
        return isDead;
    }

    /**
     * This should be externally called at the end of the CharacterDecision loop.
     * @param gameState gameState to give rewards to
     */
    public void updateDeathState(GameState gameState) {
        // player is already dead
        if (isDead) {
            ticksSinceDeath++;
            if (ticksSinceDeath == reviveTicks) {
                // revive player
                position = spawnPoint;
                currentHealth = getMaxHealth();
                ticksSinceDeath = -1;
                isDead = false;
            }
        } else if (currentHealth <= 0) { // player has just died
            ticksSinceDeath = 0;
            activeEffects.clear();
            distributeRewards(gameState);
            taggedPlayersDamage.clear();
            isDead = true;
        }

    }

    public Weapon getWeapon() {
        return weapon;
    }
    public Position getSpawnPoint() {
        return spawnPoint;
    }
    public Position getPosition() {
        return position;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getLevel() {
        // TODO: experience formula
        return 0;
    }

    public int getExpToNextLevel() {
        // TODO: experience formula
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getTotalExperience(){
        return experience;
    }

    /* Stat getter methods */
    static final int baseMaxHealth = 0;
    static final int maxHealthScaling = 0;
    public int getMaxHealth() {
        return baseMaxHealth + getLevel()*maxHealthScaling;
    }

    static final int baseSpeed = 0;
    static final int speedScaling = 0;
    public int getSpeed() {
        return baseSpeed + getLevel()*speedScaling;
    }

    static final int basePhysicalDamage = 0;
    static final int physicalDamageScaling = 0;
    public int getPhysicalDamage() {
        return basePhysicalDamage + getLevel()*physicalDamageScaling;
    }

    static final int baseMagicalDamage = 0;
    static final int magicalDamageScaling = 0;
    public int getMagicalDamage() {
        return baseMagicalDamage + getLevel()*magicalDamageScaling;
    }

    static final int basePhysicalDefense = 0;
    static final int physicalDefenseScaling = 0;
    public int getPhysicalDefense() {
        return basePhysicalDefense + getLevel()*physicalDefenseScaling;
    }

    static final int baseMagicalDefense = 0;
    static final int magicalDefenseScaling = 0;
    public int getMagicalDefense() {
        return baseMagicalDefense + getLevel()*magicalDefenseScaling;
    }

    public List<TempStatusModifier> getActiveEffects() {
        return activeEffects;
    }

    public void addEffect(TempStatusModifier modifier) {
        activeEffects.add(modifier);
    }
}
