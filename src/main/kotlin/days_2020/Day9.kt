package days_2020

import util.Day

class Day9 : Day(9, 2020) {
    val input = inputList.map(String::toLong)

    fun <T> getPairs(list: List<T>) = list.flatMapIndexed { i, value -> list.drop(i + 1).map { listOf(value, it) } }

    fun getInvalidValues(list: List<Long>, preamble: Int) = list.windowed(preamble + 1)
        .filter { getPairs(it.take(preamble)).map(List<Long>::sum).none { sum -> sum == it.last() } }
        .map(List<Long>::last)

    fun getWindowThatSumsTo(list: List<Long>, target: Long, windowSize: Int = 2): List<Long> {
        val window = list.windowed(windowSize)
            .find { it.sum() == target }
        return window ?: getWindowThatSumsTo(list, target, windowSize + 1)
    }

    override fun partOne(): Any {
        return getInvalidValues(input, 25).first()
    }

    override fun partTwo(): Any {
        val window = getWindowThatSumsTo(input, 542529149L)
        return window.maxOf { it } + window.minOf { it }
    }

}