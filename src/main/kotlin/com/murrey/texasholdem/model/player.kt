package com.murrey.texasholdem.model

/**
 * Implementation of the [Player] interface.
 */
class Player : PlayerInterface {

    companion object {
        private const val MAX_HOLE_CARDS = 2
    }

    /**
     * The hole [Card]s that a player currently holds.
     */
    private val holeCards: MutableList<Card> = mutableListOf()

    /**
     * Function that adds a card to a players hole cards.
     *
     * @param card the [Card] to add to the players hole [Card]s.
     * @throws IllegalArgumentException if player already has max number of hole [Card]s.
     */
    override fun receiveHoleCard(card: Card) {
        require(holeCards.size < MAX_HOLE_CARDS)
        holeCards.add(card)
    }

    /**
     * Returns the current hole [Card]s that a player holds.
     *
     * @return list of hole [Card]s.
     */
    override fun getHoleCards() = holeCards.toList()
}