package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.model.PlayerProtos;

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

    public CharacterDecision(PlayerProtos.PlayerDecision playerProto) {
        switch(playerProto.getDecisionType()) {
            case MOVE:
                decision = decisionTypes.MOVE;
                break;
            case ATTACK:
                decision = decisionTypes.ATTACK;
                break;
            case EQUIP:
                decision = decisionTypes.EQUIP;
                break;
            case DROP:
                decision = decisionTypes.DROP;
                break;
            case PICKUP:
                decision = decisionTypes.PICKUP;
                break;
            case PORTAL:
                decision = decisionTypes.PORTAL;
                break;
        }
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
