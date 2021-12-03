package days_2020

import util.Day

class Day5 : Day(5, 2020) {
    val input = inputList.map { BoardingPass.from(it) }

    class BoardingPass(val row: UInt, val seat: UInt) {
        val id = row * 8u + seat

        companion object {
            fun from(data: String): BoardingPass {
                val row = data.take(7).map { if (it == 'B') 1 else 0 }.joinToString("").toUInt(2)
                val seat = data.takeLast(3).map { if (it == 'R') 1 else 0 }.joinToString("").toUInt(2)

                return BoardingPass(row, seat);
            }
        }

        override fun toString() = "$row|$seat|#$id"
    }

    override fun partOne(): Any {
        return input.map { it.id }.sortedDescending().first()
    }

    override fun partTwo(): Any {
        return input.map { it.id }.sortedDescending().zipWithNext().find { (a, b) -> a - b > 1u }!!
            .let { (_, b) -> b + 1u }
    }
}