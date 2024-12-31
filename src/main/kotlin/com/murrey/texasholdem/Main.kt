package com.murrey.texasholdem

import com.murrey.texasholdem.data.DataWriter
import com.murrey.texasholdem.game.TexasHoldEm

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        // Generate training data
        generateData(10000, "train")

        // Generate test data
        generateData(1000, "test")
    }

    private fun generateData(rounds: Int, filename: String) {
        val dataWriter = DataWriter()
        for (i in 0..rounds) {
            val game = TexasHoldEm()
            val outcome = game.play()
            outcome?.also {
                dataWriter.addRow(game.selfHoleCards, it)
            }
        }
        dataWriter.writeToCSV("./$filename.csv")
    }
}