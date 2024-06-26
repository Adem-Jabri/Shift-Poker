package entity

/**
 * Entity to represent a player in the game "ShiftPoker".
 * Each player has a name, 3 revealedCards and 2 hidden cards, which are initially empty.
 *
 * @param name gives the name of the players in the game
 */

data class Player(val name: String) {

    var revealedCards: MutableList<Card> = mutableListOf()
    var hiddenCards: MutableList<Card> = mutableListOf()

    /**
     * provide a string to represent the Card.
     */
    override fun toString(): String = "$name: H$hiddenCards R$revealedCards"

}