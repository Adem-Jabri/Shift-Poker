package service

import entity.*
import kotlin.test.*

/**
 * test for the class [PlayerService]
 */
class PlayerServiceTest {
    val rootService = RootService()
    val reus = Player("reus")
    val pique = Player("pique")
    var playerList = mutableListOf(reus, pique)

    /**
     * test the method shiftCards
     */
    @Test
    fun shiftCardsTest() {
        val gameService = rootService.shiftPokerGameService
        val playerService = rootService.playerService
        // start Game
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        assertNotNull(game)
        // invalid attribute
        assertFailsWith<IllegalArgumentException> { playerService.shiftCards(2) }

        val oldShiftDeck = game.shiftDeck
        val drawnCard = game.drawPile[0]
        drawnCard.hidden = false  // so that the player can shift
        val expectedNewShiftDeckAfterShiftToTheRight = mutableListOf(drawnCard, oldShiftDeck[0], oldShiftDeck[1])
        playerService.shiftCards(1)

        assertEquals(expectedNewShiftDeckAfterShiftToTheRight, game.shiftDeck)

        // we should only shift once
        assertFailsWith<IllegalArgumentException> { playerService.shiftCards(-1) }

        // give the turn to the next player, so that he can shift one more time
        rootService.shiftPokerGameService.nextPlayer()

        val oldShiftDeck2 = game.shiftDeck
        val drawnCard2 = game.drawPile[0]
        drawnCard2.hidden = false
        val expectedNewShiftDeckAfterShiftToTheLeft = mutableListOf(oldShiftDeck2[1], oldShiftDeck2[2], drawnCard2)
        playerService.shiftCards(-1)
        assertEquals(expectedNewShiftDeckAfterShiftToTheLeft, game.shiftDeck)

        // check what happens if game is null, shifted must be false to pass the first requirement
        playerService.shifted = false
        rootService.game = null
        assertFailsWith<IllegalStateException> { rootService.playerService.shiftCards(-1) }
        playerService.shifted = false
        assertFailsWith<IllegalStateException> { rootService.playerService.shiftCards(1)}
    }

    /**
     * test the method showCardsTest
     */
    @Test
    fun showCardsTest() {
        val gameService = rootService.shiftPokerGameService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }

        rootService.playerService.showCards()
        assertNotNull(game)
        // check whether the private cards are know revealed for the active player
        assertEquals(false, game.playerList[game.activePlayer].hiddenCards[0].hidden)
        assertEquals(false, game.playerList[game.activePlayer].hiddenCards[1].hidden)
        // case game is null
        rootService.game = null
        assertFailsWith<IllegalStateException> { rootService.playerService.showCards() }
    }

    /**
     * test for the method swapOne()
     */
    @Test
    fun swapOneTest(){
        val gameService = rootService.shiftPokerGameService
        val playerService = rootService.playerService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        // must shift first
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(3, 2) }
        playerService.shiftCards(1)
        // invalid parameters
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(2, 3) }
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(3, 1) }
        // check if the swap works correctly
        val newShiftDeck = game.shiftDeck
        val revealedCards = game.playerList[game.activePlayer].revealedCards
        val expectedShiftDeck = mutableListOf(newShiftDeck[0], newShiftDeck[1], revealedCards[0])
        playerService.swapOne(2,0)
        assertEquals(expectedShiftDeck, game.shiftDeck)
        // swap only once
        playerService.swapped = true
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(2, 1) }

        playerService.swapped = true
        playerService.shifted = true
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(2,2) }
        // case game is null, swapped must be false to pass the first requirement
        playerService.swapped = false
        rootService.game = null
        assertFailsWith<IllegalStateException> { rootService.playerService.swapOne(2,2) }
    }
    /**
     * test for the method swapOne
     */
    @Test
    fun swapAllTest(){
        val gameService = rootService.shiftPokerGameService
        val playerService = rootService.playerService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        //must shift first
        assertFailsWith<IllegalArgumentException> { playerService.swapAll() }
        // now the player shifts
        playerService.shiftCards(1)
        // checks whether the cards will be swapped correctly
        val expectedShiftDeck = game.playerList[game.activePlayer].revealedCards
        playerService.swapAll()
        assertEquals(expectedShiftDeck, game.shiftDeck)
        assertNotNull(game)
        // swap only once
        playerService.swapped = true
        playerService.shifted = true
        assertFailsWith<IllegalArgumentException> { playerService.swapAll() }
        // case game is null
        playerService.swapped = false
        rootService.game = null
        assertFailsWith<IllegalStateException> { rootService.playerService.swapAll() }
    }
    /**
     * test for the Method swap()
     */
    @Test
    fun passTest(){
        val gameService = rootService.shiftPokerGameService
        val playerService = rootService.playerService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        // must shift first
        assertFailsWith<IllegalArgumentException> { playerService.pass() }
        // active Player shifts
        playerService.shiftCards(1)
        playerService.pass()
        assertNotNull(game)
        // case game is null
        playerService.shifted = true
        rootService.game = null
        assertFailsWith<IllegalStateException> { rootService.playerService.pass() }
    }

}