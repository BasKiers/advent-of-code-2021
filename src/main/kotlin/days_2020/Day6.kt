package days_2020

import util.Day

class Day6 : Day(6, 2020) {
    val input = inputString.split("\n\n")
        .map { group -> group.split("\n").filter { it != "" }.map { answers -> answers.split("").filter { it != "" } } }


    override fun partOne(): Any {
        return input.map { it.flatten().toSet().size }.sum()
    }

    override fun partTwo(): Any {
        return input.map { group ->
            group.flatten().toSet().filter { answer -> group.all { it.contains(answer) } }.size
        }.sum()
    }
}