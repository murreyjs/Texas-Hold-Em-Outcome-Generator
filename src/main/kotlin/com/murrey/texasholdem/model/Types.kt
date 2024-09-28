package com.murrey.texasholdem.model

/**
 * Alias for a list of [String]s that are written as a row in a CSV file.
 */
typealias MutableCsvRow = MutableList<String>

/**
 * Alias for a list of [MutableCsvRow]s.
 */
typealias MutableCsvRows = MutableList<MutableCsvRow>

/**
 * Alias for a list of [Card]s
 */
typealias Cards = List<Card>

/**
 * Alias for a mutable list of [Card]s
 */
typealias MutableCards = MutableList<Card>

/**
 * Alias for a list of [Player]s
 */
typealias Players = List<Player>

/**
 * Alias for a mutable list of [Player]s
 */
typealias MutablePlayers = MutableList<Player>

/**
 * Alias for a list of [CardValue]s
 */
typealias CardValues = List<CardValue>

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