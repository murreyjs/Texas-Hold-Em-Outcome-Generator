package com.murrey.texasholdem.model

/**
 * Enum representing the suit of a [Card].
 */
enum class Suit {
    SPADES,
    DIAMONDS,
    HEARTS,
    CLUBS
}

/**
 * Enum representing the value of a [Card].
 */
enum class CardValue {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE;

    /**
     * Returns true if the [CardValue] is a face value, otherwise false.
     */
    fun isFaceCard(): Boolean {
        return when (this) {
            JACK, QUEEN, KING, ACE -> true
            else -> false
        }
    }
}

/**
 * Enum representing the different types of combinations of [Card]s that can be used as a hand.
 */
enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH
}

/**
 * Enum representing the outcome of a comparison between two or more [Player]s.
 */
enum class Outcome {
    WON, LOST, TIED
}