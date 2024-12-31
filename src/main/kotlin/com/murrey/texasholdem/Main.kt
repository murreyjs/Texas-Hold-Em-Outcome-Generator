package com.murrey.texasholdem

import com.murrey.texasholdem.data.DataWriter
import com.murrey.texasholdem.game.TexasHoldEm

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val rounds = 10000
        val dataWriter = DataWriter()
        for (i in 0..rounds) {
            val game = TexasHoldEm()
            val outcome = game.play()
            outcome?.also {
                dataWriter.addRow(game.selfHoleCards, it)
            }
        }
        dataWriter.writeToCSV("./game.csv")
    }
}