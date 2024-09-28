package com.murrey.texasholdem.game

import com.murrey.texasholdem.game.outcome.OutcomeEvaluator
import com.murrey.texasholdem.model.*

/**
 * Class that deals cards to players in the format of a texas hold 'em game and determines winner.
 */
class TexasHoldEm {

    /**
     * A [Player] representing the self, who will have the [Outcome] of the game determined for themselves.
     */
    private val self: Player = Player()

    /**
     * A [Hand] representing the best combination of [Card]s that [self] will use in the game.
     */
    private val _selfHand: Hand? = null

    /**
     * A list of other [Player]s in the game.
     */
    private val players: MutablePlayers = mutableListOf()

    /**
     * The [Deck] that is used to deal hole [Card]s to each player, as well as deal [communityCards].
     */
    private val deck: Deck = Deck()

    /**
     * The list of community [Card]s that are dealt in the game.
     */
    private val _communityCards: MutableCards = mutableListOf()

    /**
     * Getter for the hole [Card]s that the [self] holds.
     */
    val selfHoleCards: Cards
        get() = self.getHoleCards()

    /**
     * Getter for the [_selfHand].
     */
    val selfHand: Hand
        get() = _selfHand ?: throw HandNotInitializedException()

    /**
     * Getter for the [_communityCards] that are dealt in the game.
     */
    val communityCards: Cards
        get() = _communityCards.toList()


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
        return evaluateOutcome()
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
     * Deals the flop and the river to the [_communityCards].
     */
    private fun dealFlopAndRiver() {
        for (i in 0 until FLOP_SIZE) {
            deck.topCard?.also { _communityCards.add(it) }
        }
        for (i in 0 until RIVER_SIZE) {
            deck.topCard?.also { _communityCards.add(it) }
        }
    }

    /**
     * Determines the [Outcome] of the game for [self].
     *
     * @return the [Outcome] of the game for [self].
     */
    private fun evaluateOutcome(): Outcome {
        return OutcomeEvaluator.evaluateOutcome(self, players, _communityCards)
    }

    companion object {
        private const val HOLE_SIZE = 2
        private const val FLOP_SIZE = 3
        private const val RIVER_SIZE = 2
        private const val NUM_PLAYERS = 4
    }
}