package com.murrey.texasholdem.game

import com.murrey.texasholdem.game.outcome.OutcomeEvaluator
import com.murrey.texasholdem.model.Card
import com.murrey.texasholdem.model.Deck
import com.murrey.texasholdem.model.Outcome
import com.murrey.texasholdem.model.Player

/**
 * Class that deals cards to players in the format of a texas hold 'em game and determines winner.
 */
class TexasHoldEm {
    private val self: Player = Player()
    private val players = mutableListOf<Player>()
    private val deck: Deck = Deck()
    private val communityCards: MutableList<Card> = mutableListOf()

    /**
     * Deals cards to [players] and determines the winner.
     *
     * @return the [Outcome] of the game for [self].
     */
    fun play(): Outcome {
        for (i in 1..NUM_PLAYERS) {
            players.add(Player())
        }
        dealHoleCards()
        dealFlopAndRiver()
        return evaluateWinner()
    }

    /**
     * Deals hold [Card]s to each [Player] in [players].
     */
    private fun dealHoleCards() {
        for (i in 0 until HOLE_SIZE) {
            players.forEach { player ->
                deck.topCard?.also { player.receiveHoleCard(it) }
            }
            deck.topCard?.also { self.receiveHoleCard(it) }
        }
    }

    /**
     * Deals the flop and the river to the [communityCards].
     */
    private fun dealFlopAndRiver() {
        for (i in 0 until FLOP_SIZE) {
            deck.topCard?.also { communityCards.add(it) }
        }
        for (i in 0 until RIVER_SIZE) {
            deck.topCard?.also { communityCards.add(it) }
        }
    }

    /**
     * Determines whether [self] won the game.
     *
     * @return the [Outcome] of the game for [self].
     */
    private fun evaluateWinner(): Outcome {
        return OutcomeEvaluator.evaluateOutcome(self, players, communityCards)
    }

    companion object {
        private const val HOLE_SIZE = 2
        private const val FLOP_SIZE = 3
        private const val RIVER_SIZE = 2
        private const val NUM_PLAYERS = 4
    }
}