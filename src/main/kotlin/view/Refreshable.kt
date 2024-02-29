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

    fun refreshAfterFlip(hiddenCards: List<Card>){}

    fun refreshAfterSwap(swapAll: Boolean){}

    fun refreshAfterShift(direction: Int){}

    fun refreshAfterNextPlayer(){}
    /**
     * perform refreshes that are necessary after the last round was played
     */
    fun refreshAfterEndGame(ranking: MutableList<Pair<List<Player>, String>>) {}



}
