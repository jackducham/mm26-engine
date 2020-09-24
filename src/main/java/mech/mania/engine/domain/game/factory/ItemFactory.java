package mech.mania.engine.domain.game.factory;

import mech.mania.engine.domain.game.items.*;

import java.util.Random;
//TODO: Hat maker, accessory maker, add a return to everything besides weapons, add names, add name generation, add accessories, update wiki with items, change health values by a factor of 10, combine hat and accessory makers, just return a different kind
public class ItemFactory {
    public static Item generateItem(int level) {
        Random rand = new Random();
        //check that the provided level is not negative
        if(level < 0) {
            throw new IllegalArgumentException("Items cannot have negative levels");
        }

        int itemTypeDecider = rand.nextInt(5);

        if(itemTypeDecider == 0) { //~~~~~~~~~~~~~~~~~~~~~~~~~Weapon     Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            int weaponTypeDecider;
            if(level < 5) {
                weaponTypeDecider = 0;
            } else if (level < 20) {
                weaponTypeDecider = rand.nextInt(2);
            } else if (level < 30) {
                weaponTypeDecider = rand.nextInt(3);
            } else {
                weaponTypeDecider = rand.nextInt(4);
            }

            StatusModifier weaponStats;
            TempStatusModifier weaponOnHit;
            int weaponDamage;
            int weaponRange;
            int weaponSplash;
            int onHitAttributes;
            int attributes;

            if (weaponTypeDecider == 0) {//~~~~~~~~Sword    Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = rand.nextInt(2);
                if (level < 5) {
                    weaponDamage = 1 + rand.nextInt(9);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(20), 0, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 1 + rand.nextInt(2), 1);
                    attributes = 1;
                    for(int i = 0; i < 2 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(2);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else {
                            weaponStats.setFlatAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 3 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(3);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 20) {
                    weaponDamage = 1 + rand.nextInt(19);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(4), rand.nextDouble() * 0.1, 1 + rand.nextInt(40), 0, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1, 0, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(3)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(3));
                    attributes = 1;
                    for(int i = 0; i < 3 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(3);
                        if (attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else {
                            weaponStats.setPercentExperienceChange(0);
                        }
                    }
                    for(int i = 0; i < 3 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(3);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 30) {
                    weaponDamage = 1 + rand.nextInt(29);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(6), rand.nextDouble() * 0.1, 1 + rand.nextInt(60), rand.nextDouble() * 0.05, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), 0, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(6)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(4));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if (attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 3 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(3);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 40) {
                    weaponDamage = 1 + rand.nextInt(39);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(8), rand.nextDouble() * 0.15, 1 + rand.nextInt(80), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.2, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(8)), rand.nextDouble() * -0.1, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(6));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if (attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 5 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(5);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setDamagePerTurn(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else {
                            weaponOnHit.setPercentDefenseChange(0);
                        }
                    }
                } else if (level < 50) {
                    weaponDamage = 1 + rand.nextInt(49);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(10), rand.nextDouble() * 0.15, 1 + rand.nextInt(100), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.35, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(10)), rand.nextDouble() * -0.1, 0, 1 + rand.nextInt(3), 1 + rand.nextInt(9));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if (attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 5 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(5);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setDamagePerTurn(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else {
                            weaponOnHit.setPercentDefenseChange(0);
                        }
                    }
                } else if (level < 60) {
                    weaponDamage = 1 + rand.nextInt(59);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(14), rand.nextDouble() * 0.2, 1 + rand.nextInt(130), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.45, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(12)), rand.nextDouble() * -0.2, 0, 1 + rand.nextInt(3), 1 + rand.nextInt(12));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if (attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 5 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(5);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setDamagePerTurn(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else {
                            weaponOnHit.setPercentDefenseChange(0);
                        }
                    }
                } else {
                    weaponDamage = 1 + rand.nextInt(70);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(18), rand.nextDouble() * 0.2, 1 + rand.nextInt(170), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.5, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(15)), rand.nextDouble() * -0.3, 0, 1 + rand.nextInt(3), 1 + rand.nextInt(15));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if (attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 5 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(5);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setDamagePerTurn(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else {
                            weaponOnHit.setPercentDefenseChange(0);
                        }
                    }
                }
            } else if (weaponTypeDecider == 1) {//~Bow      Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = rand.nextInt(2);
                if (level < 20) {
                    weaponDamage = 1 + rand.nextInt(9);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(4), rand.nextDouble() * 0.1, 1 + rand.nextInt(40), 0, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0);
                    attributes = 1;
                    for(int i = 0; i < 3 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(3);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else {
                            weaponStats.setPercentExperienceChange(0);
                        }
                    }
                    if(onHitAttributes == 0) {
                        weaponOnHit.setFlatSpeedChange(0);
                    }
                } else if (level < 30) {
                    weaponDamage = 1 + rand.nextInt(19);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(6), rand.nextDouble() * 0.1, 1 + rand.nextInt(60), rand.nextDouble() * 0.05, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1, rand.nextDouble() * -0.1, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(3)), 0, 0, 1, 0);
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 3 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(3);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else {
                            weaponOnHit.setFlatDefenseChange(0);
                        }
                    }
                } else if (level < 40) {
                    weaponDamage = 1 + rand.nextInt(29);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(8), rand.nextDouble() * 0.15, 1 + rand.nextInt(80), rand.nextDouble() * 0.1, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.25, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(5)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(3));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 4 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(4);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2){
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 50) {
                    weaponDamage = 1 + rand.nextInt(39);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(10), rand.nextDouble() * 0.15, 1 + rand.nextInt(100), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.4, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(7)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(6));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 4 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(4);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2){
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 60) {
                    weaponDamage = 1 + rand.nextInt(49);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(14), rand.nextDouble() * 0.2, 1 + rand.nextInt(130), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.55, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(9)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(8));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 4 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(4);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2){
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 70) {
                    weaponDamage = 1 + rand.nextInt(59);
                    weaponRange = 1 + rand.nextInt(3);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(18), rand.nextDouble() * 0.2, 1 + rand.nextInt(170), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.6, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(10)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(10));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 4 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(4);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2){
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else {
                    weaponDamage = 1 + rand.nextInt(70);
                    weaponRange = 1 + rand.nextInt(3);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(25), rand.nextDouble() * 0.25, 1 + rand.nextInt(210), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(4)), rand.nextDouble() * -0.75, 0, 0, 0, 0, 0, 0, -1 * (1 + rand.nextInt(11)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(12));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 4 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(4);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2){
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                }
            } else if (weaponTypeDecider == 2) {//~Wand     Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = 2 + rand.nextInt(3);
                if (level < 30) {
                    weaponDamage = 1 + rand.nextInt(9);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(6), rand.nextDouble() * 0.1, 1 + rand.nextInt(60), rand.nextDouble() * 0.05, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1, rand.nextDouble() * -0.05, 0, 0, 0, 0, -1 * (1 + rand.nextInt(5)), rand.nextDouble() * -0.05, -1 * (1 + rand.nextInt(3)), 0, 0, 1 + rand.nextInt(2), 1 + rand.nextInt(3));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 6 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(6);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 40) {
                    weaponDamage = 1 + rand.nextInt(19);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(8), rand.nextDouble() * 0.15, 1 + rand.nextInt(80), rand.nextDouble() * 0.1, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1, rand.nextDouble() * -0.1, 0, 0, 0, 0, -1 * (1 + rand.nextInt(10)), rand.nextDouble() * -0.05, -1 * (1 + rand.nextInt(5)), rand.nextDouble() * -0.05, 0, 1 + rand.nextInt(3), 1 + rand.nextInt(6));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 7 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(7);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 50) {
                    weaponDamage = 1 + rand.nextInt(29);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(10), rand.nextDouble() * 0.15, 1 + rand.nextInt(100), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.15, 0, 0, 0, 0, -1 * (1 + rand.nextInt(15)), rand.nextDouble() * -0.1, -1 * (1 + rand.nextInt(7)), rand.nextDouble() * -0.1, 0, 1 + rand.nextInt(3), 1 + rand.nextInt(9));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 7 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(7);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 60) {
                    weaponDamage = 1 + rand.nextInt(39);
                    weaponRange = 1 + rand.nextInt(3);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(14), rand.nextDouble() * 0.2, 1 + rand.nextInt(130), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.2, 0, 0, 0, 0, -1 * (1 + rand.nextInt(20)), rand.nextDouble() * -0.15, -1 * (1 + rand.nextInt(9)), rand.nextDouble() * -0.15, 0, 1 + rand.nextInt(4), 1 + rand.nextInt(12));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 7 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(7);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 70) {
                    weaponDamage = 1 + rand.nextInt(49);
                    weaponRange = 1 + rand.nextInt(3);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(18), rand.nextDouble() * 0.2, 1 + rand.nextInt(170), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.25, 0, 0, 0, 0, -1 * (1 + rand.nextInt(30)), rand.nextDouble() * -0.2, -1 * (1 + rand.nextInt(11)), rand.nextDouble() * -0.2, 0, 1 + rand.nextInt(4), 1 + rand.nextInt(15));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 7 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(7);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else {
                    weaponDamage = 1 + rand.nextInt(59);
                    weaponRange = 1 + rand.nextInt(4);
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(25), rand.nextDouble() * 0.25, 1 + rand.nextInt(210), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(4)), rand.nextDouble() * -0.3, 0, 0, 0, 0, -1 * (1 + rand.nextInt(40)), rand.nextDouble() * -0.25, -1 * (1 + rand.nextInt(13)), rand.nextDouble() * -0.25, 0, 1 + rand.nextInt(5), 1 + rand.nextInt(18));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 7 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(7);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                }
            } else {//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Stave    Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = 3 + rand.nextInt(4);
                if (level < 40) {
                    weaponDamage = 1 + rand.nextInt(9);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = rand.nextInt(2);
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(8), rand.nextDouble() * 0.15, 1 + rand.nextInt(80), rand.nextDouble() * 0.1, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.05, 0, 0, 0, rand.nextDouble() * -0.1, -1 * (1 + rand.nextInt(5)), rand.nextDouble() * -0.05, -1, rand.nextDouble() * -0.05, 1 + rand.nextInt(4), 1 + rand.nextInt(3), 1 + rand.nextInt(4));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 9 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(9);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setPercentExperienceChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 6) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else if (onHitAttributeToRemove == 7) {
                            weaponOnHit.setFlatRegenPerTurn(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 50) {
                    weaponDamage = 1 + rand.nextInt(19);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = rand.nextInt(2);
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(10), rand.nextDouble() * 0.15, 1 + rand.nextInt(100), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.05, 0, 0, 0, rand.nextDouble() * -0.15, -1 * (1 + rand.nextInt(15)), rand.nextDouble() * -0.05, -1 * (1 + rand.nextInt(2)), rand.nextDouble() * -0.05, 1 + rand.nextInt(6), 1 + rand.nextInt(4), 1 + rand.nextInt(6));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 9 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(9);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setPercentExperienceChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 6) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else if (onHitAttributeToRemove == 7) {
                            weaponOnHit.setFlatRegenPerTurn(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 60) {
                    weaponDamage = 1 + rand.nextInt(29);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = rand.nextInt(3);
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(14), rand.nextDouble() * 0.2, 1 + rand.nextInt(130), rand.nextDouble() * 0.15, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.1, 0, 0, 0, rand.nextDouble() * -0.15, -1 * (1 + rand.nextInt(20)), rand.nextDouble() * -0.1, -1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.1, 1 + rand.nextInt(8), 1 + rand.nextInt(5), 1 + rand.nextInt(8));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 9 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(9);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setPercentExperienceChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 6) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else if (onHitAttributeToRemove == 7) {
                            weaponOnHit.setFlatRegenPerTurn(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else if (level < 70) {
                    weaponDamage = 1 + rand.nextInt(39);
                    weaponRange = 1 + rand.nextInt(2);
                    weaponSplash = rand.nextInt(3);
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(18), rand.nextDouble() * 0.2, 1 + rand.nextInt(170), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.1, 0, 0, 0, rand.nextDouble() * -0.2, -1 * (1 + rand.nextInt(25)), rand.nextDouble() * -0.1, -1 * (1 + rand.nextInt(4)), rand.nextDouble() * -0.1, 1 + rand.nextInt(10), 1 + rand.nextInt(6), 1 + rand.nextInt(10));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 9 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(9);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setPercentExperienceChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 6) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else if (onHitAttributeToRemove == 7) {
                            weaponOnHit.setFlatRegenPerTurn(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                } else {
                    weaponDamage = 1 + rand.nextInt(49);
                    weaponRange = 1 + rand.nextInt(3);
                    weaponSplash = rand.nextInt(4);
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(25), rand.nextDouble() * 0.25, 1 + rand.nextInt(210), rand.nextDouble() * 0.2, 0, 0, 0);
                    weaponOnHit = new TempStatusModifier(-1 * (1 + rand.nextInt(3)), rand.nextDouble() * -0.15, 0, 0, 0, rand.nextDouble() * -0.2, -1 * (1 + rand.nextInt(30)), rand.nextDouble() * -0.15, -1 * (1 + rand.nextInt(5)), rand.nextDouble() * -0.15, 1 + rand.nextInt(12), 1 + rand.nextInt(7), 1 + rand.nextInt(12));
                    attributes = 1 + rand.nextInt(2);
                    for(int i = 0; i < 4 - attributes; ++i) {
                        int attributeToRemove = rand.nextInt(4);
                        if(attributeToRemove == 0) {
                            weaponStats.setFlatExperienceChange(0);
                        } else if (attributeToRemove == 1) {
                            weaponStats.setFlatAttackChange(0);
                        } else if (attributeToRemove == 2) {
                            weaponStats.setPercentExperienceChange(0);
                        } else {
                            weaponStats.setPercentAttackChange(0);
                        }
                    }
                    for(int i = 0; i < 9 - onHitAttributes; ++i) {
                        int onHitAttributeToRemove = rand.nextInt(9);
                        if (onHitAttributeToRemove == 0) {
                            weaponOnHit.setFlatSpeedChange(0);
                        } else if (onHitAttributeToRemove == 1) {
                            weaponOnHit.setPercentSpeedChange(0);
                        } else if (onHitAttributeToRemove == 2) {
                            weaponOnHit.setPercentExperienceChange(0);
                        } else if (onHitAttributeToRemove == 3) {
                            weaponOnHit.setFlatAttackChange(0);
                        } else if (onHitAttributeToRemove == 4) {
                            weaponOnHit.setPercentAttackChange(0);
                        } else if (onHitAttributeToRemove == 5) {
                            weaponOnHit.setFlatDefenseChange(0);
                        } else if (onHitAttributeToRemove == 6) {
                            weaponOnHit.setPercentDefenseChange(0);
                        } else if (onHitAttributeToRemove == 7) {
                            weaponOnHit.setFlatRegenPerTurn(0);
                        } else {
                            weaponOnHit.setDamagePerTurn(0);
                        }
                    }
                }
            }

            return new Weapon(weaponStats, weaponRange, weaponSplash, weaponDamage, weaponOnHit);

        } else if (itemTypeDecider == 1) {//~~~~~~~~~~~~~~~~~~Clothes    Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier clothesStats;
            int attributes;

            if(level < 5) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 20) {
                attributes = 1 + rand.nextInt(2);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(8), rand.nextDouble() * 0.05, 0, 0, 1 + rand.nextInt(10), 0, 1 + rand.nextInt(15), rand.nextDouble() * 0.1, 1 + rand.nextInt(5));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1 + rand.nextInt(2);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(12), rand.nextDouble() * 0.1, 0, 0, 1 + rand.nextInt(25), 0, 1 + rand.nextInt(20), rand.nextDouble() * 0.1, 1 + rand.nextInt(7));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1 + rand.nextInt(3);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(16), rand.nextDouble() * 0.15, 0, 0, 1 + rand.nextInt(35), 0, 1 + rand.nextInt(25), rand.nextDouble() * 0.15, 1 + rand.nextInt(10));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1 + rand.nextInt(4);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(20), rand.nextDouble() * 0.2, 0, 0, 1 + rand.nextInt(50), 0, 1 + rand.nextInt(30), rand.nextDouble() * 0.2, 1 + rand.nextInt(14));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1 + rand.nextInt(4);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(30), rand.nextDouble() * 0.25, 0, 0, 1 + rand.nextInt(65), 0, 1 + rand.nextInt(40), rand.nextDouble() * 0.25, 1 + rand.nextInt(18));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            } else if (level < 70) {
                attributes = 1 + rand.nextInt(5);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(40), rand.nextDouble() * 0.3, 0, 0, 1 + rand.nextInt(80), 0, 1 + rand.nextInt(50), rand.nextDouble() * 0.3, 1 + rand.nextInt(25));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            } else {
                attributes = 1 + rand.nextInt(5);
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(55), rand.nextDouble() * 0.4, 0, 0, 1 + rand.nextInt(110), 0, 1 + rand.nextInt(60), rand.nextDouble() * 0.35, 1 + rand.nextInt(30));
                for(int i = 0; i < 6 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(6);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 2) {
                        clothesStats.setFlatRegenPerTurn(0);
                    } else if (attributeToRemove == 3) {
                        clothesStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4) {
                        clothesStats.setPercentHealthChange(0);
                    } else {
                        clothesStats.setPercentDefenseChange(0);
                    }
                }
            }
        } else if (itemTypeDecider == 2) {//~~~~~~~~~~~~~~~~~~Shoes      Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier shoesStats;
            int attributes;

            if(level < 5) {
                attributes = 1 + rand.nextInt(2);
                shoesStats = new StatusModifier(1, 0, 1 + rand.nextInt(2), 0, 0, 0, 0, 0, 1, 0, 1);
                for(int i = 0; i < 4 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(4);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else {
                        shoesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 20) {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(2), rand.nextDouble() * 0.05, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(2));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(2), rand.nextDouble() * 0.1, 1 + rand.nextInt(8), 0, 0, 0, 0, 0, 1 + rand.nextInt(4), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(3), rand.nextDouble() * 0.15, 1 + rand.nextInt(11), 0, 0, 0, 0, 0, 1 + rand.nextInt(6), 0, 1 + rand.nextInt(4));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(3), rand.nextDouble() * 0.2, 1 + rand.nextInt(14), 0, 0, 0, 0, 0, 1 + rand.nextInt(8), 0, 1 + rand.nextInt(5));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(4), rand.nextDouble() * 0.25, 1 + rand.nextInt(17), 0, 0, 0, 0, 0, 1 + rand.nextInt(11), 0, 1 + rand.nextInt(6));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            } else if (level < 70) {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(4), rand.nextDouble() * 0.3, 1 + rand.nextInt(21), 0, 0, 0, 0, 0, 1 + rand.nextInt(15), 0, 1 + rand.nextInt(7));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            } else {
                attributes = 1 + rand.nextInt(3);
                shoesStats = new StatusModifier(1 + rand.nextInt(5), rand.nextDouble() * 0.35, 1 + rand.nextInt(25), 0, 0, 0, 0, 0, 1 + rand.nextInt(20), 0, 1 + rand.nextInt(8));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        shoesStats.setFlatSpeedChange(0);
                    } else if (attributeToRemove == 1){
                        shoesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 2) {
                        shoesStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 3) {
                        shoesStats.setFlatRegenPerTurn(0);
                    } else {
                        shoesStats.setPercentSpeedChange(0);
                    }
                }
            }
        } else if (itemTypeDecider == 3) {//~~~~~~~~~~~~~~~~~~Hat        Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier clothesStats;
            int attributes;

            if(level < 5) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 20) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 70) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            }
        } else if (itemTypeDecider == 4) {//~~~~~~~~~~~~~~~~~~Accessory  Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier clothesStats;
            int attributes;

            if(level < 5) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 20) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 70) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            } else {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(4), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 3 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        clothesStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        clothesStats.setFlatDefenseChange(0);
                    } else {
                        clothesStats.setFlatRegenPerTurn(0);
                    }
                }
            }
        } else {//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Consumable Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            TempStatusModifier potionStats;
            int attributes;

            if(level < 5) {
                attributes = 1;
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(10), 0, 1 + rand.nextInt(10), 1 + rand.nextInt(3), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 20) {
                attributes = 1;
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(15), 0, 1 + rand.nextInt(20), 1 + rand.nextInt(3), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1 + rand.nextInt(2);
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(20), 0, 1 + rand.nextInt(35), 1 + rand.nextInt(3), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1 + rand.nextInt(2);
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(25), 0, 1 + rand.nextInt(45), 1 + rand.nextInt(4), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1 + rand.nextInt(2);
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(30), 0, 1 + rand.nextInt(60), 1 + rand.nextInt(4), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1 + rand.nextInt(2);
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(35), 0, 1 + rand.nextInt(75), 1 + rand.nextInt(4), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            } else {
                attributes = 1 + rand.nextInt(2);
                potionStats = new TempStatusModifier(0, 0, 0, 0, 0, 0, 0, 0, 1 + rand.nextInt(40), 0, 1 + rand.nextInt(90), 1 + rand.nextInt(4), 0);
                for(int i = 0; i < 2 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(3);
                    if(attributeToRemove == 0) {
                        potionStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        potionStats.setFlatDefenseChange(0);
                    } else {
                        potionStats.setFlatRegenPerTurn(0);
                    }
                }
            }
        }

        return null;
    }
}
