package entity
import kotlin.test.*
/**
 * test for the class [Card]
 */
class CardTest {
    // create test data
    private val inputCardColour = CardColour.HEARTS
    private val inputCardValue = CardValue.ACE
    private val inputState : Boolean = false
    private val inputCardColour2 = CardColour.DIAMONDS
    private val inputCardValue2 = CardValue.FIVE
    private val inputState2 : Boolean = true
    // the class will be tested with test data
    private val testCard = Card(inputState, inputCardColour, inputCardValue)
    private val testCard2 = Card(inputState2, inputCardColour2, inputCardValue2)

    /**
     * check whether the attributes have been set correctly
     */
    @Test
    fun createCardTest(){
        assertEquals(inputState, testCard.hidden)
        assertEquals(inputCardColour, testCard.cardColour)
        assertEquals(inputCardValue, testCard.value)

        assertEquals(inputState2, testCard2.hidden)
        assertEquals(inputCardColour2, testCard2.cardColour)
        assertEquals(inputCardValue2, testCard2.value)
    }
    /**
     * Check if to String produces the correct strings for some test cards
     * of all four colours.
     */
    @Test
    fun testToString() {
        // the method will be tested with this test data
        val realToStringCard = "$inputCardColour $inputCardValue"
        val realToStringCard2 = "$inputCardColour2 $inputCardValue2"
        // check whether the method is working correctly
        assertEquals(realToStringCard, testCard.toString())
        assertEquals(realToStringCard2, testCard2.toString())

    }


}