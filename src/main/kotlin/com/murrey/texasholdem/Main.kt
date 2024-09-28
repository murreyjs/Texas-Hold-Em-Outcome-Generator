package com.murrey.texasholdem

import com.murrey.texasholdem.data.DataWriter
import com.murrey.texasholdem.game.TexasHoldEm

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val rounds = 1000
        val dataWriter = DataWriter()
        for (i in 0..rounds) {
            val game = TexasHoldEm()
            game.play()
            dataWriter.addRow(game.selfHoleCards, game.communityCards, game.selfHand)
        }
        dataWriter.writeToCSV("./game.csv")
    }
}