package service

/**
 * Service layer class that provides the logic for the actions a player
 * can take.
 */

class PlayerService(private val rootService: RootService) : AbstractRefreshingService() {
    var shifted: Boolean = false
    var swapped: Boolean = false

    /**
     * works only if it will be the first shift action
     * shifts whether right or left
     * @param direction
     */
    fun shiftCards(direction: Int) {
        require(!shifted) {
            "You can shift only one time"
        }
        require(direction == -1 || direction == 1) {
            "direction must be either -1 to shift to the left or 1 to shift to the right"
        }
        if (direction == -1) {
            shiftCardsToTheLeft()
        } else shiftCardsToTheRight()
    }

    /**
     * makes shifted true, so that no other shift action can be made
     * takes a card from the draw pile, makes it revealed and puts it on the right edge
     * of the shiftDeck (which will automatically shift the cards in the middle to the left)
     * and then it will remove the last card on the left edge of the shiftDeck and puts it
     * in the discardPile
     */
    private fun shiftCardsToTheLeft() {
        shifted = true
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        val drawnCard = rootService.shiftPokerGameService.drawCard()
        drawnCard.hidden = false
        game.shiftDeck.add(drawnCard)
        val discardCard = game.shiftDeck[0]
        game.discardPile.add(discardCard)
        game.shiftDeck.removeAt(0)
    }

    /**
     * makes shifted true, so that no other shift action can be made
     * takes a card from the draw pile, makes it revealed and puts it in the left edge
     * of the shiftDeck (which will automatically shift the cards in the middle to the right)
     * and then it will remove the last card on the right edge of the shiftDeck and puts it
     * in the discardPile
     */
    private fun shiftCardsToTheRight() {
        shifted = true
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        val drawnCard = rootService.shiftPokerGameService.drawCard()
        drawnCard.hidden = false
        game.shiftDeck.add(0, drawnCard)
        val discardCard = game.shiftDeck[game.shiftDeck.size - 1]
        game.discardPile.add(discardCard)
        game.shiftDeck.removeAt(game.shiftDeck.size - 1)
    }

    /**
     * the active player can see his hidden cards
     */
    fun showCards() {
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        rootService.cardService.reveal()
    }

    /**
     * it works only if the active player is making his first swap action
     * it will swap the chosen card in the shift deck with the chosen revealed card of the active player
     * @param deckIndex is the index of the chosen card in the shift deck
     * @param revealedIndex is the index of the  chosen revealed card of the active player
     */
    fun swapOne(deckIndex: Int, revealedIndex: Int) {
        require(shifted){
            "you cannot swap before shifting"
        }
        require(!swapped) {
            "you can swap only one time"
        }
        require(deckIndex in 0..2) {
            "shiftDeck contains only 3 Cards"
        }
        require(revealedIndex in 0..2) {
            "Each player has only 3 revealed Cards"
        }

        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        val temp = game.shiftDeck[deckIndex]
        game.shiftDeck[deckIndex] = game.playerList[game.activePlayer].revealedCards[revealedIndex]
        game.playerList[game.activePlayer].revealedCards[revealedIndex] = temp

        rootService.shiftPokerGameService.nextPlayer()


    }

    /**
     * it works only if the active player is making his first swap action
     * and it will swap the cards in the shift deck with the revealed cards of the active player
     */
    fun swapAll() {
        require(shifted){
            "you cannot swap before shifting"
        }
        require(!swapped) {
            "you can swap only once"
        }

        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        val temp = game.shiftDeck
        game.shiftDeck = game.playerList[game.activePlayer].revealedCards
        game.playerList[game.activePlayer].revealedCards = temp

        rootService.shiftPokerGameService.nextPlayer()
    }

    /**
     * it will work only if the active player already has made a shift action
     * it will hide the hidden cards of the active player and gives the turn to the next player
     */
    fun pass() {
        require(shifted) {
            "you cannot pass before shifting"
        }
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        rootService.shiftPokerGameService.nextPlayer()
    }
}