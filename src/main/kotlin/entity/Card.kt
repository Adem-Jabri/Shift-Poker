package entity
/**
 * Data class for the single type of game elements that the game "ShiftPoker" knows: cards.
 * the card has a state, whether hidden or revealed and its given by the argument [hidden]
 * Each Card is characterized by a [CardColour] and a [CardValue]
 */

data class Card (var hidden: Boolean, val cardColour: CardColour, val value: CardValue){
    /**
     * provide a string to represent the Card.
     */
    override fun toString() = "$cardColour $value"
}