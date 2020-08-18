package mech.mania.engine.domain.game.items;

public enum HatEffect {
    //TODO: List hat effects and define implementation in Player methods

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

    EXTRA_PICKUP_RANGE,
    /* EFFECT:
        This hat effect increases the range at which the wearer can pickup items from the ground.

       IMPLEMENTATION:
        NOT YET IMPLEMENTED
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

    FULL_EXP
    /* EFFECT:
        This hat effect causes the wearer to receive the the full amount of kill xp from any
        monster they damage instead of their regular portion of it. This effect doesn't affect the
        amount of xp that any other character receive, nor does it apply to xp awarded for player
        kills.

       IMPLEMENTATION:
        NOT YET IMPLEMENTED
     */
}
