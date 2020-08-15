package mech.mania.engine.domain.model;

import mech.mania.engine.domain.game.characters.Character;
import mech.mania.engine.domain.game.characters.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameChange {
    private List<String> newPlayerNames;
    private Map<String, CharacterChange> characterChanges;
    private Map<String, Character> allCharacters;

    public GameChange() {
        characterChanges = new HashMap<>();
        allCharacters = new HashMap<>();
        newPlayerNames = new ArrayList<>();
    }

    public void addCharacter(Character newCharacter) {
        allCharacters.put(newCharacter.getName(), newCharacter);
        newPlayerNames.add(newCharacter.getName());
    }

    public void clearChanges() {
        newPlayerNames.clear();
        characterChanges.clear();
    }

    public void updatePlayer(
            Character character,
            CharacterDecision decision,
            List<Position> path,
            boolean died,
            boolean revived) {
        if (!characterChanges.containsKey(character.getName())) {
            characterChanges.put(character.getName(), new CharacterChange());
        }

        CharacterChange change = characterChanges.get(character.getName());

        if (decision != null) {
            change.decision = decision.getDecision();
        }

        if (path != null) {
            change.path = path;
        }

        change.died = died;
        change.revived = revived;
    }

    private class CharacterChange {
        boolean died = false;
        boolean revived = false;
        CharacterDecision.decisionTypes decision = null;
        List<Position> path = null;


        public VisualizerProtos.CharacterChange buildProtoClass() {
            VisualizerProtos.CharacterChange.Builder builder = VisualizerProtos.CharacterChange.newBuilder();
            builder.setDied(died);
            builder.setRespawned(revived);

            switch (decision) {
                case MOVE:
                    builder.setDecisionType(CharacterProtos.DecisionType.MOVE);
                    break;
                case ATTACK:
                    builder.setDecisionType(CharacterProtos.DecisionType.ATTACK);
                    break;
                case PORTAL:
                    builder.setDecisionType(CharacterProtos.DecisionType.PORTAL);
                    break;
                case DROP:
                    builder.setDecisionType(CharacterProtos.DecisionType.DROP);
                    break;
                case EQUIP:
                    builder.setDecisionType(CharacterProtos.DecisionType.EQUIP);
                    break;
                case PICKUP:
                    builder.setDecisionType(CharacterProtos.DecisionType.PICKUP);
                    break;
                default:
                    builder.setDecisionType(CharacterProtos.DecisionType.NONE);
            }

            for (Position position : path) {
                builder.addPath(position.buildProtoClass());
            }

            return builder.build();
        }
    }

    public VisualizerProtos.GameChange buildProtoClass() {
        VisualizerProtos.GameChange.Builder builder = VisualizerProtos.GameChange.newBuilder();
        builder.addAllNewPlayerNames(newPlayerNames);

        for (String name : characterChanges.keySet()) {
            builder.putCharacterStatChanges(name, characterChanges.get(name).buildProtoClass());
        }

        return builder.build();
    }

}
