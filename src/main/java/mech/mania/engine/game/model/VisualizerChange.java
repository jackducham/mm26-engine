package mech.mania.engine.game.model;

import mech.mania.engine.game.characters.Character;
import mech.mania.engine.game.characters.*;

import mech.mania.engine.server.communication.visualizer.model.VisualizerProtos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VisualizerChange {
    private List<String> newPlayerNames;
    private Map<String, CharacterChange> characterChanges;
    private Map<String, Character> allCharacters;

    public VisualizerChange() {
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
            builder.setRevived(revived);
            builder.setDecisionType(decision);
            builder.setPath(path);

            return builder.build();
        }
    }

    public VisualizerProtos.VisualizerChange buildProtoClass() {
        VisualizerProtos.VisualizerChange.Builder builder = VisualizerProtos.VisualizerChange.newBuilder();
        builder.putAllNewPlayerNames(newPlayerNames);

        for (String name : characterChanges.keySet()) {
            builder.putCharacterChange(name, characterChanges.get(name).buildProtoClass());
        }

        return builder.build();
    }

}
