package days_2021

import util.Day
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day7 : Day(7) {
    val input = inputString.split(",").map(String::toInt)

    fun getExponentialFuelCost(num: Int) = if (num < 1) num else (1..num).reduce(Int::plus)

    fun getCostToTarget(positions: List<Int>, target: Int, costFunc: (num: Int) -> Int) =
        positions.fold(0) { fuelCost, position -> fuelCost + costFunc(abs(target - position)) }

    fun getAllCosts(positions: List<Int>, costFunc: (num: Int) -> Int = { it }): List<Int> {
        val min = positions.reduce { a, b -> min(a, b) }
        val max = positions.reduce { a, b -> max(a, b) }
        return (min..max).map { getCostToTarget(positions, it, costFunc) }
    }

    override fun partOne(): Any {
        return getAllCosts(input).reduce { minFuelCost, newFuelCost ->
            min(minFuelCost, newFuelCost)
        }
    }

    override fun partTwo(): Any {
        return getAllCosts(input, ::getExponentialFuelCost).reduce { minFuelCost, newFuelCost ->
            min(minFuelCost, newFuelCost)
        }
    }

}