package com.murrey.texasholdem.game.hand

import com.murrey.texasholdem.model.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class HandBuilderTest {

    @ParameterizedTest
    @MethodSource("getData")
    fun testHandBuilder(testData: TestData) {
        val actual = HandBuilder.buildHand(testData.cards)
        assertEquals(testData.expected, actual)
    }

    companion object {
        @JvmStatic
        fun getData(): List<TestData> {
            return listOf(
                // Royal Flush
                TestData(
                    cards = listOf(ACE_CLUBS, KING_CLUBS, QUEEN_CLUBS, JACK_CLUBS, TEN_CLUBS, NINE_CLUBS, EIGHT_CLUBS),
                    expected = ROYAL_FLUSH
                ),
                TestData(
                    cards = listOf(ACE_CLUBS, KING_CLUBS, QUEEN_CLUBS, JACK_CLUBS, TEN_CLUBS, TEN_DIAMONDS, ACE_HEARTS),
                    expected = ROYAL_FLUSH
                ),

                // Straight Flush
                TestData(
                    cards = listOf(KING_CLUBS, QUEEN_CLUBS, JACK_CLUBS, TEN_CLUBS, NINE_CLUBS, EIGHT_CLUBS, SEVEN_CLUBS),
                    expected = Hand(
                        handType = HandType.STRAIGHT_FLUSH,
                        highCard = CardValue.KING,
                        firstKicker = CardValue.QUEEN,
                        secondKicker = CardValue.JACK,
                        thirdKicker = CardValue.TEN,
                        fourthKicker = CardValue.NINE,
                    )
                ),
                TestData(
                    cards = listOf(NINE_DIAMONDS, EIGHT_DIAMONDS, SEVEN_DIAMONDS, SIX_DIAMONDS, FIVE_DIAMONDS, SIX_SPADES, EIGHT_HEARTS),
                    expected = Hand(
                        handType = HandType.STRAIGHT_FLUSH,
                        highCard = CardValue.NINE,
                        firstKicker = CardValue.EIGHT,
                        secondKicker = CardValue.SEVEN,
                        thirdKicker = CardValue.SIX,
                        fourthKicker = CardValue.FIVE,
                    )
                ),
                TestData(
                    cards = listOf(ACE_CLUBS, TWO_CLUBS, THREE_CLUBS, FOUR_CLUBS, FIVE_CLUBS, ACE_SPADES, ACE_HEARTS),
                    expected = Hand(
                        handType = HandType.STRAIGHT_FLUSH,
                        highCard = CardValue.FIVE,
                        firstKicker = CardValue.FOUR,
                        secondKicker = CardValue.THREE,
                        thirdKicker = CardValue.TWO,
                        fourthKicker = CardValue.ACE,
                    )
                ),

                // Four of a Kind
                TestData(
                    cards = listOf(FOUR_CLUBS, FOUR_DIAMONDS, FOUR_HEARTS, FOUR_SPADES, ACE_HEARTS, ACE_SPADES, ACE_CLUBS),
                    expected = Hand(
                        handType = HandType.FOUR_OF_A_KIND,
                        highCard = CardValue.FOUR,
                        firstKicker = CardValue.FOUR,
                        secondKicker = CardValue.FOUR,
                        thirdKicker = CardValue.FOUR,
                        fourthKicker = CardValue.ACE,
                    )
                ),
                TestData(
                    cards = listOf(FIVE_CLUBS, FIVE_DIAMONDS, FIVE_HEARTS, FIVE_SPADES, THREE_CLUBS, KING_DIAMONDS, QUEEN_CLUBS),
                    expected = Hand(
                        handType = HandType.FOUR_OF_A_KIND,
                        highCard = CardValue.FIVE,
                        firstKicker = CardValue.FIVE,
                        secondKicker = CardValue.FIVE,
                        thirdKicker = CardValue.FIVE,
                        fourthKicker = CardValue.KING,
                    )
                ),

                // Full House
                TestData(
                    cards = listOf(FOUR_CLUBS, FOUR_DIAMONDS, FOUR_HEARTS, EIGHT_HEARTS, EIGHT_CLUBS, EIGHT_SPADES, THREE_CLUBS),
                    expected = Hand(
                        handType = HandType.FULL_HOUSE,
                        highCard = CardValue.EIGHT,
                        firstKicker = CardValue.EIGHT,
                        secondKicker = CardValue.EIGHT,
                        thirdKicker = CardValue.FOUR,
                        fourthKicker = CardValue.FOUR,
                    )
                ),
                TestData(
                    cards = listOf(THREE_CLUBS, THREE_SPADES, THREE_HEARTS, TEN_CLUBS, TEN_HEARTS, KING_DIAMONDS, QUEEN_CLUBS),
                    expected = Hand(
                        handType = HandType.FULL_HOUSE,
                        highCard = CardValue.THREE,
                        firstKicker = CardValue.THREE,
                        secondKicker = CardValue.THREE,
                        thirdKicker = CardValue.TEN,
                        fourthKicker = CardValue.TEN,
                    )
                ),
            )
        }

        // Hearts
        private val ACE_HEARTS = Card(Suit.HEARTS, CardValue.ACE)
        private val KING_HEARTS = Card(Suit.HEARTS, CardValue.KING)
        private val QUEEN_HEARTS = Card(Suit.HEARTS, CardValue.QUEEN)
        private val JACK_HEARTS = Card(Suit.HEARTS, CardValue.JACK)
        private val TEN_HEARTS = Card(Suit.HEARTS, CardValue.TEN)
        private val NINE_HEARTS = Card(Suit.HEARTS, CardValue.NINE)
        private val EIGHT_HEARTS = Card(Suit.HEARTS, CardValue.EIGHT)
        private val SEVEN_HEARTS = Card(Suit.HEARTS, CardValue.SEVEN)
        private val SIX_HEARTS = Card(Suit.HEARTS, CardValue.SIX)
        private val FIVE_HEARTS = Card(Suit.HEARTS, CardValue.FIVE)
        private val FOUR_HEARTS = Card(Suit.HEARTS, CardValue.FOUR)
        private val THREE_HEARTS = Card(Suit.HEARTS, CardValue.THREE)
        private val TWO_HEARTS = Card(Suit.HEARTS, CardValue.TWO)

        // Spades
        private val ACE_SPADES = Card(Suit.SPADES, CardValue.ACE)
        private val KING_SPADES = Card(Suit.SPADES, CardValue.KING)
        private val QUEEN_SPADES = Card(Suit.SPADES, CardValue.QUEEN)
        private val JACK_SPADES = Card(Suit.SPADES, CardValue.JACK)
        private val TEN_SPADES = Card(Suit.SPADES, CardValue.TEN)
        private val NINE_SPADES = Card(Suit.SPADES, CardValue.NINE)
        private val EIGHT_SPADES = Card(Suit.SPADES, CardValue.EIGHT)
        private val SEVEN_SPADES = Card(Suit.SPADES, CardValue.SEVEN)
        private val SIX_SPADES = Card(Suit.SPADES, CardValue.SIX)
        private val FIVE_SPADES = Card(Suit.SPADES, CardValue.FIVE)
        private val FOUR_SPADES = Card(Suit.SPADES, CardValue.FOUR)
        private val THREE_SPADES = Card(Suit.SPADES, CardValue.THREE)
        private val TWO_SPADES = Card(Suit.SPADES, CardValue.TWO)

        // Clubs
        private val ACE_CLUBS = Card(Suit.CLUBS, CardValue.ACE)
        private val KING_CLUBS = Card(Suit.CLUBS, CardValue.KING)
        private val QUEEN_CLUBS = Card(Suit.CLUBS, CardValue.QUEEN)
        private val JACK_CLUBS = Card(Suit.CLUBS, CardValue.JACK)
        private val TEN_CLUBS = Card(Suit.CLUBS, CardValue.TEN)
        private val NINE_CLUBS = Card(Suit.CLUBS, CardValue.NINE)
        private val EIGHT_CLUBS = Card(Suit.CLUBS, CardValue.EIGHT)
        private val SEVEN_CLUBS = Card(Suit.CLUBS, CardValue.SEVEN)
        private val SIX_CLUBS = Card(Suit.CLUBS, CardValue.SIX)
        private val FIVE_CLUBS = Card(Suit.CLUBS, CardValue.FIVE)
        private val FOUR_CLUBS = Card(Suit.CLUBS, CardValue.FOUR)
        private val THREE_CLUBS = Card(Suit.CLUBS, CardValue.THREE)
        private val TWO_CLUBS = Card(Suit.CLUBS, CardValue.TWO)

        // Diamonds
        private val ACE_DIAMONDS = Card(Suit.DIAMONDS, CardValue.ACE)
        private val KING_DIAMONDS = Card(Suit.DIAMONDS, CardValue.KING)
        private val QUEEN_DIAMONDS = Card(Suit.DIAMONDS, CardValue.QUEEN)
        private val JACK_DIAMONDS = Card(Suit.DIAMONDS, CardValue.JACK)
        private val TEN_DIAMONDS = Card(Suit.DIAMONDS, CardValue.TEN)
        private val NINE_DIAMONDS = Card(Suit.DIAMONDS, CardValue.NINE)
        private val EIGHT_DIAMONDS = Card(Suit.DIAMONDS, CardValue.EIGHT)
        private val SEVEN_DIAMONDS = Card(Suit.DIAMONDS, CardValue.SEVEN)
        private val SIX_DIAMONDS = Card(Suit.DIAMONDS, CardValue.SIX)
        private val FIVE_DIAMONDS = Card(Suit.DIAMONDS, CardValue.FIVE)
        private val FOUR_DIAMONDS = Card(Suit.DIAMONDS, CardValue.FOUR)
        private val THREE_DIAMONDS = Card(Suit.DIAMONDS, CardValue.THREE)
        private val TWO_DIAMONDS = Card(Suit.DIAMONDS, CardValue.TWO)

        // Hands
        private val ROYAL_FLUSH = Hand(
            handType = HandType.ROYAL_FLUSH,
            highCard = CardValue.ACE,
            firstKicker = CardValue.KING,
            secondKicker = CardValue.QUEEN,
            thirdKicker = CardValue.JACK,
            fourthKicker = CardValue.TEN
        )
    }

    data class TestData(
        val cards: Cards,
        val expected: Hand
    )
}