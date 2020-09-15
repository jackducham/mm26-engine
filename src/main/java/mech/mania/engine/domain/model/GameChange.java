package mech.mania.engine.domain.model;

import mech.mania.engine.domain.game.characters.CharacterDecision;
import mech.mania.engine.domain.game.characters.Position;
import mech.mania.engine.domain.game.items.Clothes;
import mech.mania.engine.domain.game.items.Hat;
import mech.mania.engine.domain.game.items.Shoes;
import mech.mania.engine.domain.game.items.Weapon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameChange {
    private List<String> newPlayerNames;
    private List<String> deadPlayerNames;
    private Map<String, CharacterChange> characterChanges;
    private List<Position> tileTimeChanges;

    public GameChange() {
        characterChanges = new HashMap<>();
        deadPlayerNames = new ArrayList<>();
        newPlayerNames = new ArrayList<>();
        tileTimeChanges = new ArrayList<>();
    }

    public void addCharacter(String newCharacter) {
        newPlayerNames.add(newCharacter);
    }

    public void clearChanges() {
        newPlayerNames.clear();
        deadPlayerNames.clear();
        characterChanges.clear();
        tileTimeChanges.clear();
    }

    private void createUntrackedCharacter(String characterName) {
        if (!characterChanges.containsKey(characterName)) {
            characterChanges.put(characterName, new CharacterChange());
        }
    }

    public void addDeadCharacter(String characterName) {
        createUntrackedCharacter(characterName);
        deadPlayerNames.add(characterName);
    }

    public boolean wasDeadAtTurnStart(String characterName) {
        return deadPlayerNames.contains(characterName);
    }

    public void characterDied(String characterName) {
        createUntrackedCharacter(characterName);
        characterChanges.get(characterName).died = true;
    }

    public void characterRevived(String characterName) {
        createUntrackedCharacter(characterName);
        characterChanges.get(characterName).revived = true;
    }

    public void setCharacterDecision(String characterName, CharacterDecision decision) {
        createUntrackedCharacter(characterName);
        characterChanges.get(characterName).decision = decision;
    }

    public void setCharacterMovePath(String characterName, List<Position> path) {
        createUntrackedCharacter(characterName);
        characterChanges.get(characterName).path = path;
    }

    // 0: hat change, 1: clothes change, 2: shoes change, 3: weapon change
    public void characterEquip(String characterName, Class itemType) {
        createUntrackedCharacter(characterName);
        CharacterChange curChange = characterChanges.get(characterName);
        if (itemType == null) {
        } else if (itemType == Hat.class) {
            curChange.hatChanged = true;
        } else if (itemType == Clothes.class) {
            curChange.clothesChanged = true;
        } else if (itemType == Shoes.class) {
            curChange.shoesChanged = true;
        } else if (itemType == Weapon.class) {
            curChange.weaponChanged = true;
        }
    }

    public void characterAttackLocations(String characterName, List<Position> attackLocations) {
        createUntrackedCharacter(characterName);
        characterChanges.get(characterName).attackPositions = attackLocations;
    }

    private class CharacterChange {
        boolean died = false;
        boolean revived = false;
        CharacterDecision decision = null;
        List<Position> path = null;

        boolean hatChanged = false;
        boolean clothesChanged = false;
        boolean shoesChanged = false;
        boolean weaponChanged = false;

        List<Position> attackPositions = null;

        public VisualizerProtos.CharacterChange buildProtoClass() {
            VisualizerProtos.CharacterChange.Builder builder = VisualizerProtos.CharacterChange.newBuilder();
            builder.setDied(died);
            builder.setRespawned(revived);
            builder.setDecision(decision.buildProtoClassCharacterDecision());

            if(path != null){
                for (Position position : path) {
                    builder.addPath(position.buildProtoClass());
                }
            }

            builder.setHatChanged(hatChanged);
            builder.setClothesChanged(clothesChanged);
            builder.setShoesChanged(shoesChanged);
            builder.setWeaponChanged(weaponChanged);

            return builder.build();
        }
    }

    public VisualizerProtos.GameChange buildProtoClass() {
        VisualizerProtos.GameChange.Builder builder = VisualizerProtos.GameChange.newBuilder();
        builder.addAllNewPlayerNames(newPlayerNames);

        for (String name : characterChanges.keySet()) {
            builder.putCharacterChanges(name, characterChanges.get(name).buildProtoClass());
        }

        for (Position pos : tileTimeChanges) {
            builder.addTileTimeChanges(pos.buildProtoClass());
        }

        return builder.build();
    }

}
