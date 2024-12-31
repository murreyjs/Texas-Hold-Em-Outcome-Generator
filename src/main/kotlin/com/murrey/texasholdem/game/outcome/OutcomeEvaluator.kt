package com.murrey.texasholdem.game.outcome

import com.murrey.texasholdem.game.hand.HandComparator
import com.murrey.texasholdem.model.*

/**
 * Object containing functions related to evaluating the outcome of a Texas Hold Em game for a single player.
 */
object OutcomeEvaluator {

    /**
     * Evaluates the outcome of a Texas Hold Em game for a single player.
     *
     * @param selfHand the [Hand] that the self has.
     * @param playerHands a list of [Hand]s that the other players have.
     *
     * @return the [Outcome] for the self [Player].
     */
    fun evaluateOutcome(selfHand: Hand, playerHands: Hands): Outcome {

        // Store the outcomes for the comparison of the self hand with every other players hand.
        val outcomes = mutableListOf<Outcome>()
        playerHands.forEach {
            outcomes.add(HandComparator.compare(selfHand, it))
        }

        // If the self lost or tied with any other player, then return that outcome, otherwise the self won.
        return if (outcomes.any { it == Outcome.LOST }) {
            Outcome.LOST
        } else if (outcomes.any { it == Outcome.TIED }) {
            Outcome.TIED
        } else {
            Outcome.WON
        }
    }
}