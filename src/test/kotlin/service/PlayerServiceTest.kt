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
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        assertNotNull(game)
        assertFailsWith<IllegalArgumentException> { playerService.shiftCards(2) }
        val oldShiftDeck = game.shiftDeck
        val drawnCard = game.drawPile[0]
        drawnCard.hidden = false
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
    }

    /**
     * test the method showCardsTest
     */
    @Test
    fun showCardsTest() {
        val gameService = rootService.shiftPokerGameService
        val playerService = rootService.playerService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }

        rootService.playerService.showCards()
        assertNotNull(game)
        assertEquals(false, game.playerList[game.activePlayer].hiddenCards[0].hidden)
        assertEquals(false, game.playerList[game.activePlayer].hiddenCards[1].hidden)
    }

    /**
     * test for the method swapOne
     */
    @Test
    fun swapOneTest(){
        val gameService = rootService.shiftPokerGameService
        val playerService = rootService.playerService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }

        assertFailsWith<IllegalArgumentException> { playerService.swapOne(3, 2) }
        playerService.shiftCards(1)
        assertNotNull(game)
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(2, 3) }
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(1, 3) }


        val newShiftDeck = game.shiftDeck
        val revealedCards = game.playerList[game.activePlayer].revealedCards
        val expectedShiftDeck = mutableListOf(newShiftDeck[0], newShiftDeck[1], revealedCards[0])
        playerService.swapOne(2,0)
        assertEquals(expectedShiftDeck, game.shiftDeck)

        playerService.swapped = true
        assertFailsWith<IllegalArgumentException> { playerService.swapOne(2, 1) }
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

        assertFailsWith<IllegalArgumentException> { playerService.swapAll() }

        playerService.shiftCards(1)
        val newShiftDeck = game.shiftDeck
        val expectedShiftDeck = game.playerList[game.activePlayer].revealedCards
        playerService.swapAll()
        assertEquals(expectedShiftDeck, game.shiftDeck)
        assertNotNull(game)
        playerService.swapped = true
        assertFailsWith<IllegalArgumentException> { playerService.swapAll() }
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

        assertFailsWith<IllegalArgumentException> { playerService.pass() }
        playerService.shiftCards(1)
        playerService.pass()
        assertNotNull(game)
        assertEquals(1, game.activePlayer)
    }

}