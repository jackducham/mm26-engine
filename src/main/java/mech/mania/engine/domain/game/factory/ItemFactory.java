package mech.mania.engine.domain.game.factory;

import mech.mania.engine.domain.game.items.*;

import java.util.Random;

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
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 30) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 40) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 50) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 60) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                }
            } else if (weaponTypeDecider == 1) {//~Bow      Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = rand.nextInt(2);
                if (level < 20) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 30) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 40) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 50) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 60) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                }
            } else if (weaponTypeDecider == 2) {//~Wand     Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = 2 + rand.nextInt(3);
                if (level < 30) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 40) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 50) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 60) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                }
            } else {//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Stave    Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
                onHitAttributes = 3 + rand.nextInt(4);
                if (level < 40) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 50) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else if (level < 60) {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
                } else {
                    weaponDamage = rand.nextInt(10);
                    weaponRange = 1;
                    weaponSplash = 0;
                    weaponStats = new StatusModifier(0, 0, 0, 0, 1 + rand.nextInt(2), 0, 1 + rand.nextInt(19), 0, 0, 0, 0);
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
        } else if (itemTypeDecider == 2) {//~~~~~~~~~~~~~~~~~~Shoes      Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/
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
