package days_2021

import util.Day

class Day1 : Day(1) {
    val input = inputList.map { it.toInt() }
    val normalizedInput = input.windowed(3, 1).map { it.sum() }

    private fun getDepthDiff(measurements: List<Int>) = measurements.zipWithNext { a, b ->
        b - a
    }

    fun getNumOfDepthIncreases(measurements: List<Int>) = getDepthDiff(measurements).count { diff -> diff > 0 }

    override fun partOne(): Any {
        return getNumOfDepthIncreases(input)
    }

    override fun partTwo(): Any {
        return getNumOfDepthIncreases(normalizedInput)
    }
}