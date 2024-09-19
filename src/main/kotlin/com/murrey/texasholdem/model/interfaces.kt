package com.murrey.texasholdem.model

/**
 * Interface for a participant that plays in a game.
 */
interface PlayerInterface {

    /**
     * Function that adds a card to a players hand.
     */
    fun receiveHoleCard(card: Card)

    /**
     * Function that adds a card to a players hand.
     */
    fun getHoleCards(): List<Card>
}

/**
 * Interface for the hand that a player holds.
 */
interface HandInterface {
    val handType: HandType
    var highCard: CardValue?
    var firstKicker: CardValue?
    var secondKicker: CardValue?
    var thirdKicker: CardValue?
    var fourthKicker: CardValue?
}