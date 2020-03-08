package mech.mania.engine.game.characters;

import mech.mania.engine.game.characters.Position;

public class CharacterDecision {
    public enum decisionTypes {
        MOVE, ATTACK, EQUIP, DROP, PICKUP, PORTAL
    }
    private decisionTypes decision;
    private Position actionPosition;
    private int inventoryIndex;

    public CharacterDecision(decisionTypes decision, Position actionPosition) {
        this.decision = decision;
        this.actionPosition = actionPosition;
        inventoryIndex = -1;
    }

    public CharacterDecision(decisionTypes decision, Position actionPosition, int inventoryIndex) {
        this.decision = decision;
        this.actionPosition = actionPosition;
        this.inventoryIndex = inventoryIndex;
    }

    public decisionTypes getDecision() {
        return decision;
    }

    public Position getActionPosition() {
        return actionPosition;
    }

    public int getInventoryIndex() {
        return inventoryIndex;
    }

}
