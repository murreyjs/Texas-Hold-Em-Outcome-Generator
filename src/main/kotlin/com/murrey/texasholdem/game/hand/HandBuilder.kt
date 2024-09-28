package com.murrey.texasholdem.game.hand

import com.murrey.texasholdem.model.*
import com.murrey.texasholdem.util.findValuesByOccurrence
import com.murrey.texasholdem.util.removeMaxValue

/**
 * Object containing functions related to building a [Hand] from a list of [Card]s.
 */
object HandBuilder {
    private val aceLowStraight = listOf(CardValue.ACE, CardValue.TWO, CardValue.THREE, CardValue.FOUR, CardValue.FIVE)

    /**
     * Builds a [Hand] from the provided list of [Card]s.
     *
     * @param cards the list of [Card]s to build a [Hand] out of.
     * @return the best [Hand] that can be built using the provided [Card]s.
     */
    fun buildHand(cards: Cards): Hand {
        val mutableCards = cards.toMutableList()
        return when (HandTypeEvaluator.evaluateHandType(mutableCards)) {
            HandType.ROYAL_FLUSH -> buildRoyalFlush()
            HandType.STRAIGHT_FLUSH -> buildStraightFlush(mutableCards)
            HandType.FOUR_OF_A_KIND -> buildFourOfAKind(mutableCards)
            HandType.FULL_HOUSE -> buildFullHouse(mutableCards)
            HandType.FLUSH -> buildFlush(mutableCards)
            HandType.STRAIGHT -> buildStraight(mutableCards)
            HandType.THREE_OF_A_KIND -> buildThreeOfAKind(mutableCards)
            HandType.TWO_PAIR -> buildTwoPair(mutableCards)
            HandType.ONE_PAIR -> buildOnePair(mutableCards)
            HandType.HIGH_CARD -> buildHighCard(mutableCards)
        }
    }

    /**
     * Builds a Royal Flush [Hand].
     *
     * @return a [Hand] representing a Royal Flush.
     */
    private fun buildRoyalFlush(): Hand {
        return Hand(
            handType = HandType.ROYAL_FLUSH,
            highCard = CardValue.ACE,
            firstKicker = CardValue.KING,
            secondKicker = CardValue.QUEEN,
            thirdKicker = CardValue.JACK,
            fourthKicker = CardValue.TEN
        )
    }

    /**
     * Builds a Straight Flush [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Straight Flush.
     */
    private fun buildStraightFlush(cards: MutableCards): Hand {
        val straight = getStraight(cards, true)
        return buildHand(straight, HandType.STRAIGHT_FLUSH)
    }

    /**
     * Builds a Four of a Kind [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing Four of a Kind.
     */
    private fun buildFourOfAKind(cards: MutableCards): Hand {
        val highCard = cards.findValuesByOccurrence(4).max()
        cards.removeAll { it.value == highCard }
        return Hand(
            handType = HandType.FOUR_OF_A_KIND,
            highCard = highCard,
            firstKicker = highCard,
            secondKicker = highCard,
            thirdKicker = highCard,
            fourthKicker = cards.removeMaxValue()
        )
    }

    /**
     * Builds a Full House [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Full House.
     */
    private fun buildFullHouse(cards: MutableCards): Hand {
        val threeOfAKindValue = cards.findValuesByOccurrence(3).max()
        val pairValue = cards.findValuesByOccurrence(2).max()
        val threeGreaterThanTwo = threeOfAKindValue.ordinal > pairValue.ordinal
        val highCard = if (threeGreaterThanTwo) threeOfAKindValue else pairValue
        val kicker = if (threeGreaterThanTwo) pairValue else threeOfAKindValue
        return Hand(
            handType = HandType.FULL_HOUSE,
            highCard = highCard,
            firstKicker = highCard,
            secondKicker = highCard,
            thirdKicker = kicker,
            fourthKicker = kicker
        )
    }

    /**
     * Builds a Flush [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Flush.
     */
    private fun buildFlush(cards: MutableCards): Hand {
        val flush = getFlush(cards)
        return buildHand(flush, HandType.FLUSH)
    }

    /**
     * Builds a Straight [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Straight.
     */
    private fun buildStraight(cards: MutableCards): Hand {
        val straight = getStraight(cards)
        return buildHand(straight, HandType.STRAIGHT)
    }

    /**
     * Builds a Three of a Kind [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing Three of a Kind.
     */
    private fun buildThreeOfAKind(cards: MutableCards): Hand {
        val highCard = cards.findValuesByOccurrence(3).first()
        cards.removeAll { it.value == highCard }
        return Hand(
            handType = HandType.THREE_OF_A_KIND,
            highCard = highCard,
            firstKicker = highCard,
            secondKicker = highCard,
            thirdKicker = cards.removeMaxValue(),
            fourthKicker = cards.removeMaxValue()
        )
    }

    /**
     * Builds a Two Pair [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing Two Pair.
     */
    private fun buildTwoPair(cards: MutableCards): Hand {
        val pairs = cards.findValuesByOccurrence(2)
        val highPair = pairs.maxBy { it }
        val lowPair = pairs.minBy { it }
        cards.removeAll { it.value == highPair || it.value == lowPair }
        return Hand(
            handType = HandType.TWO_PAIR,
            highCard = highPair,
            firstKicker = highPair,
            secondKicker = lowPair,
            thirdKicker = lowPair,
            fourthKicker = cards.removeMaxValue()
        )
    }

