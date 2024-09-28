package com.murrey.texasholdem.data

import com.murrey.texasholdem.model.*
import com.murrey.texasholdem.util.addHandData
import com.murrey.texasholdem.util.addSortedCards
import java.io.File

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
     * @param communityCards the [Cards] that were community cards in the game.
     * @param selfHand the [Hand] that the self used to win the game.
     */
    fun addRow(
        selfHoleCards: Cards,
        communityCards: Cards,
        selfHand: Hand,
    ) {
        val row: MutableCsvRow = mutableListOf()
        row.addSortedCards(selfHoleCards)
        row.addSortedCards(communityCards)
        row.addHandData(selfHand)
        rows.add(row)
    }

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