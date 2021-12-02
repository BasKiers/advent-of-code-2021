package days_2021

import util.Day

class Day2 : Day(2) {
    val commands: List<Pair<String, Int>> = inputList.map { line ->
        val (command, amount) = line.split(" ").zipWithNext()[0]
        Pair(command, amount.toInt())
    }

    abstract class Vessel(val x: Int, val y: Int) {
        abstract fun up(i: Int): Vessel
        abstract fun down(i: Int): Vessel
        abstract fun forward(i: Int): Vessel

        fun getLocation() = x * y
    }

    class DiveBoat(x: Int, y: Int): Vessel(x, y) {
        override fun up(i: Int) = DiveBoat(x, y - i)
        override fun down(i: Int) = DiveBoat(x, y + i)
        override fun forward(i: Int) = DiveBoat(x + i, y)
    }

    class Submarine(x: Int, y: Int, val aim: Int = 0): Vessel(x, y) {
        override fun up(i: Int) = Submarine(x, y, aim - i)
        override fun down(i: Int) = Submarine(x, y, aim + i)
        override fun forward(i: Int) = Submarine(x + i, y + aim * i, aim)
    }

    fun executeCommand(vessel: Vessel, command: Pair<String, Int>): Vessel {
        val (direction, amount) = command

        return when (direction) {
            "up" -> vessel.up(amount)
            "down" -> vessel.down(amount)
            "forward" -> vessel.forward(amount)
            else -> vessel
        }
    }

    override fun partOne(): Any {
        return commands.fold(DiveBoat(0, 0), ::executeCommand).getLocation()
    }

    override fun partTwo(): Any {
        return commands.fold(Submarine(0, 0), ::executeCommand).getLocation()
    }
}