package com.murrey.texasholdem.game.outcome

import com.murrey.texasholdem.game.hand.HandBuilder
import com.murrey.texasholdem.game.hand.HandComparator
import com.murrey.texasholdem.model.*

/**
 * Object containing functions related to evaluating the outcome of a Texas Hold Em game for a single player.
 */
object OutcomeEvaluator {

    /**
     * Evaluates the outcome of a Texas Hold Em game for a single player.
     *
     * @param self the [Player] to evaluate the outcome for.
     * @param others a list of [Player]s to compare the [self]s best [Hand] to.
     * @param communityCards the list of [Card]s that make up the River and Flop in the game.
     *
     * @return the [Outcome] for the [self] [Player].
     */
    fun evaluateOutcome(self: Player, others: Players, communityCards: Cards): Outcome {
        val playerHands = others.map { player ->
            HandBuilder.buildHand(player.getHoleCards() + communityCards)
        }
        val selfHand= HandBuilder.buildHand(self.getHoleCards() + communityCards)

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