package com.murrey.texasholdem.game.hand

import com.murrey.texasholdem.model.Card
import com.murrey.texasholdem.model.CardValue
import com.murrey.texasholdem.model.Hand
import com.murrey.texasholdem.model.HandType
import com.murrey.texasholdem.util.removeMaxBy

/**
 * Object containing functions related to building a [Hand] from a list of [Card]s.
 */
object HandBuilder {

    /**
     * Builds a [Hand] from the provided list of [Card]s.
     *
     * @param cards the list of [Card]s to build a [Hand] out of.
     * @return the best [Hand] that can be built using the provided [Card]s.
     */
    fun buildHand(cards: List<Card>): Hand {
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
        return Hand(handType = HandType.ROYAL_FLUSH)
    }

    /**
     * Builds a Straight Flush [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Straight Flush.
     */
    private fun buildStraightFlush(cards: MutableList<Card>): Hand {
        val highCard = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.STRAIGHT_FLUSH,
            highCard = highCard
        )
    }

    /**
     * Builds a Four of a Kind [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing Four of a Kind.
     */
    private fun buildFourOfAKind(cards: MutableList<Card>): Hand {
        val highCard = findValuesByOccurrence(cards, 4).firstOrNull()
        cards.removeAll { it.value == highCard }
        val kicker1 = cards[0].value
        return Hand(
            handType = HandType.FOUR_OF_A_KIND,
            highCard = highCard,
            firstKicker = kicker1
        )
    }

    /**
     * Builds a Full House [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Full House.
     */
    private fun buildFullHouse(cards: MutableList<Card>): Hand {
        val threeOfAKindValue = findValuesByOccurrence(cards, 3).firstOrNull()
        val pairValue = findValuesByOccurrence(cards, 2).firstOrNull()
        val threeGreaterThanTwo = threeOfAKindValue!!.ordinal > pairValue!!.ordinal
        val highCard = if (threeGreaterThanTwo) threeOfAKindValue else pairValue
        val kicker1 = if (threeGreaterThanTwo) pairValue else threeOfAKindValue
        return Hand(
            handType = HandType.FULL_HOUSE,
            highCard = highCard,
            firstKicker = kicker1
        )
    }

    /**
     * Builds a Flush [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Flush.
     */
    private fun buildFlush(cards: MutableList<Card>): Hand {
        val highCard = cards.removeMaxBy { it.value }?.value
        val kicker1 = cards.removeMaxBy { it.value }?.value
        val kicker2 = cards.removeMaxBy { it.value }?.value
        val kicker3 = cards.removeMaxBy { it.value }?.value
        val kicker4 = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.FLUSH,
            highCard = highCard,
            firstKicker = kicker1,
            secondKicker = kicker2,
            thirdKicker = kicker3,
            fourthKicker = kicker4
        )
    }

    /**
     * Builds a Straight [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a Straight.
     */
    private fun buildStraight(cards: MutableList<Card>): Hand {
        val highCard = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.STRAIGHT,
            highCard = highCard
        )
    }

    /**
     * Builds a Three of a Kind [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing Three of a Kind.
     */
    private fun buildThreeOfAKind(cards: MutableList<Card>): Hand {
        val highCard = findValuesByOccurrence(cards, 3).firstOrNull()
        cards.removeAll { it.value == highCard }
        val kicker1 = cards.removeMaxBy { it.value }?.value
        val kicker2 = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.THREE_OF_A_KIND,
            highCard = highCard,
            firstKicker = kicker1,
            secondKicker = kicker2
        )
    }

    /**
     * Builds a Two Pair [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing Two Pair.
     */
    private fun buildTwoPair(cards: MutableList<Card>): Hand {
        val pairs = findValuesByOccurrence(cards, 2)
        val highCard = pairs.maxBy { it }
        val kicker1 = pairs.minBy { it }
        cards.removeAll { it.value == highCard || it.value == kicker1 }
        val kicker2 = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.TWO_PAIR,
            highCard = highCard,
            firstKicker = kicker1,
            secondKicker = kicker2
        )
    }

    /**
     * Builds a One Pair [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing a One Pair.
     */
    private fun buildOnePair(cards: MutableList<Card>): Hand {
        val highCard = findValuesByOccurrence(cards, 2).firstOrNull()
        cards.removeAll { it.value == highCard }
        val kicker1 = cards.removeMaxBy { it.value }?.value
        val kicker2 = cards.removeMaxBy { it.value }?.value
        val kicker3 = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.ONE_PAIR,
            highCard = highCard,
            firstKicker = kicker1,
            secondKicker = kicker2,
            thirdKicker = kicker3
        )
    }

    /**
     * Builds a High Card [Hand] using the provided [Card]s.
     *
     * @param cards the [Card]s that make up the [Hand].
     * @return a [Hand] representing High Card.
     */
    private fun buildHighCard(cards: MutableList<Card>): Hand {
        val highCard = cards.removeMaxBy { it.value }?.value
        val kicker1 = cards.removeMaxBy { it.value }?.value
        val kicker2 = cards.removeMaxBy { it.value }?.value
        val kicker3 = cards.removeMaxBy { it.value }?.value
        val kicker4 = cards.removeMaxBy { it.value }?.value
        return Hand(
            handType = HandType.HIGH_CARD,
            highCard = highCard,
            firstKicker = kicker1,
            secondKicker = kicker2,
            thirdKicker = kicker3,
            fourthKicker = kicker4
        )
    }

    /**
     * Returns a list of [CardValue]s that occur a specified number of times in the given list of cards.
     *
     * @param cards A list of cards from which to count [CardValue] occurrences.
     * @param occurrences The number of times a [CardValue] must appear in the list to be included in the result.
     * @return A list of [CardValue]s that appear exactly [occurrences] times in [cards].
     */
    private fun findValuesByOccurrence(cards: List<Card>, occurrences: Int): Set<CardValue> {
        return cards
            .groupBy { it.value }
            .filter { (_, groupedCards) -> groupedCards.size == occurrences }
            .keys
    }
}