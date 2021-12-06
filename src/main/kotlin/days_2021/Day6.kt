package days_2021

import util.Day

class Day6 : Day(6) {
    val inputGroups = inputString.split(",").map(String::toInt).groupBy { it };
    val input = List(9) { index -> inputGroups.getOrDefault(index, listOf()).size.toLong() }

    fun getNextDay(fishList: List<Long>): List<Long> {
        return List(9) { index ->
            when (index) {
                8 -> fishList[0]
                6 -> fishList[7] + fishList[0]
                else -> fishList[index + 1]
            }
        }
    }

    override fun partOne(): Any {
        var list = input
        for (i in 1..80) {
            list = getNextDay(list)
        }
        return list.sum()
    }

    override fun partTwo(): Any {
        var list = input
        for (i in 1..256) {
            list = getNextDay(list)
        }
        return list.sum()
    }

}