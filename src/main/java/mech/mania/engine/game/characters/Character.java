package mech.mania.engine.game.characters;
import mech.mania.engine.game.GameState;
import mech.mania.engine.game.GameLogic;
import mech.mania.engine.game.board.Board;
import mech.mania.engine.game.items.Weapon;
import mech.mania.engine.game.items.TempStatusModifier;
import java.util.List;


public abstract class Character {
    protected double currentHealth;
    protected int experience;
    protected Position position;
    protected Weapon weapon;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getLevel() {
        // TODO: experience formula
        return 0;
    }

    public double getPercentExpToNextLevel(){
        // TODO: experience formula
        return 0.0;
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
}
