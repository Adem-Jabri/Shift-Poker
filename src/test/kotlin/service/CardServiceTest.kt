package service
import entity.*
import kotlin.test.*

/**
 * class to test the class [CardService]
 */
class CardServiceTest {
    val rootService = RootService()
    val reus = Player("reus")
    val pique = Player("pique")
    var playerList = mutableListOf(reus, pique)
    /**
     * test for the method createShuffleDeck
     */
    @Test
    fun createShuffleDeckTest(){
        val cardService = rootService.cardService
        val drawPileTest = cardService.createShuffleDeck()
        // check if 52 different cards have been created
        assertEquals(52, drawPileTest.distinct().size)
    }

    /**
     * test for the method reveal()
     */
    @Test
    fun revealTest(){
        val cardService = rootService.cardService
        val gameService = rootService.shiftPokerGameService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        // cards are already hidden, so reveal the directly
        cardService.reveal()
        assertEquals(false, game.playerList[game.activePlayer].hiddenCards[0].hidden)
        assertEquals(false, game.playerList[game.activePlayer].hiddenCards[1].hidden)
    }
    /**
     * test for the method hide()
     */
    @Test
    fun hideTest(){
        val cardService = rootService.cardService
        val gameService = rootService.shiftPokerGameService
        gameService.startGame(2, playerList)
        val game = rootService.game
        checkNotNull(game) { "No game currently running." }
        // reveal all the cards
        for(card in game.playerList[game.activePlayer].hiddenCards){
            card.hidden = false
        }
        // call hide() function
        cardService.hide()
        assertEquals(true, game.playerList[game.activePlayer].hiddenCards[0].hidden)
        assertEquals(true, game.playerList[game.activePlayer].hiddenCards[1].hidden)
    }
}