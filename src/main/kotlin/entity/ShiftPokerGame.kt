package entity
/**
 * Entity class that represents a game state of "ShiftPoker". This class is just a wrapper
 * for the player objects through [playerList] and for the cards in [shiftDeck], in [drawPile] and in [discardPile].
 * the active player will be given by the argument [activePlayer]
 * and the number of rounds will be given by the argument [roundCount].
 *
 */

class ShiftPokerGame ( var roundCount: Int = 2, var activePlayer: Int ) {

    var playerList: MutableList<Player> = mutableListOf<Player>()
    var shiftDeck: MutableList<Card> = mutableListOf<Card>()
    var drawPile: MutableList<Card> = mutableListOf<Card>()
    var discardPile: MutableList<Card> = mutableListOf<Card>()

}