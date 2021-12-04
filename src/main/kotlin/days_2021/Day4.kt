package days_2021

import util.Day
import util.Util

class Day4 : Day(4) {
    data class BingoCard(val card: List<List<Int>>) {
        var rows: List<List<Int>> = card
        var cols: List<List<Int>> = Util.transpose(card)
        var winningState: Int? = null

        fun call(num: Int) {
            if (winningState != null) {
                return
            }
            rows = rows.map { row -> row.filter { it != num } }
            cols = cols.map { row -> row.filter { it != num } }

            if (rows.any(List<Int>::isEmpty) || cols.any(List<Int>::isEmpty)) {
                winningState = rows.map(List<Int>::sum).sum() * num
            }
        }

        companion object {
            fun from(state: String): BingoCard {
                val numbers = state.split("\n").map {
                    it.split(" ").filter { numStr -> numStr != "" }.map(String::toInt)
                }

                return BingoCard(numbers)
            }
        }
    }

    data class Bingo(val rounds: List<Int>, val cards: List<BingoCard>) : Iterator<Bingo> {
        val availableRounds = rounds.toMutableList()

        companion object {
            fun from(state: String): Bingo {
                val parts = state.split("\n\n")
                val rounds = parts[0].split(",").map(String::toInt)
                val cards = parts.drop(1).map(BingoCard::from)

                return Bingo(rounds, cards)
            }
        }

        fun getWinners(): List<BingoCard> = cards.filter { card -> card.winningState != null }

        fun getRemaining(): List<BingoCard> = cards.filter { card -> card.winningState == null }

        override fun hasNext() = availableRounds.isNotEmpty()

        override fun next(): Bingo {
            val round = availableRounds.removeAt(0)
            cards.forEach { card -> card.call(round) }

            return this
        }
    }

    override fun partOne(): Any {
        val bingo = Bingo.from(inputString)
        while (bingo.hasNext() && bingo.getWinners().isEmpty()) {
            bingo.next()
        }
        return bingo.getWinners().first().winningState!!
    }

    override fun partTwo(): Any {
        val bingo = Bingo.from(inputString)
        while (bingo.hasNext() && bingo.getRemaining().size > 1) {
            bingo.next()
        }
        val lastRemainingCard = bingo.getRemaining().first()
        while (bingo.hasNext()) {
            bingo.next()
        }
        return lastRemainingCard.winningState!!
    }

}