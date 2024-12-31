package com.murrey.texasholdem.data

import com.murrey.texasholdem.model.*
import java.io.File
import kotlin.math.abs

/**
 * Class that builds CSV data and writes it to a file.
 */
class DataWriter {

    /**
     * The [MutableCsvRows] that make up the CSV file.
     */
    private val rows: MutableCsvRows = mutableListOf()

    /**
     * Builds a [MutableCsvRow] from the provided data and adds it to the [rows].
     *
     * @param selfHoleCards the [Cards] that the self held in the game.
     * @param outcome the [Outcome] of the hand.
     */
    fun addRow(
        selfHoleCards: Cards,
        outcome: Outcome
    ) {
        val row: MutableCsvRow = mutableListOf()

        // Add columns for card value, as well as a one hot encoded rank.
        // Always sort by descending, so the model knows the first columns are related to the high card.
        selfHoleCards.sortedByDescending { it.value }.forEach {
            row.add(it.value.ordinal.toString())
            val suitList = mutableListOf("0", "0", "0", "0")
            suitList[it.suit.ordinal] = "1"
            row.addAll(suitList)
        }

        // Add column that is 1 if hole card values are the same, otherwise 0.
        row.add(boolToBinary(selfHoleCards[0].value == selfHoleCards[1].value))

        // Add column that is 1 if hole card suits are the same, otherwise 0.
        row.add(boolToBinary(selfHoleCards[0].suit == selfHoleCards[1].suit))

        // Add column for the difference in values of the two cards.
        row.add(abs(selfHoleCards[0].value.ordinal - selfHoleCards[1].value.ordinal).toString())

        // Add column for the sum of values of the two cards.
        row.add((selfHoleCards[0].value.ordinal + selfHoleCards[1].value.ordinal).toString())

        row.add(boolToBinary(outcome == Outcome.WON))

        rows.add(row)
    }

    /**
     * Returns a string of "1" if [eval] is true, otherwise "0".
     *
     * @param eval the [Boolean] to evaluate
     *
     * @return "1" if [eval] is true, otherwise "0".
     */
    private fun boolToBinary(eval: Boolean) = if (eval) "1" else "0"

    /**
     * Writes the [rows] to a CSV file.
     *
     * @param fileName the name of the CSV file to create.
     */
    fun writeToCSV(fileName: String) {
        File(fileName).printWriter().use { out ->
            rows.forEach { row ->
                out.println(row.joinToString(","))
            }
        }
    }

}