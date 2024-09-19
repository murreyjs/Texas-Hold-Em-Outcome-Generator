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
    override val handType: HandType,
    override var highCard: CardValue? = null,
    override var firstKicker: CardValue? = null,
    override var secondKicker: CardValue? = null,
    override var thirdKicker: CardValue? = null,
    override var fourthKicker: CardValue? = null
) : HandInterface