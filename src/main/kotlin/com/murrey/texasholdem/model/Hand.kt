package com.murrey.texasholdem.model

/**
 * Model representing a combination of cards used to win a round of Texas Hold em.
 *
 * @param handType the [HandType] of the [Hand].
 * @param highCard the highest key [CardValue] that is used in the [HandType].
 * @param firstKicker the first kicker [CardValue] in the [Hand], null if [HandType] doesn't require this kicker.
 * @param secondKicker the second kicker [CardValue] in the [Hand], null if [HandType] doesn't require this kicker.
 * @param thirdKicker the third kicker [CardValue] in the [Hand], null if [HandType] doesn't require this kicker.
 * @param fourthKicker the fourth kicker [CardValue] in the [Hand], null if [HandType] doesn't require this kicker.
 */
data class Hand(
    val handType: HandType,
    var highCard: CardValue,
    var firstKicker: CardValue,
    var secondKicker: CardValue,
    var thirdKicker: CardValue,
    var fourthKicker: CardValue
)

/**
 * Exception to throw when the self players [Hand] is not yet initialized.
 */
class HandNotInitializedException : IllegalStateException("Self does not have hand type yet, please call play() function")