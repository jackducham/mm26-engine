package mech.mania.engine.domain.game.items;

public enum HatEffect {
    //TODO: List hat effects and define implementation in Player methods

    LINGERING_POTIONS,
    /* EFFECT:
        This hat effect causes any consumable the wearer uses to apply the same TempStatusModifier,
        but with a doubled duration.

       IMPLEMENTATION:
        NOT YET IMPLEMENTED
     */

    SHOE_BOOST,
    /* EFFECT:
        This hat effect causes doubles the flat speed modifier of the wearer's shoes.

       IMPLEMENTATION:
        NOT YET IMPLEMENTED
     */

    CLOTHES_BOOST,
    /* EFFECT:
        This hat effect multiplies the flat defense bonus of the wearer's clothes by a
        factor of 1.5.

       IMPLEMENTATION:
        NOT YET IMPLEMENTED
     */

    WEAPON_BOOST,
    /* EFFECT:
        This hat effect multiplies the flat damage bonus of the wearer's weapon by a
        factor of 1.25.

       IMPLEMENTATION:
        NOT YET IMPLEMENTED
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
        NOT YET IMPLEMENTED
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