    /**
     * Builds a One Pair [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a One Pair.
     */
    private fun buildOnePair(cards: MutableCards): Hand {
        val pair = cards.findValuesByOccurrence(2).first()
        cards.removeAll { it.value == pair }
        return Hand(
            handType = HandType.ONE_PAIR,
            highCard = pair,
            firstKicker = pair,
            secondKicker = cards.removeMaxValue(),
            thirdKicker = cards.removeMaxValue(),
            fourthKicker = cards.removeMaxValue(),
        )
    }

    /**
     * Builds a High Card [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing High Card.
     */
    private fun buildHighCard(cards: MutableCards): Hand {
        val sortedCards = cards.sortedByDescending { it.value }.toMutableList()
        return buildHand(sortedCards, HandType.HIGH_CARD)
    }

    /**
     * Builds a [Hand] with the provided [HandType] and assigns the high card and kickers by descending [CardValue].
     *
     * @param cards the [MutableCards] to assign the high card and kickers from.
     * @param handType the [HandType] to assign the [Hand].
     * @return the constructed [Hand].
     */
    private fun buildHand(cards: MutableCards, handType: HandType): Hand {
        require(cards.size == 5)
        return if (isAceLowStraight(cards, handType)) {
            Hand(
                handType = HandType.STRAIGHT_FLUSH,
                highCard = CardValue.FIVE,
                firstKicker = CardValue.FOUR,
                secondKicker = CardValue.THREE,
                thirdKicker = CardValue.TWO,
                fourthKicker = CardValue.ACE,
            )
        } else Hand(
            handType = handType,
            highCard = cards.removeMaxValue(),
            firstKicker = cards.removeMaxValue(),
            secondKicker = cards.removeMaxValue(),
            thirdKicker = cards.removeMaxValue(),
            fourthKicker = cards.removeMaxValue()
        )
    }

    /**
     * Checks if the provided [cards] and [handType] are an ace low straight.
     *
     * @param cards the [Cards] containing the ace low straight.
     * @param handType the [HandType] of the provided cards.
     * @return True if the cards are an ace low straight, otherwise false.
     */
    private fun isAceLowStraight(cards: Cards, handType: HandType): Boolean {
        return (handType == HandType.STRAIGHT ||  handType == HandType.STRAIGHT_FLUSH)
            && cards.map { it.value }.containsAll(aceLowStraight)
    }

    /**
     * Finds the five cards in a list of [MutableCards] that make up the highest straight.
     *
     * @param cards the [MutableCards] to find the straight in.
     * @param isFlush [Boolean] to ensure that the straight is also a flush.
     * @return [MutableCards] consisting of the straight.
     */
    private fun getStraight(cards: MutableCards, isFlush: Boolean = false): MutableCards {
        val handSize = 5
        val flushSuit = cards.groupingBy { it.suit }
            .eachCount()
            .entries
            .find { it.value >= 5 }
            ?.key
        val sortedCards = cards.sortedByDescending { it.value }
        val distinctWeights = sortedCards.map { it.value }.distinct()


        // Check for normal consecutive straight
        for (i in 0..distinctWeights.size - handSize) {
            val potentialStraight = distinctWeights.subList(i, i + handSize)
            if (potentialStraight.zipWithNext().all { (a, b) -> a.ordinal - b.ordinal == 1 }) {
                val straight = sortedCards.filter { it.value in potentialStraight }.toMutableList()
                return checkStraightFlush(isFlush, flushSuit, straight)
            }
        }

        // Check for ACE low straight
        if (distinctWeights.containsAll(aceLowStraight)) {
            val straight = sortedCards.filter { it.value in aceLowStraight }.toMutableList()
            return checkStraightFlush(isFlush, flushSuit, straight)
        }

        throw IllegalArgumentException("The provided MutableCards do not contain a Straight.")
    }

    /**
     * If [isFlush] is true, filters the [straight] cards by the [suit]. Otherwise, just returns the [straight].
     *
     * @param isFlush [Boolean] to force filtering by a Flush suit.
     * @param suit [Suit] to filter the [straight] cards by.
     * @param straight [MutableCards] that contain the straight.
     */
    private fun checkStraightFlush(
        isFlush: Boolean,
        suit: Suit?,
        straight: MutableCards
    ): MutableCards {
        return if (isFlush) {
            suit?.let { s -> straight.filter { it.suit == s }.toMutableList() }
                ?: throw IllegalArgumentException("The provided MutableCards do not contain a Flush")
        } else {
            straight
        }
    }

    /**
     * Finds the five cards in a list of [MutableCards] that make up a flush.
     *
     * @param cards the [MutableCards] to find the flush in.
     * @return [MutableCards] consisting of the flush.
     */
    private fun getFlush(cards: MutableCards): MutableCards {
        val suitGroups = cards.groupBy { it.suit }
        val flushSuit = suitGroups.entries.find { it.value.size >= 5 }?.key
            ?: throw IllegalArgumentException("The provided MutableCards do not contain a flush.")

        return flushSuit.let { suit ->
            suitGroups[suit]?.sortedByDescending { it.value }?.take(5)?.toMutableList() ?: throw IllegalArgumentException("The provided MutableCards do not contain a flush.")
        }
    }
}