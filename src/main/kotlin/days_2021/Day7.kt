package days_2021

import util.Day
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day7 : Day(7) {
    val input = inputString.split(",").map(String::toInt)
    val minInputVal = input.reduce { a, b -> min(a, b) }
    val maxInputVal = input.reduce { a, b -> max(a, b) }

    fun getExponentialFuelCost(num: Int) = if (num < 1) num else (1..num).reduce(Int::plus)

    fun getCostToTarget(positions: List<Int>, target: Int, costFunc: (num: Int) -> Int) =
        positions.fold(0) { fuelCost, position -> fuelCost + costFunc(abs(target - position)) }

    fun getMinCost(min: Int, max: Int, positions: List<Int>, costFunc: (num: Int) -> Int = { it }): Int {
        val step = (max - min) / 3
        val left = min + step
        val right = max - step
        val leftRes = getCostToTarget(positions, left, costFunc)
        val rightRes = getCostToTarget(positions, right, costFunc);

        return if (leftRes < rightRes) {
            if (left == min) leftRes else getMinCost(min, right, positions, costFunc)
        } else {
            if (left == max) rightRes else getMinCost(left, max, positions, costFunc)
        }
    }

    override fun partOne(): Any {
        return getMinCost(minInputVal, maxInputVal, input)
    }

    override fun partTwo(): Any {
        return getMinCost(minInputVal, maxInputVal, input, ::getExponentialFuelCost)
    }

}