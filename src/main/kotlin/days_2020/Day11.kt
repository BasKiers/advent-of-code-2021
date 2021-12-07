package days_2020

import util.Day
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

class Day11 : Day(11, 2020) {
    val input = inputList.map { row ->
        row.toCharArray().map {
            when (it) {
                '#' -> Tile.OCCUPIED_SEAT
                'L' -> Tile.EMPTY_SEAT
                else -> Tile.FLOOR
            }
        }
    }

    enum class Tile {
        FLOOR, EMPTY_SEAT, OCCUPIED_SEAT, WALL
    }

    fun getNextTile(floorPlan: List<List<Tile>>, tile: Tile, position: Pair<Int, Int>): Tile {
        val (x, y) = position
        val adjacentTiles: Map<Tile, Int> by lazy {

            val subList = floorPlan.subList(max(0, y - 1), min(y + 2, floorPlan.size))
                .map { row -> row.subList(max(0, x - 1), min(x + 2, row.size)) }

            subList.flatten().groupBy { it }
                .mapValues { (_, it) -> it.size }
        }

        return when (tile) {
            Tile.OCCUPIED_SEAT -> if (adjacentTiles.getOrDefault(Tile.OCCUPIED_SEAT, 0) > 4) Tile.EMPTY_SEAT else tile
            Tile.EMPTY_SEAT -> if (adjacentTiles.getOrDefault(Tile.OCCUPIED_SEAT, 0) == 0) Tile.OCCUPIED_SEAT else tile
            else -> tile
        }
    }

    fun getNextState(floorPlan: List<List<Tile>>): List<List<Tile>> {
        return floorPlan.mapIndexed { y, rows ->
            rows.mapIndexed { x, tile -> getNextTile(floorPlan, tile, x to y) }
        }
    }

    val directions = listOf(1 to 0, 1 to 1, 0 to 1, -1 to 1, -1 to 0, -1 to -1, 0 to -1, 1 to -1)
    fun getNextTile2(floorPlan: List<List<Tile>>, tile: Tile, position: Pair<Int, Int>): Tile {
        val adjacentTiles: Map<Tile, Int> by lazy {
            val subList = directions.map { (xDir, yDir) ->
                var pos = position
                var tileAtPos: Tile;
                do {
                    pos = pos.first + xDir to pos.second + yDir
                    tileAtPos =
                        floorPlan.getOrElse(pos.second) { listOf(Tile.WALL) }.getOrElse(pos.first) { Tile.WALL };
                } while (tileAtPos == Tile.FLOOR)
                tileAtPos
            }
            subList.groupBy { it }
                .mapValues { (_, it) -> it.size }
        }

        return when (tile) {
            Tile.OCCUPIED_SEAT -> if (adjacentTiles.getOrDefault(Tile.OCCUPIED_SEAT, 0) > 4) Tile.EMPTY_SEAT else tile
            Tile.EMPTY_SEAT -> if (adjacentTiles.getOrDefault(Tile.OCCUPIED_SEAT, 0) == 0) Tile.OCCUPIED_SEAT else tile
            else -> tile
        }
    }

    fun getNextState2(floorPlan: List<List<Tile>>): List<List<Tile>> {
        return floorPlan.mapIndexed { y, rows ->
            rows.mapIndexed { x, tile -> getNextTile2(floorPlan, tile, x to y) }
        }
    }

    fun getOccupiedSeats(floorPlan: List<List<Tile>>): Int = floorPlan.flatten().count { it == Tile.OCCUPIED_SEAT }

    fun floorPlanToString(floorPlan: List<List<Tile>>): String =
        floorPlan.map { rows ->
            rows.joinToString("") { tile ->
                when (tile) {
                    Tile.OCCUPIED_SEAT -> "#"
                    Tile.EMPTY_SEAT -> "L"
                    Tile.FLOOR -> "."
                    Tile.WALL -> "|"
                }
            }
        }.joinToString("\n")

    fun statesAreEqual(stateA: List<List<Tile>>, stateB: List<List<Tile>>): Boolean =
        stateA.flatten().zip(stateB.flatten()).all { (a, b) -> a == b }

    override fun partOne(): Any {
        var stateTransition = input to input
        do {
            stateTransition = stateTransition.second to getNextState(stateTransition.second)
        } while (!statesAreEqual(stateTransition.first, stateTransition.second))
        return getOccupiedSeats(stateTransition.second)
    }

    override fun partTwo(): Any {
        var stateTransition = input to input
        do {
            stateTransition = stateTransition.second to getNextState2(stateTransition.second)
        } while (!statesAreEqual(stateTransition.first, stateTransition.second))
        return getOccupiedSeats(stateTransition.second)
    }

}