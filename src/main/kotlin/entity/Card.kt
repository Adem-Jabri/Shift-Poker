package entity
/**
 * Data class for the single type of game elements that the game "ShiftPoker" knows: cards.
 * the card has a state, whether hidden or revealed and its given by the argument [hidden]
 * Each Card is characterized by a [CardColour] and a [CardValue]
 */

class Card (var hidden: Boolean, val suit: CardColour, val value: CardValue){
    override fun toString() = "$hidden $suit $value"
}