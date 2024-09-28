package com.murrey.texasholdem.game.hand

import com.murrey.texasholdem.model.*
import com.murrey.texasholdem.util.combinations

/**
 * Object containing functions related to evaluating the [HandType] of a list of [Card]s.
 */
object HandTypeEvaluator {

    /**
     * Determines the best [HandType] for each combination of five cards given a list of seven cards.
     *
     * @param cards the list of seven [Card]s used to determine the best [HandType].
     * @return the best [HandType].
     */
    fun evaluateHandType(cards: Cards): HandType {
        require(cards.size == 7) { "Input must be exactly 7 cards" }

        val allCombinations = cards.combinations(5)
        return allCombinations.map { evaluateFiveCardHand(it) }.maxBy { it.ordinal }
    }

    /**
     * Determines the [HandType] for a list of five [Card]s.
     *
     * @param cards the list of [Card]s used to determine the [HandType].
     * @return the [HandType] of the five [Card]s.
     */
    private fun evaluateFiveCardHand(cards: Cards): HandType {
        require(cards.size == 5) { "Hand must contain exactly 5 cards" }

        val values = cards.map { it.value }
        val suits = cards.map { it.suit }

        val isRoyalFlush = values == listOf(CardValue.ACE, CardValue.KING, CardValue.QUEEN, CardValue.JACK, CardValue.TEN)
        val isFlush = suits.toSet().size == 1
        val isStraight = isStraight(values)
        val valueGroups = values.groupBy { it }.mapValues { it.value.size }

        return when {
            isRoyalFlush -> HandType.ROYAL_FLUSH
            isFlush && isStraight -> HandType.STRAIGHT_FLUSH
            valueGroups.containsValue(4) -> HandType.FOUR_OF_A_KIND
            valueGroups.containsValue(3) && valueGroups.containsValue(2) -> HandType.FULL_HOUSE
            isFlush -> HandType.FLUSH
            isStraight -> HandType.STRAIGHT
            valueGroups.containsValue(3) -> HandType.THREE_OF_A_KIND
            valueGroups.filter { it.value == 2 }.size == 2 -> HandType.TWO_PAIR
            valueGroups.containsValue(2) -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }
    }

    /**
     * Determines if a list of [CardValue]s can be categorized as a [HandType.STRAIGHT].
     *
     * @param values the [CardValues] used to determine the existence of a [HandType.STRAIGHT].
     * @return [Boolean] true if the [values] are a [HandType.STRAIGHT], false otherwise.
     */
    private fun isStraight(values: CardValues): Boolean {
        val sortedValues = values.sortedBy { it.ordinal }
        val isRegularStraight = sortedValues.zipWithNext().all { (a, b) -> b.ordinal - a.ordinal == 1 }
        val isAceLowStraight = sortedValues == listOf(CardValue.TWO, CardValue.THREE, CardValue.FOUR, CardValue.FIVE, CardValue.ACE)
        return isRegularStraight || isAceLowStraight
    }
 }