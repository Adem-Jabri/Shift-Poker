package entity
import kotlin.test.*
class PlayerTest {
    val firstCard = Card(false, CardColour.DIAMONDS, CardValue.SEVEN)
    val secondCard = Card(false, CardColour.CLUBS, CardValue.KING)
    val thirdCard = Card(false, CardColour.DIAMONDS, CardValue.QUEEN)
    val forthCard = Card(true, CardColour.HEARTS, CardValue.QUEEN)
    val fifthCard = Card(true, CardColour.HEARTS, CardValue.KING)

    // create test data
    val inputName = "reus"
    val inputHiddenCards = mutableListOf(forthCard, fifthCard)
    val inputRevealedCards = mutableListOf(firstCard, secondCard, thirdCard)

    /**
     * check whether the attributes have been set correctly
     */
    @Test
    fun case(){
        // the class will be tested with test data
        val reus = Player(inputName)
        reus.hiddenCards = inputHiddenCards
        reus.revealedCards = inputRevealedCards

        assertEquals(inputName, reus.name)
        assertEquals(inputHiddenCards, reus.hiddenCards)
        assertEquals(inputRevealedCards, reus.revealedCards)
    }
    /**
     * Check if to String produces the correct strings for some test cards
     * of all four colours.
     */
    @Test
    fun testToString() {
        // the method will be tested with this test data
        val lewa = Player("lewa")
        lewa.hiddenCards = inputHiddenCards
        lewa.revealedCards = inputRevealedCards
        val lewaToString = "lewa: H$inputHiddenCards R$inputRevealedCards"
        // check whether the method is working correctly
        assertEquals(lewaToString, lewa.toString())
    }

}