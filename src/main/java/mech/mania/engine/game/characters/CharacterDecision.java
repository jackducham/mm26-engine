package mech.mania.engine.game.characters;

public class CharacterDecision {
    public enum decisionTypes {
        MOVE, ATTACK, EQUIP, DROP, PICKUP, PORTAL
    }
    private decisionTypes decision;
    private Position actionPosition;
    private int index;

    public CharacterDecision(decisionTypes decision, Position actionPosition) {
        this.decision = decision;
        this.actionPosition = actionPosition;
        index = -1;
    }

    public CharacterDecision(decisionTypes decision, Position actionPosition, int inventoryIndex) {
        this.decision = decision;
        this.actionPosition = actionPosition;
        this.index = inventoryIndex;
    }

    public decisionTypes getDecision() {
        return decision;
    }

    public Position getActionPosition() {
        return actionPosition;
    }

    public int getIndex() {
        return index;
    }

}
