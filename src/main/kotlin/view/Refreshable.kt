package view

import service.*
import entity.*


/**
 * This interface provides a mechanism for the service layer classes to communicate
 * (usually to the view classes) that certain changes have been made to the entity
 * layer, so that the user interface can be updated accordingly.
 *
 * Default (empty) implementations are provided for all methods, so that implementing UI classes
 * only need to react to events relevant to them.
 *
 * @see AbstractRefreshingService
 *
 */
interface Refreshable {

    /**
     * perform refreshes that are necessary after a new game started
     */
    fun refreshAfterStartGame() {}

    /**
     * perform refreshes that are necessary after a Flip
     */
    fun refreshAfterFlip(hiddenCards: List<Card>){}

    /**
     * perform refreshes that are necessary after a swap
     */
    fun refreshAfterSwap(swapAll: Boolean){}

    /**
     * perform refreshes that are necessary after a shift
     */
    fun refreshAfterShift(direction: Int){}

    /**
     * perform refreshes that are necessary after a nextPlayer() call
     */
    fun refreshAfterNextPlayer(){}

    /**
     * perform refreshes that are necessary after End game
     */
    fun refreshAfterEndGame(ranking: MutableList<Pair<List<Player>, String>>) {}



}
