package com.murrey.texasholdem.model

import kotlin.collections.ArrayDeque

/**
 * Model for a deck of [Card]s.
 */
class Deck {

    /**
     * The [Card]s contained within this deck.
     */
    private val cards: ArrayDeque<Card> by lazy {
        ArrayDeque<Card>().apply {
            for (suit in Suit.values()) {
                for (value in CardValue.values()) {
                    add(Card(suit, value))
                }
            }
        }
    }

    init {
        shuffle()
    }

    /**
     * Returns the top card in the deck.
     */
    val topCard: Card?
        get() = cards.firstOrNull()

    /**
     * Shuffles the order of the [Card]s in this deck.
     */
    private fun shuffle() {
        val shuffled = cards.shuffled()
        cards.clear()
        cards.addAll(shuffled)
    }
}

/**
 * Model for a standard playing card.
 */
data class Card(val suit: Suit, val value: CardValue)