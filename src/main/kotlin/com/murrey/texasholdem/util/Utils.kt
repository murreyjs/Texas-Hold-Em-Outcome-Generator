package com.murrey.texasholdem.util

import com.murrey.texasholdem.model.*

/**
 * Generates a list of all combinations of size [k] using the elements in the caller list.
 *
 * The function begins by dropping the first element, and recursively generating the combinations
 * for all remaining elements with size [k] - 1. It then adds back the first element to each of those
 * combinations, resulting in a list of all combinations of size [k] with the first element.
 * We then drop the first element and recursively generate all combinations of size [k] with the
 * remaining elements.
 *
 * @param k the size of combinations to generate.
 * @return list of combinations.
 */
fun <T> List<T>.combinations(k: Int): List<List<T>> {
    if (k == 0) return listOf(emptyList())
    if (size < k) return emptyList()
    return drop(1).combinations(k - 1).map { listOf(first()) + it } + drop(1).combinations(k)
}

/**
 * Removes and returns the element from the [MutableList] that has the maximum value given by the [selector].
 *
 * @param selector the [Comparable] used to determine each element's value.
 * @return the element in the list with the maximum value given by the [selector].
 */
fun <T, R : Comparable<R>> MutableList<T>.removeMaxBy(selector: (T) -> R): T {
    require(this.size > 0)

    val maxElement = this.maxByOrNull(selector)
    if (maxElement != null) {
        this.remove(maxElement)
        return maxElement
    } else {
        throw NoSuchElementException("Max element not found.")
    }
}

/**
 * Finds the [Card] with the highest [CardValue], removes it from the [MutableCards], and returns its [CardValue].
 *
 * @return the highest [CardValue] in the [MutableCards].
 */
fun MutableCards.removeMaxValue(): CardValue {
    return this.removeMaxBy { it.value }.value
}

/**
 * Sorts a set of cards by their [CardValue] and adds the string value of the ordinal value of those.
 *
 * @param cards the [Cards] to sort and add to the [MutableCsvRow].
 */
fun MutableCsvRow.addSortedCards(cards: Cards) {
    this.addAll(cards.sortedBy { it.value }.map { it.value.ordinal.toString() })
}

/**
 * Adds string values of [Hand] data to a [MutableCsvRow].
 *
 * @param hand the [Hand] to retrieve the data from.
 */
fun MutableCsvRow.addHandData(hand: Hand) {
    this.add(hand.handType.ordinal.toString())
    this.add(hand.highCard.ordinal.toString())
    this.add(hand.firstKicker.ordinal.toString())
    this.add(hand.secondKicker.ordinal.toString())
    this.add(hand.thirdKicker.ordinal.toString())
    this.add(hand.fourthKicker.ordinal.toString())
}

/**
 * Returns a list of [CardValue]s that occur a specified number of times in the caller list of cards.
 *
 * @param occurrences The number of times a [CardValue] must appear in the list to be included in the result.
 * @return A list of [CardValue]s that appear exactly [occurrences] times in the caller.
 */
fun Cards.findValuesByOccurrence(occurrences: Int): Set<CardValue> {
    return this
        .groupBy { it.value }
        .filter { (_, groupedCards) -> groupedCards.size == occurrences }
        .keys
}

/**
 * Recursively compares the properties of another [Hand]. Using the first provided property, if the caller [Hand] has a
 * higher property value, then return [Outcome.WON]. If the caller [Hand] has a lower property value, return [Outcome.LOST].
 * If the property values are equal, then compare the next property in [properties]. If [properties] is empty, then
 * return [Outcome.TIED].
 *
 * @param other the [Hand] to compare to the caller.
 * @param properties the [Hand] properties to compare.
 * @return the determined [Outcome].
 */
fun <T : Comparable<T>> Hand.compareHand(other: Hand, vararg properties: (Hand) -> T?): Outcome {
    if (properties.isEmpty()) {
        return Outcome.TIED
    }

    val firstProperty = properties.first()
    val thisProp = firstProperty(this)
    val otherProp = firstProperty(other)
    if (thisProp == null || otherProp == null) {
        return Outcome.TIED
    }
    val comparison = thisProp.compareTo(otherProp)

    return when {
        comparison > 0 -> Outcome.WON
        comparison < 0 -> Outcome.LOST
        else -> compareHand(other, *properties.drop(1).toTypedArray())
    }
}