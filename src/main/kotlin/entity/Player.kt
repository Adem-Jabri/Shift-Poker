package entity
/**
 * Entity to represent a player in the game "ShiftPoker".
 * Each player has a name, 3 revealedCards and 2 hidden cards
 *
 * @param name gives the name of the players in the game
 * @param revealedCards list of cards that will initially be revealed on the front of the player
 * @param hiddenCards list of cards that will initially be hidden on thefront of the player
 */

class Player(
    var name : String,
    revealedCards: List<Card>,
    hiddenCards: List<Card>
) {
}