package entity
/**
 * Data class for the single type of game elements that the game "ShiftPoker" knows: cards.
 * the card has a state, whether hidden or revealed and its given by the param:
 * @param hidden
 * It is characterized by a [CardSuit] and a [CardValue]
 */

class Card (var hidden: Boolean, val value: CardValue, val suit: CardSuit){
    override fun toString() = "$suit$value"
    /**
     * compares two [Card]s according to the [Enum.ordinal] value of their [CardSuit]
     * (i.e., the order in which the suits are declared in the enum class)
     */
    operator fun compareTo(other: Card) = this.value.ordinal - other.value.ordinal

}