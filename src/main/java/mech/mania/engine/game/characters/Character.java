package mech.mania.engine.game.characters;
<<<<<<< HEAD
import mech.mania.engine.game.GameState;
import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.board.Board;
=======

import static java.lang.Math.max;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import mech.mania.engine.game.GameState;
import mech.mania.engine.game.items.TempStatusModifier;
>>>>>>> engine-outline
import mech.mania.engine.game.items.Weapon;
import mech.mania.engine.game.items.TempStatusModifier;
import java.util.List;


public abstract class Character {
    private static final int reviveTicks = 0;

    protected double currentHealth;
    protected int experience; // XP reward on death (monster & player) AND XP gained by player
    protected Position position;
    protected Position spawnPoint;
    protected Weapon weapon;
<<<<<<< HEAD
=======
    List<TempStatusModifier> activeEffects;
    protected Map<Player, Double> taggedPlayersDamage;
    private boolean isDead;
    private int ticksSinceDeath;
    private String name;


    protected Character(int experience, Position spawnPoint, Weapon weapon, String name) {
        this.currentHealth = getMaxHealth();
        this.experience = experience;
        this.position = spawnPoint;
        this.spawnPoint = spawnPoint;
        this.name = name;
        this.weapon = weapon;
        this.activeEffects = new ArrayList<>();
        this.taggedPlayersDamage = new HashMap<>();
        this.isDead = false;
        this.ticksSinceDeath = -1;
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
        if (currentHealth <= 0) {
            isDead = true;
            ticksSinceDeath = 0;
        }
    }
>>>>>>> engine-outline

    protected void distributeRewards(GameState gameState) {
        for (Player player : taggedPlayersDamage.keySet()) {
            player.experience += this.experience;
        }
    }

    public void respawn() {
        position = spawnPoint;
        currentHealth = getMaxHealth();
        isDead = false;
        ticksSinceDeath = -1;
    }

    public void onDeath(GameState gameState) {
        isDead = true;
        ticksSinceDeath = 0;
        activeEffects.clear();
        distributeRewards(gameState);
        taggedPlayersDamage.clear();
    }

    public void updateDeathTicks() {
        ticksSinceDeath++;
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
        if (ticksSinceDeath == reviveTicks) {
            isDead = false;
            ticksSinceDeath = -1;
        }
        return isDead;
    }
    public Weapon getWeapon() {
        return weapon;
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

    /**
     * Updates the characters new position if it is valid, otherwise nothing happens
     * @param oldPosition position the character is currently at
     * @param newPosition position the character wants to move to, it is already verified
     */
    public void moveCharacter(Position oldPosition, Position newPosition) {
        if (GameLogic.validateMove(oldPosition, newPosition, this)) {
            setPosition(position);
        }
        // else nothing happens
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
