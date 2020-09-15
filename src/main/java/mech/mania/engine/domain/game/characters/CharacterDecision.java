package mech.mania.engine.domain.game.characters;

import mech.mania.engine.domain.model.CharacterProtos;

public class CharacterDecision {
    public enum decisionTypes {
        NONE, MOVE, ATTACK, EQUIP, DROP, PICKUP, PORTAL
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

    public CharacterDecision(CharacterProtos.CharacterDecision characterDecision) {
        switch(characterDecision.getDecisionType()) {
            case NONE:
                decision = decisionTypes.NONE;
                break;
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

        actionPosition = new Position(characterDecision.getTargetPosition());
        index = characterDecision.getIndex();
    }

    public CharacterProtos.CharacterDecision buildProtoClassCharacterDecision(){
        CharacterProtos.CharacterDecision.Builder decisionBuilder = CharacterProtos.CharacterDecision.newBuilder();
        switch(this.decision) {
            case NONE:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.NONE);
                break;
            case MOVE:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.MOVE);
                break;
            case ATTACK:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.ATTACK);
                break;
            case EQUIP:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.EQUIP);
                break;
            case DROP:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.DROP);
                break;
            case PICKUP:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.PICKUP);
                break;
            case PORTAL:
                decisionBuilder.setDecisionType(CharacterProtos.DecisionType.PORTAL);
                break;
        }

        decisionBuilder.setIndex(this.index);
        decisionBuilder.setTargetPosition(this.actionPosition.buildProtoClass());

        return decisionBuilder.build();
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
