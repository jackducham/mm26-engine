package mech.mania.engine.game.characters;

import mech.mania.engine.game.board.Position;

public abstract class Character {
    private double health;
    private int level;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    /* Stat getter methods */
    public double getMaxHealth() {
        // TODO: Level formula
        return 0;
    }

    public double getSpeed() {
        // TODO: Level formula
        return 0;
    }

    public double getPhysicalDamage() {
        // TODO: Level formula
        return 0;
    }

    public double getMagicalDamage() {
        // TODO: Level formula
        return 0;
    }

    public double getPhysicalDefense() {
        // TODO: Level formula
        return 0;
    }

    public double getMagicalDefense() {
        // TODO: Level formula
        return 0;
    }

    public abstract double[][] getAttackPattern();
}
