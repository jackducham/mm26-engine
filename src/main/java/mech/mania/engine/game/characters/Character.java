package mech.mania.engine.game.characters;

import static java.lang.Math.exp;
import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mech.mania.engine.game.GameState;
import mech.mania.engine.game.items.TempStatusModifier;
import mech.mania.engine.game.items.Weapon;

import java.util.List;

public abstract class Character {
    private static final int reviveTicks = 1;

    protected double currentHealth;
    protected int experience; // XP reward on death (monster & player) AND XP gained by player
    protected Position position;
    protected Position spawnPoint;
    protected Weapon weapon;
    List<TempStatusModifier> activeEffects;
    protected Map<Player, Double> taggedPlayersDamage;
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
        // TODO: remove cast once health has been changed to int
        characterBuilder.setCurrentHealth((int)currentHealth);
        characterBuilder.setExperience(experience);
        characterBuilder.setPosition(position.buildProtoClass());
        characterBuilder.setSpawnPoint(spawnPoint.buildProtoClass());
        characterBuilder.setWeapon(weapon.buildProtoClassWeapon());

        for (int i = 0; i < activeEffects.size(); i++) {
            characterBuilder.setActiveEffects(i, activeEffects.get(i).buildProtoClassTemp());
        }

        // TODO: add players/player names

        characterBuilder.setTicksSinceDeath(ticksSinceDeath);
        characterBuilder.setName(name);
        characterBuilder.setIsDead(isDead);

        return characterBuilder.build();
    }

    public void takeDamage(double physicalDamage, double magicalDamage, Player player) {
        double actualDamage = max(0, physicalDamage - getPhysicalDefense())
            + max(0, magicalDamage - getMagicalDefense());

        if (taggedPlayersDamage.containsKey(player)) {
            taggedPlayersDamage.put(player, taggedPlayersDamage.get(player) + actualDamage);
        } else {
            taggedPlayersDamage.put(player, actualDamage);
        }

        updateCurrentHealth(-actualDamage);
    }

    protected void distributeRewards(GameState gameState) {
        for (Player player : taggedPlayersDamage.keySet()) {
            player.experience += this.getLevel();
        }
    }

    public void removePlayer(Player toRemove) {
        taggedPlayersDamage.remove(toRemove);
    }
    public void updateCurrentHealth(double delta) {
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
    public double getCurrentHealth() {
        return currentHealth;
    }

    public int getLevel() {
        // TODO: experience formula
        return 0;
    }

    public double getPercentExpToNextLevel() {
        // TODO: experience formula
        return 0.0;
    }

    public String getName() {
        return name;
    }

    public int getTotalExperience(){
        return experience;
    }

    /* Stat getter methods */
    static final double baseMaxHealth = 0;
    static final double maxHealthScaling = 0;
    public double getMaxHealth() {
        return baseMaxHealth + getLevel()*maxHealthScaling;
    }

    static final double baseSpeed = 0;
    static final double speedScaling = 0;
    public double getSpeed() {
        return baseSpeed + getLevel()*speedScaling;
    }

    static final double basePhysicalDamage = 0;
    static final double physicalDamageScaling = 0;
    public double getPhysicalDamage() {
        return basePhysicalDamage + getLevel()*physicalDamageScaling;
    }

    static final double baseMagicalDamage = 0;
    static final double magicalDamageScaling = 0;
    public double getMagicalDamage() {
        return baseMagicalDamage + getLevel()*magicalDamageScaling;
    }

    static final double basePhysicalDefense = 0;
    static final double physicalDefenseScaling = 0;
    public double getPhysicalDefense() {
        return basePhysicalDefense + getLevel()*physicalDefenseScaling;
    }

    static final double baseMagicalDefense = 0;
    static final double magicalDefenseScaling = 0;
    public double getMagicalDefense() {
        return baseMagicalDefense + getLevel()*magicalDefenseScaling;
    }

    public List<TempStatusModifier> getActiveEffects() {
        return activeEffects;
    }

    public void addEffect(TempStatusModifier modifier) {
        activeEffects.add(modifier);
    }
}
