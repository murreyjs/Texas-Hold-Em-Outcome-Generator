package com.murrey.texasholdem.game.hand

import com.murrey.texasholdem.model.CardValue
import com.murrey.texasholdem.model.Hand
import com.murrey.texasholdem.model.HandType
import com.murrey.texasholdem.model.Outcome
import com.murrey.texasholdem.util.compareHand

/**
 * Object containing functions that compare the different properties between two [Hand]s and determine the
 * [Outcome] of the comparison.
 */
object HandComparator {

    /**
     * Compares the [HandType] of each hand and determines the [Outcome] for [hand1].
     * If the [HandType]s are equal, then compare the high card [CardValue].
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    fun compare(hand1: Hand, hand2: Hand): Outcome {
        val handTypeComparison = hand1.compareHand(hand2, Hand::handType)
        return if (handTypeComparison != Outcome.TIED) {
            handTypeComparison
        } else {
            hand1.compareHand(
                hand2,
                Hand::highCard,
                Hand::firstKicker,
                Hand::secondKicker,
                Hand::thirdKicker,
                Hand::fourthKicker
            )
        }
    }
}