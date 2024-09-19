package com.murrey.texasholdem.util

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
fun <T, R : Comparable<R>> MutableList<T>.removeMaxBy(selector: (T) -> R): T? {
    if (this.isEmpty()) return null

    val maxElement = this.maxByOrNull(selector)
    if (maxElement != null) {
        this.remove(maxElement)
    }
    return maxElement
}