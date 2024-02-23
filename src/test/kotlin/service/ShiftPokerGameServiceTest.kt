package service
import entity.*
import kotlin.test.*

/**
 * test for the class [ShiftPokerGameService]
 */
class ShiftPokerGameServiceTest {
    val rootService = RootService()

    // create test data
    private val firstCard = Card(false, CardColour.DIAMONDS, CardValue.ACE)
    private val secondCard = Card(false, CardColour.CLUBS, CardValue.TWO)
    private val thirdCard = Card(false, CardColour.DIAMONDS, CardValue.THREE)
    private val forthCard = Card(true, CardColour.SPADES, CardValue.FOUR)
    private val fifthCard = Card(true, CardColour.DIAMONDS, CardValue.FIVE)
    private val hiddenCards = mutableListOf(forthCard, fifthCard)
    private val revealedCards = mutableListOf(firstCard, secondCard, thirdCard)

    private val firstCard2 = Card(false, CardColour.DIAMONDS, CardValue.NINE)
    private val secondCard2 = Card(false, CardColour.DIAMONDS, CardValue.SEVEN)
    private val thirdCard2 = Card(false, CardColour.DIAMONDS, CardValue.EIGHT)
    private val forthCard2 = Card(true, CardColour.DIAMONDS, CardValue.SIX)
    private val fifthCard2 = Card(true, CardColour.DIAMONDS, CardValue.FIVE)
    private val hiddenCards2 = mutableListOf(forthCard2, fifthCard2)
    private val revealedCards2 = mutableListOf(firstCard2, secondCard2, thirdCard2)
    val reus = Player("reus")
    val pique = Player("pique")
    var playerList = mutableListOf(reus, pique)
    //val lewa = Player("lewa")
    /**
     * test startGame method by giving fir invalid attributes, and then test
     * whether the game is started correctly and whether the Cards are correctly
     * distributed.
     */
    @Test
    fun evaluateStartGame(){
        val gameService = rootService.shiftPokerGameService
        assertFailsWith<IllegalArgumentException> { gameService.startGame(1,  mutableListOf(pique, reus)) }
        assertFailsWith<IllegalArgumentException> { gameService.startGame(2,  mutableListOf(pique)) }
        gameService.startGame(2, mutableListOf(pique, reus))
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        assertEquals(2, game.roundCount)
        assertEquals(2, game.playerList.size)
        assertEquals(3, game.playerList[0].revealedCards.size )
        assertEquals(2, game.playerList[0].hiddenCards.size )
        assertEquals(3, game.playerList[1].revealedCards.size )
        assertEquals(2, game.playerList[1].hiddenCards.size )
        assertEquals(3, game.shiftDeck.size)
        //after distributing the cards, it will be 39 cards remaining in the drawPile
        assertEquals(39, game.drawPile.size )
    }

    /**
     * tests whether the method nextPlayer works correctly
     */
    @Test
    fun nextPlayerTest(){
        val gameService = rootService.shiftPokerGameService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        gameService.nextPlayer()
        assertEquals(1, game.activePlayer)
        gameService.nextPlayer()
        assertEquals(0, game.activePlayer)
        game.roundCount = 0
        gameService.nextPlayer()
        assertNull(rootService.game)
    }

    @Test
    fun endGameTest(){
        val gameService = rootService.shiftPokerGameService
        gameService.startGame(2, playerList)
        rootService.playerService.shifted = false
        rootService.playerService.swapped = false
        assertNotNull(rootService.game)
        gameService.endGame()
        assertNull(rootService.game)
    }
    /**
     * test for evaluateGameMethod
     */
    @Test
    fun evaluateGameTest(){
        val gameService = rootService.shiftPokerGameService
        gameService.startGame(3, mutableListOf(reus, pique))
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        // change the cards of each player, otherwise they will be randomly generated
        game.playerList[0].hiddenCards = hiddenCards
        game.playerList[0].revealedCards = revealedCards
        game.playerList[1].hiddenCards = hiddenCards2
        game.playerList[1].revealedCards = revealedCards2
        val test = gameService.evaluateGame()
        print(test)
        val expected = listOf( Pair(listOf(pique), "Straight Flush"), Pair(listOf(reus), "Straight"))
        assertEquals(expected, test)
    }

}