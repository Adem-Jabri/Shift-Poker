package service

import entity.ShiftPokerGame

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
}