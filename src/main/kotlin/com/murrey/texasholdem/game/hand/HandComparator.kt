package com.murrey.texasholdem.game.hand

import com.murrey.texasholdem.model.Hand
import com.murrey.texasholdem.model.Outcome

/**
 * Object containing functions that compare the different properties between two [Hand]s and determine the
 * [Outcome] of the comparison.
 */
object HandComparator {

    /**
     * Compares the [Hand.handType] of each hand and determines the [Outcome] for [hand1].
     * If the [Hand.handType]s are equal, then compare the [Hand.highCard].
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    fun compare(hand1: Hand, hand2: Hand): Outcome {
        return if (hand1.handType == hand2.handType) {
            compareHighCard(hand1, hand2)
        } else if (hand1.handType > hand2.handType) {
            Outcome.WON
        } else {
            Outcome.LOST
        }
    }

    /**
     * Compares the [Hand.highCard] of each hand and determines the [Outcome] for [hand1].
     * If the [Hand.highCard]s are equal, then compare the [Hand.firstKicker].
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    private fun compareHighCard(hand1: Hand, hand2: Hand): Outcome {
        return hand1.highCard?.let { highCard1 ->
            hand2.highCard?.let { highCard2 ->
                if (highCard1 == highCard2) {
                    compareFirstKicker(hand1, hand2)
                } else if (highCard1 > highCard2) {
                    Outcome.WON
                } else {
                    Outcome.LOST
                }
            } ?: Outcome.TIED
        } ?: Outcome.TIED
    }

    /**
     * Compares the [Hand.firstKicker] of each hand and determines the [Outcome] for [hand1].
     * If the [Hand.firstKicker]s are equal, then compare the [Hand.secondKicker].
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    private fun compareFirstKicker(hand1: Hand, hand2: Hand): Outcome {
        return hand1.firstKicker?.let { firstKicker1 ->
            hand2.firstKicker?.let { firstKicker2 ->
                if (firstKicker1 == firstKicker2) {
                    compareSecondKicker(hand1, hand2)
                } else if (firstKicker1 > firstKicker2) {
                    Outcome.WON
                } else {
                    Outcome.LOST
                }
            } ?: Outcome.TIED
        } ?: Outcome.TIED
    }

    /**
     * Compares the [Hand.secondKicker] of each hand and determines the [Outcome] for [hand1].
     * If the [Hand.secondKicker]s are equal, then compare the [Hand.thirdKicker].
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    private fun compareSecondKicker(hand1: Hand, hand2: Hand): Outcome {
        return hand1.secondKicker?.let { secondKicker1 ->
            hand2.secondKicker?.let { secondKicker2 ->
                if (secondKicker1 == secondKicker2) {
                    compareThirdKicker(hand1, hand2)
                } else if (secondKicker1 > secondKicker2) {
                    Outcome.WON
                } else {
                    Outcome.LOST
                }
            } ?: Outcome.TIED
        } ?: Outcome.TIED
    }

    /**
     * Compares the [Hand.thirdKicker] of each hand and determines the [Outcome] for [hand1].
     * If the [Hand.thirdKicker]s are equal, then compare the [Hand.fourthKicker].
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    private fun compareThirdKicker(hand1: Hand, hand2: Hand): Outcome {
        return hand1.thirdKicker?.let { thirdKicker1 ->
            hand2.thirdKicker?.let { thirdKicker2 ->
                if (thirdKicker1 == thirdKicker2) {
                    compareFourthKicker(hand1, hand2)
                } else if (thirdKicker1 > thirdKicker2) {
                    Outcome.WON
                } else {
                    Outcome.LOST
                }
            } ?: Outcome.TIED
        } ?: Outcome.TIED
    }

    /**
     * Compares the [Hand.fourthKicker] of each hand and determines the [Outcome] for [hand1].
     * If the [Hand.fourthKicker]s are equal, then the hands are equal.
     *
     * @param hand1 the [Hand] to determine the [Outcome] for.
     * @param hand2 the [Hand] to compare [hand1] to.
     * @return the [Outcome] for [hand1].
     */
    private fun compareFourthKicker(hand1: Hand, hand2: Hand): Outcome {
        return hand1.fourthKicker?.let { fourthKicker1 ->
            hand2.fourthKicker?.let { fourthKicker2 ->
                if (fourthKicker1 == fourthKicker2) {
                    Outcome.TIED
                } else if (fourthKicker1 > fourthKicker2) {
                    Outcome.WON
                } else {
                    Outcome.LOST
                }
            } ?: Outcome.TIED
        } ?: Outcome.TIED
    }
}