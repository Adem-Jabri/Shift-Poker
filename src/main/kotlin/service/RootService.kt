package service

import entity.ShiftPokerGame
import view.Refreshable

/**
 * Main class of the service layer for the War card game. Provides access
 * to all other service classes and holds the [game] state for these
 * services to access.
 * the other service classes are:
 * @property PlayerService
 * @property ShiftPokerGameService
 * @property CardService
 */

class RootService {
    val playerService = PlayerService(this)

    val shiftPokerGameService = ShiftPokerGameService(this)
    val cardService = CardService(this)
    /**
     * The currently active game. Can be `null`, if no game has started yet.
     */
    var game: ShiftPokerGame? = null

    /**
     * takes a list of refreshables
     */
    fun addRefreshables(vararg newRefreshables: Refreshable) {
        newRefreshables.forEach { addRefreshable(it) }
    }

    /**
     * each element of the list given to the method [addRefreshables] will be given to this method
     */
    private fun addRefreshable(newRefreshable: Refreshable) {
        shiftPokerGameService.addRefreshable(newRefreshable)
        playerService.addRefreshable(newRefreshable)
        cardService.addRefreshable(newRefreshable)
    }
}
