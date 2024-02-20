package entity
import kotlin.test.*

class ShiftPokerGameTest {
    /**
     * check whether the attributes have been set correctly
     */
    @Test
    fun case(){
        // create test data
        val reus = Player("reus")
        val lewa = Player("lewa")
        val firstCard = Card(false, CardColour.DIAMONDS, CardValue.SEVEN)
        val secondCard = Card(false, CardColour.CLUBS, CardValue.KING)
        val thirdCard = Card(false, CardColour.DIAMONDS, CardValue.QUEEN)
        val forthCard = Card(true, CardColour.HEARTS, CardValue.QUEEN)
        val fifthCard = Card(true, CardColour.HEARTS, CardValue.KING)
        val sixthCard = Card(true, CardColour.HEARTS, CardValue.NINE)
        val seventhCard = Card(true, CardColour.HEARTS, CardValue.EIGHT)
        val inputRoundCount = 5
        val inputActivePlayer = 1

        // the class will be tested with test data
        val newGame = ShiftPokerGame(inputRoundCount, inputActivePlayer)
        newGame.playerList = mutableListOf(reus, lewa)
        newGame.shiftDeck = mutableListOf(firstCard, secondCard, thirdCard)
        newGame.drawPile = mutableListOf(forthCard, fifthCard, sixthCard, seventhCard)
        newGame.discardPile = mutableListOf(secondCard, thirdCard)

        assertEquals(inputRoundCount, newGame.roundCount)
        assertEquals(inputActivePlayer, newGame.activePlayer)
        assertEquals(reus.toString(), newGame.playerList[0].toString() )
        assertEquals(lewa.toString(), newGame.playerList[1].toString() )

        assertEquals(firstCard.toString(), newGame.shiftDeck[0].toString())
        assertEquals(secondCard.toString(), newGame.shiftDeck[1].toString())
        assertEquals(thirdCard.toString(), newGame.shiftDeck[2].toString())

        assertEquals(forthCard.toString(), newGame.drawPile[0].toString())
        assertEquals(fifthCard.toString(), newGame.drawPile[1].toString())
        assertEquals(sixthCard.toString(), newGame.drawPile[2].toString())
        assertEquals(seventhCard.toString(), newGame.drawPile[3].toString())

        assertEquals(secondCard.toString(), newGame.discardPile[0].toString())
        assertEquals(thirdCard.toString(), newGame.discardPile[1].toString())
    }

}