package mech.mania.engine.domain.game.factory;

import mech.mania.engine.domain.game.items.*;

import java.util.Random;
//TODO: add names, add name generation, add accessories, update wiki with items
public class ItemFactory {
    public static Item generateItem(int level) {
        Random rand = new Random();
        //check that the provided level is not negative
        if(level < 0) {
            throw new IllegalArgumentException("Items cannot have negative levels");
        }

        int itemTypeDecider = rand.nextInt(7);
        String spriteFileName;

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

                if (weaponDamage <= 9) {
                    spriteFileName = "mm26_wearables/weapons/swords/1.png";
                } else if (weaponDamage <= 19) {
                    spriteFileName = "mm26_wearables/weapons/swords/2.png";
                } else if (weaponDamage <= 29) {
                    spriteFileName = "mm26_wearables/weapons/swords/3.png";
                } else if (weaponDamage <= 39) {
                    spriteFileName = "mm26_wearables/weapons/swords/4.png";
                } else if (weaponDamage <= 49) {
                    spriteFileName = "mm26_wearables/weapons/swords/5.png";
                } else if (weaponDamage <= 59) {
                    spriteFileName = "mm26_wearables/weapons/swords/6.png";
                } else {
                    spriteFileName = "mm26_wearables/weapons/swords/7.png";
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

                if (weaponDamage <= 9) {
                    spriteFileName = "mm26_wearables/weapons/bows/1.png";
                } else if (weaponDamage <= 19) {
                    spriteFileName = "mm26_wearables/weapons/bows/2.png";
                } else if (weaponDamage <= 29) {
                    spriteFileName = "mm26_wearables/weapons/bows/3.png";
                } else if (weaponDamage <= 39) {
                    spriteFileName = "mm26_wearables/weapons/bows/4.png";
                } else if (weaponDamage <= 49) {
                    spriteFileName = "mm26_wearables/weapons/bows/5.png";
                } else if (weaponDamage <= 59) {
                    spriteFileName = "mm26_wearables/weapons/bows/6.png";
                } else {
                    spriteFileName = "mm26_wearables/weapons/bows/7.png";
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

                if (weaponDamage <= 9) {
                    spriteFileName = "mm26_wearables/weapons/wands/1.png";
                } else if (weaponDamage <= 19) {
                    spriteFileName = "mm26_wearables/weapons/wands/2.png";
                } else if (weaponDamage <= 29) {
                    spriteFileName = "mm26_wearables/weapons/wands/3.png";
                } else if (weaponDamage <= 39) {
                    spriteFileName = "mm26_wearables/weapons/wands/4.png";
                } else if (weaponDamage <= 49) {
                    spriteFileName = "mm26_wearables/weapons/wands/5.png";
                } else {
                    spriteFileName = "mm26_wearables/weapons/wands/6.png";
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

                if (weaponDamage <= 9) {
                    spriteFileName = "mm26_wearables/weapons/staves/1.png";
                } else if (weaponDamage <= 19) {
                    spriteFileName = "mm26_wearables/weapons/staves/2.png";
                } else if (weaponDamage <= 29) {
                    spriteFileName = "mm26_wearables/weapons/staves/3.png";
                } else if (weaponDamage <= 39) {
                    spriteFileName = "mm26_wearables/weapons/staves/4.png";
                } else {
                    spriteFileName = "mm26_wearables/weapons/staves/5.png";
                }


            }

            return new Weapon(weaponStats, weaponRange, weaponSplash, weaponDamage, weaponOnHit, spriteFileName);

        } else if (itemTypeDecider == 1) {//~~~~~~~~~~~~~~~~~~Clothes    Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier clothesStats;
            int attributes;

            if(level < 5) {
                attributes = 1;
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(40), 0, 0, 0, 0, 0, 1 + rand.nextInt(5), 0, 1 + rand.nextInt(3));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(80), rand.nextDouble() * 0.05, 0, 0, 1 + rand.nextInt(10), 0, 1 + rand.nextInt(15), rand.nextDouble() * 0.1, 1 + rand.nextInt(5));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(120), rand.nextDouble() * 0.1, 0, 0, 1 + rand.nextInt(25), 0, 1 + rand.nextInt(20), rand.nextDouble() * 0.1, 1 + rand.nextInt(7));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(160), rand.nextDouble() * 0.15, 0, 0, 1 + rand.nextInt(35), 0, 1 + rand.nextInt(25), rand.nextDouble() * 0.15, 1 + rand.nextInt(10));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(200), rand.nextDouble() * 0.2, 0, 0, 1 + rand.nextInt(50), 0, 1 + rand.nextInt(30), rand.nextDouble() * 0.2, 1 + rand.nextInt(14));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(300), rand.nextDouble() * 0.25, 0, 0, 1 + rand.nextInt(65), 0, 1 + rand.nextInt(40), rand.nextDouble() * 0.25, 1 + rand.nextInt(18));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(400), rand.nextDouble() * 0.3, 0, 0, 1 + rand.nextInt(80), 0, 1 + rand.nextInt(50), rand.nextDouble() * 0.3, 1 + rand.nextInt(25));
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
                clothesStats = new StatusModifier(0, 0, 1 + rand.nextInt(550), rand.nextDouble() * 0.4, 0, 0, 1 + rand.nextInt(110), 0, 1 + rand.nextInt(60), rand.nextDouble() * 0.35, 1 + rand.nextInt(30));
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

            if (clothesStats.getFlatDefenseChange() <= 40) {
                spriteFileName = "mm26_wearables/clothes/villager_gal_top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 80) {
                spriteFileName = "mm26_wearables/clothes/butler_top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 120) {
                spriteFileName = "mm26_wearables/clothes/alchemist_top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 160) {
                spriteFileName = "mm26_wearables/clothes/witch_top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 200) {
                spriteFileName = "mm26_wearables/clothes/knight_top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 250) {
                spriteFileName = "mm26_wearables/clothes/maid_top_alt.png";
            } else if (clothesStats.getFlatDefenseChange() <= 300) {
                spriteFileName = "mm26_wearables/clothes/antler_boi_top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 350) {
                spriteFileName = "mm26_wearables/clothes/witch_top_alt.png";
            } else if (clothesStats.getFlatDefenseChange() <= 400) {
                spriteFileName = "mm26_wearables/clothes/Fruity Legolas - Top.png";
            } else if (clothesStats.getFlatDefenseChange() <= 450) {
                spriteFileName = "mm26_wearables/clothes/knight_top_alt.png";
            } else if (clothesStats.getFlatDefenseChange() <= 500) {
                spriteFileName = "mm26_wearables/clothes/Hooded - Top.png";
            } else {
                spriteFileName = "mm26_wearables/clothes/maid_top.png";
            }

            return new Clothes(clothesStats, spriteFileName);

        } else if (itemTypeDecider == 2) {//~~~~~~~~~~~~~~~~~~Shoes      Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier shoesStats;
            int attributes;

            if(level < 5) {
                attributes = 1 + rand.nextInt(2);
                shoesStats = new StatusModifier(1, 0, 1 + rand.nextInt(20), 0, 0, 0, 0, 0, 1, 0, 1);
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
                shoesStats = new StatusModifier(1 + rand.nextInt(2), rand.nextDouble() * 0.05, 1 + rand.nextInt(40), 0, 0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(2));
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
                shoesStats = new StatusModifier(1 + rand.nextInt(2), rand.nextDouble() * 0.1, 1 + rand.nextInt(80), 0, 0, 0, 0, 0, 1 + rand.nextInt(4), 0, 1 + rand.nextInt(3));
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
                shoesStats = new StatusModifier(1 + rand.nextInt(3), rand.nextDouble() * 0.15, 1 + rand.nextInt(110), 0, 0, 0, 0, 0, 1 + rand.nextInt(6), 0, 1 + rand.nextInt(4));
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
                shoesStats = new StatusModifier(1 + rand.nextInt(3), rand.nextDouble() * 0.2, 1 + rand.nextInt(140), 0, 0, 0, 0, 0, 1 + rand.nextInt(8), 0, 1 + rand.nextInt(5));
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
                shoesStats = new StatusModifier(1 + rand.nextInt(4), rand.nextDouble() * 0.25, 1 + rand.nextInt(170), 0, 0, 0, 0, 0, 1 + rand.nextInt(11), 0, 1 + rand.nextInt(6));
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
                shoesStats = new StatusModifier(1 + rand.nextInt(4), rand.nextDouble() * 0.3, 1 + rand.nextInt(210), 0, 0, 0, 0, 0, 1 + rand.nextInt(15), 0, 1 + rand.nextInt(7));
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
                shoesStats = new StatusModifier(1 + rand.nextInt(5), rand.nextDouble() * 0.35, 1 + rand.nextInt(250), 0, 0, 0, 0, 0, 1 + rand.nextInt(20), 0, 1 + rand.nextInt(8));
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

            int spriteDecider = rand.nextInt(3);
            if (shoesStats.getFlatSpeedChange() <= 2) {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/shoes/villager_gal_bottom.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/shoes/butler_bottom.png";
                } else {
                    spriteFileName = "mm26_wearables/shoes/alchemist_bottom.png";
                }
            } else if (shoesStats.getFlatSpeedChange() <= 3) {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/shoes/witch_bottom.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/shoes/knight_bottom.png";
                } else {
                    spriteFileName = "mm26_wearables/shoes/maid_bottom_alt.png";
                }
            } else if (shoesStats.getFlatSpeedChange() <= 4) {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/shoes/antler_boi_bottom.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/shoes/witch_bottom_alt.png";
                } else {
                    spriteFileName = "mm26_wearables/shoes/Fruity Legolas - Bottom.png";
                }
            } else {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/shoes/knight_bottom_alt.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/shoes/Hooded - Bottom.png";
                } else {
                    spriteFileName = "mm26_wearables/shoes/maid_bottom.png";
                }
            }

            return new Shoes(shoesStats, spriteFileName);

        } else if (itemTypeDecider == 3) {//~~~~~~~~~~~~~~~~~~Hat        Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier hatStats;
            int attributes;

            if(level < 20) {
                attributes = 1;
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(60), 0, 1 + rand.nextInt(2), rand.nextDouble() * 0.1, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(2));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setFlatDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1 + rand.nextInt(4);
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(90), 0, 1 + rand.nextInt(4), rand.nextDouble() * 0.1, 1 + rand.nextInt(10), rand.nextDouble() * 0.05, 1 + rand.nextInt(3), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 7 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(7);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4){
                        hatStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 5){
                        hatStats.setFlatDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1 + rand.nextInt(6);
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(130), 0, 1 + rand.nextInt(6), rand.nextDouble() * 0.15, 1 + rand.nextInt(30), rand.nextDouble() * 0.05, 1 + rand.nextInt(4), rand.nextDouble() * 0.05, 1 + rand.nextInt(4));
                for(int i = 0; i < 8 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(8);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4){
                        hatStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 5){
                        hatStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 6) {
                        hatStats.setPercentDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1 + rand.nextInt(6);
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(170), rand.nextDouble() * 0.1, 1 + rand.nextInt(8), rand.nextDouble() * 0.2, 1 + rand.nextInt(60), rand.nextDouble() * 0.1, 1 + rand.nextInt(5), rand.nextDouble() * 0.1, 1 + rand.nextInt(5));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        hatStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        hatStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        hatStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        hatStats.setPercentDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1 + rand.nextInt(7);
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(220), rand.nextDouble() * 0.15, 1 + rand.nextInt(10), rand.nextDouble() * 0.2, 1 + rand.nextInt(80), rand.nextDouble() * 0.1, 1 + rand.nextInt(6), rand.nextDouble() * 0.15, 1 + rand.nextInt(6));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        hatStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        hatStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        hatStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        hatStats.setPercentDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 70) {
                attributes = 1 + rand.nextInt(8);
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(260), rand.nextDouble() * 0.2, 1 + rand.nextInt(14), rand.nextDouble() * 0.2, 1 + rand.nextInt(110), rand.nextDouble() * 0.15, 1 + rand.nextInt(7), rand.nextDouble() * 0.2, 1 + rand.nextInt(8));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        hatStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        hatStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        hatStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        hatStats.setPercentDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            } else {
                attributes = 1 + rand.nextInt(9);
                hatStats = new StatusModifier(0, 0, 1 + rand.nextInt(350), rand.nextDouble() * 0.25, 1 + rand.nextInt(18), rand.nextDouble() * 0.2, 1 + rand.nextInt(150), rand.nextDouble() * 0.2, 1 + rand.nextInt(8), rand.nextDouble() * 0.25, 1 + rand.nextInt(10));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        hatStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        hatStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        hatStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        hatStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        hatStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        hatStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        hatStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        hatStats.setPercentDefenseChange(0);
                    } else {
                        hatStats.setFlatRegenPerTurn(0);
                    }
                }
            }
            MagicEffect effect = MagicEffect.NONE;
            int hatEffectYN = rand.nextInt(2);
            if(hatEffectYN == 1) {
                int hatEffect = rand.nextInt(6);
                if (hatEffect == 0) {
                    effect = MagicEffect.CLOTHES_BOOST;
                } else if (hatEffect == 1) {
                    effect = MagicEffect.SHOES_BOOST;
                } else if (hatEffect == 2) {
                    effect = MagicEffect.WEAPON_BOOST;
                } else if (hatEffect == 3) {
                    effect = MagicEffect.LINGERING_POTIONS;
                } else if (hatEffect == 4) {
                    effect = MagicEffect.STACKING_BONUS;
                } else {
                    effect = MagicEffect.TRIPLED_ON_HIT;
                }
            }

            int spriteDecider = rand.nextInt(3);
            if (hatStats.getFlatAttackChange() <= 40) {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/hats/villager_head.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/hats/butler_head.png";
                } else {
                    spriteFileName = "mm26_wearables/hats/alchemist_head.png";
                }
            } else if (hatStats.getFlatAttackChange() <= 80) {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/hats/witch_head.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/hats/knight_head.png";
                } else {
                    spriteFileName = "mm26_wearables/hats/maid_head_alt.png";
                }
            } else if (hatStats.getFlatAttackChange() <= 120) {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/hats/antler_boi_head.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/hats/witch_head_alt.png";
                } else {
                    spriteFileName = "mm26_wearables/hats/Fruity Legolas - Head.png";
                }
            } else {
                if (spriteDecider == 0) {
                    spriteFileName = "mm26_wearables/hats/knight_head_alt.png";
                } else if (spriteDecider == 1) {
                    spriteFileName = "mm26_wearables/hats/Hooded - Head.png";
                } else {
                    spriteFileName = "mm26_wearables/hats/maid_head.png";
                }
            }

            return new Hat(hatStats, effect, spriteFileName);

        } else if (itemTypeDecider == 4) {//~~~~~~~~~~~~~~~~~~Accessory  Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
            StatusModifier accessoryStats;
            int attributes;

            if(level < 20) {
                attributes = 1;
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(60), 0, 1 + rand.nextInt(2), rand.nextDouble() * 0.1, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(2));
                for(int i = 0; i < 5 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(5);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setFlatDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 30) {
                attributes = 1 + rand.nextInt(4);
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(90), 0, 1 + rand.nextInt(4), rand.nextDouble() * 0.1, 1 + rand.nextInt(10), rand.nextDouble() * 0.05, 1 + rand.nextInt(3), 0, 1 + rand.nextInt(3));
                for(int i = 0; i < 7 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(7);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4){
                        accessoryStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 5){
                        accessoryStats.setFlatDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 40) {
                attributes = 1 + rand.nextInt(6);
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(130), 0, 1 + rand.nextInt(6), rand.nextDouble() * 0.15, 1 + rand.nextInt(30), rand.nextDouble() * 0.05, 1 + rand.nextInt(4), rand.nextDouble() * 0.05, 1 + rand.nextInt(4));
                for(int i = 0; i < 8 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(8);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 4){
                        accessoryStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 5){
                        accessoryStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 6) {
                        accessoryStats.setPercentDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 50) {
                attributes = 1 + rand.nextInt(6);
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(170), rand.nextDouble() * 0.1, 1 + rand.nextInt(8), rand.nextDouble() * 0.2, 1 + rand.nextInt(60), rand.nextDouble() * 0.1, 1 + rand.nextInt(5), rand.nextDouble() * 0.1, 1 + rand.nextInt(5));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        accessoryStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        accessoryStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        accessoryStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        accessoryStats.setPercentDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 60) {
                attributes = 1 + rand.nextInt(7);
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(220), rand.nextDouble() * 0.15, 1 + rand.nextInt(10), rand.nextDouble() * 0.2, 1 + rand.nextInt(80), rand.nextDouble() * 0.1, 1 + rand.nextInt(6), rand.nextDouble() * 0.15, 1 + rand.nextInt(6));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        accessoryStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        accessoryStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        accessoryStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        accessoryStats.setPercentDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            } else if (level < 70) {
                attributes = 1 + rand.nextInt(8);
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(260), rand.nextDouble() * 0.2, 1 + rand.nextInt(14), rand.nextDouble() * 0.2, 1 + rand.nextInt(110), rand.nextDouble() * 0.15, 1 + rand.nextInt(7), rand.nextDouble() * 0.2, 1 + rand.nextInt(8));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        accessoryStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        accessoryStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        accessoryStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        accessoryStats.setPercentDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            } else {
                attributes = 1 + rand.nextInt(9);
                accessoryStats = new StatusModifier(0, 0, 1 + rand.nextInt(350), rand.nextDouble() * 0.25, 1 + rand.nextInt(18), rand.nextDouble() * 0.2, 1 + rand.nextInt(150), rand.nextDouble() * 0.2, 1 + rand.nextInt(8), rand.nextDouble() * 0.25, 1 + rand.nextInt(10));
                for(int i = 0; i < 9 - attributes; ++i) {
                    int attributeToRemove = rand.nextInt(9);
                    if(attributeToRemove == 0) {
                        accessoryStats.setFlatHealthChange(0);
                    } else if (attributeToRemove == 1){
                        accessoryStats.setPercentHealthChange(0);
                    } else if (attributeToRemove == 2){
                        accessoryStats.setFlatExperienceChange(0);
                    } else if (attributeToRemove == 3){
                        accessoryStats.setPercentExperienceChange(0);
                    } else if (attributeToRemove == 4){
                        accessoryStats.setFlatAttackChange(0);
                    } else if (attributeToRemove == 5){
                        accessoryStats.setPercentAttackChange(0);
                    } else if (attributeToRemove == 6){
                        accessoryStats.setFlatDefenseChange(0);
                    } else if (attributeToRemove == 7){
                        accessoryStats.setPercentDefenseChange(0);
                    } else {
                        accessoryStats.setFlatRegenPerTurn(0);
                    }
                }
            }
            MagicEffect effect = MagicEffect.NONE;
            int hatEffectYN = rand.nextInt(2);
            if(hatEffectYN == 1) {
                int hatEffect = rand.nextInt(6);
                if (hatEffect == 0) {
                    effect = MagicEffect.CLOTHES_BOOST;
                } else if (hatEffect == 1) {
                    effect = MagicEffect.SHOES_BOOST;
                } else if (hatEffect == 2) {
                    effect = MagicEffect.WEAPON_BOOST;
                } else if (hatEffect == 3) {
                    effect = MagicEffect.LINGERING_POTIONS;
                } else if (hatEffect == 4) {
                    effect = MagicEffect.STACKING_BONUS;
                } else {
                    effect = MagicEffect.TRIPLED_ON_HIT;
                }
            }

            int spriteDecider = rand.nextInt(20);
            if (spriteDecider == 0) {
                spriteFileName = "mm26_wearables/accessories/Acc - Airpods.png";
            } else if (spriteDecider == 1) {
                spriteFileName = "mm26_wearables/accessories/Acc - Ammulet.png";
            } else if (spriteDecider == 2) {
                spriteFileName = "mm26_wearables/accessories/Acc - Civilized Weapon.png";
            } else if (spriteDecider == 3) {
                spriteFileName = "mm26_wearables/accessories/Acc - Forever Gauntlet.png";
            } else if (spriteDecider == 4) {
                spriteFileName = "mm26_wearables/accessories/Acc - Hero Mask.png";
            } else if (spriteDecider == 5) {
                spriteFileName = "mm26_wearables/accessories/Acc - Mask.png";
            } else if (spriteDecider == 6) {
                spriteFileName = "mm26_wearables/accessories/Acc - Monster Balls.png";
            } else if (spriteDecider == 7) {
                spriteFileName = "mm26_wearables/accessories/Acc - Nose Ring.png";
            } else if (spriteDecider == 8) {
                spriteFileName = "mm26_wearables/accessories/Acc - Pharoh_s Selfie Stick.png";
            } else if (spriteDecider == 9) {
                spriteFileName = "mm26_wearables/accessories/Acc - Power Gloves.png";
            } else if (spriteDecider == 10) {
                spriteFileName = "mm26_wearables/accessories/Acc - Quill.png";
            } else if (spriteDecider == 11) {
                spriteFileName = "mm26_wearables/accessories/Acc - Red Scarf.png";
            } else if (spriteDecider == 12) {
                spriteFileName = "mm26_wearables/accessories/Acc - Rings.png";
            } else if (spriteDecider == 13) {
                spriteFileName = "mm26_wearables/accessories/Acc - Rings.png";
            } else if (spriteDecider == 14) {
                spriteFileName = "mm26_wearables/accessories/Acc - Sunglasses.png";
            } else if (spriteDecider == 15) {
                spriteFileName = "mm26_wearables/accessories/bow1_acc.png";
            } else if (spriteDecider == 16) {
                spriteFileName = "mm26_wearables/accessories/bow2_acc.png";
            } else if (spriteDecider == 17) {
                spriteFileName = "mm26_wearables/accessories/earrings_acc.png";
            } else {
                spriteFileName = "mm26_wearables/accessories/onimask_acc.png";
            }

            return new Accessory(accessoryStats, effect, spriteFileName);

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

            return new Consumable(5, potionStats, "");
        }

        //return null;

        //comment the above return null out to check that every reachable state returns an item or throws an exception.
    }
}
