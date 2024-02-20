package entity
import kotlin.test.*
class CardTest {
    // create test data
    val inputCardSuit = CardSuit.HEARTS
    val inputCardValue = CardValue.ACE
    val inputState : Boolean = false
    val inputCardSuit2 = CardSuit.DIAMONDS
    val inputCardValue2 = CardValue.FIVE
    val inputState2 : Boolean = true
    // the class will be tested with test data
    val testCard = Card(inputState, inputCardSuit, inputCardValue)
    val testCard2 = Card(inputState2, inputCardSuit2, inputCardValue2)

    /**
     * check whether the attributes have been set correctly
     */
    @Test
    fun case(){
        assertEquals(inputState, testCard.hidden)
        assertEquals(inputCardSuit, testCard.suit)
        assertEquals(inputCardValue, testCard.value)

        assertEquals(inputState2, testCard2.hidden)
        assertEquals(inputCardSuit2, testCard2.suit)
        assertEquals(inputCardValue2, testCard2.value)
    }
    /**
     * Check if to String produces the correct strings for some test cards
     * of all four suits.
     */
    @Test
    fun testToString() {
        // the method will be tested with this test data
        val realToStringCard = "$inputState $inputCardSuit $inputCardValue"
        val realToStringCard2 = "$inputState2 $inputCardSuit2 $inputCardValue2"
        // check whether the method is working correctly
        assertEquals(realToStringCard, testCard.toString())
        assertEquals(realToStringCard2, testCard2.toString())

    }


}