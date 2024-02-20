package entity
import kotlin.test.*
class CardTest {
    // create test data
    val inputCardColour = CardColour.HEARTS
    val inputCardValue = CardValue.ACE
    val inputState : Boolean = false
    val inputCardColour2 = CardColour.DIAMONDS
    val inputCardValue2 = CardValue.FIVE
    val inputState2 : Boolean = true
    // the class will be tested with test data
    val testCard = Card(inputState, inputCardColour, inputCardValue)
    val testCard2 = Card(inputState2, inputCardColour2, inputCardValue2)

    /**
     * check whether the attributes have been set correctly
     */
    @Test
    fun case(){
        assertEquals(inputState, testCard.hidden)
        assertEquals(inputCardColour, testCard.suit)
        assertEquals(inputCardValue, testCard.value)

        assertEquals(inputState2, testCard2.hidden)
        assertEquals(inputCardColour2, testCard2.suit)
        assertEquals(inputCardValue2, testCard2.value)
    }
    /**
     * Check if to String produces the correct strings for some test cards
     * of all four suits.
     */
    @Test
    fun testToString() {
        // the method will be tested with this test data
        val realToStringCard = "$inputState $inputCardColour $inputCardValue"
        val realToStringCard2 = "$inputState2 $inputCardColour2 $inputCardValue2"
        // check whether the method is working correctly
        assertEquals(realToStringCard, testCard.toString())
        assertEquals(realToStringCard2, testCard2.toString())

    }


}