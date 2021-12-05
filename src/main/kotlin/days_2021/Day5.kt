package days_2021

import util.Day
import kotlin.math.absoluteValue

class Day5 : Day(5) {
    val lines: List<Pair<Pair<Int, Int>, Pair<Int, Int>>> = inputList.map { line ->
        val (from, to) = line.split(" -> ")
        val (fromX, fromY) = from.split(",").map(String::toInt)
        val (toX, toY) = to.split(",").map(String::toInt)
        (fromX to fromY) to (toX to toY)
    }

    class Diagram(width: Int, height: Int) {
        val points: MutableList<MutableList<Int>> = MutableList(height) { MutableList(width) { 0 } }

        fun drawLine(fromPair: Pair<Int, Int>, toPair: Pair<Int, Int>): Diagram {
            val (x1, y1) = fromPair
            val (x2, y2) = toPair

            if (x1 == x2) {
                for (y in minOf(y1, y2)..maxOf(y1, y2)) {
                    points[y][x1] += 1
                }
            } else if (y1 == y2) {
                for (x in minOf(x1, x2)..maxOf(x1, x2)) {
                    points[y1][x] += 1
                }
            } else if (x1 - x2 != 0 && (x1 - x2).absoluteValue == (y1 - y2).absoluteValue) {
                val xDir = if (x2 - x1 < 0) -1 else +1
                val yDir = if (y2 - y1 < 0) -1 else +1
                println("$fromPair, $toPair, $xDir, $yDir")
                for (i in 0..maxOf(x1 - x2, x2 - x1)) {
                    points[y1 + yDir * i][x1 + xDir * i] += 1
                }
            }

            return this
        }

        override fun toString() =
            "\n" + points.map { row -> row.map { if (it == 0) '.' else it.toString() }.joinToString("") }
                .joinToString("\n")
    }

    override fun partOne(): Any {
        val diagram = Diagram(1000, 1000)
        return lines.fold(diagram) { target, (from, to) ->
            target.drawLine(
                from,
                to
            )
        }.points.fold(0) { acc, row -> acc + row.count { it >= 2 } }
    }

    override fun partTwo(): Any {
        return "bar"
    }

}