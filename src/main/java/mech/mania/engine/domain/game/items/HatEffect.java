package mech.mania.engine.domain.game.items;

public enum HatEffect {
    LINGERING_POTIONS,
    /* EFFECT:
        This hat effect causes any consumable the wearer uses to apply the same TempStatusModifier,
        but with a doubled duration.

       IMPLEMENTATION:
        Player.java -> useConsumable(Consumable, int) -> This function now checks for this hat effect. If this hat
        effect is detected, the function doubles the turnsLeft variable within the TempStatusModifier before it applies
        it to the Player.
     */

    SHOES_BOOST,
    /* EFFECT:
        This hat effect applies the flat speed bonus from the wearer's shoes a second time.

       IMPLEMENTATION:
        Player.java -> getSpeed() -> if this hat effect is detected, it adds the flat speed bonus on the shoes a second time.
     */

    CLOTHES_BOOST,
    /* EFFECT:
        This hat effect applies the flat defense bonus of the wearer's clothes a second time.

       IMPLEMENTATION:
        Player.java -> getDefense() -> if this hat effect is detected, it adds the flat defense bonus on the clothes a second time.
     */

    WEAPON_BOOST,
    /* EFFECT:
        This hat effect applies the flat damage bonus of the wearer's weapon a second time at half value.

       IMPLEMENTATION:
        Player.java -> getAttack() -> if this hat effect is detected, it adds the flat defense bonus on the weapon a second time (Multiplying by 0.5 first).
     */

    TRIPLED_ON_HIT,
    /* EFFECT:
        This hat effect causes attacks made by the wearer to deal no regular damage but they apply
        three copies of the weapons on hit effect.

       IMPLEMENTATION:
        GameLogic.java -> processAttack(GameState gameState, Character attacker, Position attackCoordinate) ->
        if this function detects the TRIPLED_ON_HIT effect on the attacker's hat, it creates a copy of the attacker's
        weapon which has an attack (NOT attack change within the stats) of 0, and hits the target with that weapon three
        times.
     */

    STACKING_BONUS
    /* EFFECT:
        This hat effect adds a long lasting TSM to the wearer every turn which has the same stats as the hat this effect
        is on. The end effect is that the longer that hat is worn, the bigger the stat bonus, up to the duration of the
        TSM. When taking the hat off, the stats are slowly lost, one stack at a time, as they all wear off.

       IMPLEMENTATION:
        Player.java -> updateCharacter -> If this hat effect is detected, the updateCharacter function copies the SM of
        the wearer's hat into a TSM with a duration of 10 and a damage per turn of 0, and then applies the effect.
     */
}
