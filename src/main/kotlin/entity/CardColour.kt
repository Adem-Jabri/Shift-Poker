package entity

/**
 * Enum to distinguish between the four possible colours in a french-suited card game:
 * clubs, spades, hearts, or diamonds
 */
enum class CardColour {
    CLUBS,
    SPADES,
    HEARTS,
    DIAMONDS,
    ;

    /**
     * provide a single character to represent this colour.
     * Returns one of: ♣/♠/♥/♦
     */
    override fun toString() = when(this) {
        CLUBS -> "♣"
        SPADES -> "♠"
        HEARTS -> "♥"
        DIAMONDS -> "♦"
    }


}