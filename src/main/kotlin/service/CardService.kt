package service

import entity.*

/**
 * Service layer class that provides the logic for the operations made on the cards
 */
class CardService(val rootService: RootService) : AbstractRefreshingService() {
    /**
     * Creates a shuffled 52 cards list of all four suits and cards
     * from 2 to Ace
     */
    fun createShuffleDeck() = MutableList(52) { index ->
        Card(
            true,
            CardColour.values()[index / 13], // There are 13 cards per suit
            CardValue.values()[index % 13] // There are 13 different values
        )
    }.shuffled().toMutableList()

    /**
     * makes the hidden Cards of the active player again hidden
     */
    fun hide() {
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        for (card in game.playerList[game.activePlayer].hiddenCards) {
            card.hidden = true
        }
    }

    /**
     * makes the hidden Cards of the active player revealed
     */
    fun reveal() {
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        for (card in game.playerList[game.activePlayer].hiddenCards) {
            card.hidden = false
        }
    }
}