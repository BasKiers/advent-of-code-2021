package days_2020

import util.Day

class Day1 : Day(1, 2020) {
    val input = inputList.map { it.toInt() }

    fun getPairs() = input.mapIndexed { i, value -> input.drop(i + 1).map { listOf(value, it) } }

    fun getTriples(): List<List<List<Int>>> {
        val pairs = getPairs()
        return input.flatMapIndexed { i, value -> pairs.drop(i + 1).map { it.map { pair -> pair + value } } }
    }

    fun get2020Product(list: List<List<List<Int>>>) =
        list.flatten().find { it.sum() == 2020 }!!.let { it.reduce(Int::times) }

    override fun partOne(): Any {
        return get2020Product(getPairs())
    }

    override fun partTwo(): Any {
        return get2020Product(getTriples())
    }
}