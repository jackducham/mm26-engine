package mech.mania.engine.domain.game.factory;

import mech.mania.engine.domain.game.items.*;

import java.util.Random;

public class ItemFactory {
    public static Item generateItem(int level) {
        Random rand = new Random();
        //check that the provided level is not negative
        if(level < 0) {
            return null;
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

        } else if (itemTypeDecider == 2) {//~~~~~~~~~~~~~~~~~~Shoes      Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/

        } else if (itemTypeDecider == 3) {//~~~~~~~~~~~~~~~~~~Hat        Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/

        } else if (itemTypeDecider == 4) {//~~~~~~~~~~~~~~~~~~Accessory  Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/

        } else {//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Consumable Builder -\/-\/-\/-\/-\/-\/-\/-\/-\/

        }

        return null;
    }
}
