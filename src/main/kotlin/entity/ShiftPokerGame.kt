package entity
/**
 * Entity class that represents a game state of "ShiftPoker". This class is just a wrapper
 * for the player objects and for the cards in shift Deck, in drawPile and in discardPile.
 * the active player will be given by the parameter [activePlayer]
 * @param roundCount
 * @param activePlayer
 * @param playerList
 * @param shiftDeck
 * @param drawPile
 * @param discardPile
 */

class ShiftPokerGame (
    var roundCount: Int = 2,
    var activePlayer: Int,
    playerList: List<Player> = emptyList(),
    shiftDeck: List<Card> = emptyList(),
    drawPile: List<Card> = emptyList(),
    discardPile: List<Card>  ) {
}