package days_2021

import util.Day

class Day9 : Day(9) {
    val input = inputList.map { row -> row.toCharArray().map(Char::digitToInt) }

    fun getPosition(seaFloor: List<List<Int>>, position: Pair<Int, Int>) =
        seaFloor.getOrNull(position.second)?.getOrNull(position.first)

    val directions = listOf(1 to 0, 0 to 1, -1 to 0, 0 to -1)
    fun getAdjacentHeights(seaFloor: List<List<Int>>, position: Pair<Int, Int>): List<Pair<Pair<Int, Int>, Int>> {
        return directions.mapNotNull { (xDir, yDir) ->
            val dirPos = xDir + position.first to yDir + position.second
            val adjacentHeight = getPosition(seaFloor, dirPos)
            if (adjacentHeight != null) {
                dirPos to adjacentHeight
            } else {
                null
            }
        }
    }

    fun getBasin(seaFloor: List<List<Int>>, position: Pair<Int, Int>): List<Pair<Pair<Int, Int>, Int>>? {
        val height = getPosition(seaFloor, position)
        if (height == null || height == 9) {
            return null
        }

        val discoveredPositions: MutableSet<Pair<Int, Int>> = mutableSetOf()
        val heightsInBasin: MutableMap<Pair<Int, Int>, Int> = mutableMapOf(position to height)
        while (discoveredPositions.size != heightsInBasin.size) {
            val (positionToSearch) = heightsInBasin.asSequence().first { (pos) -> !discoveredPositions.contains(pos) }
            val newHeights = getAdjacentHeights(seaFloor, positionToSearch)
                .filterNot { (pos, height) -> height == 9 || heightsInBasin.any { (p) -> p == pos } }
            for ((newPos, newHeight) in newHeights) {
                heightsInBasin[newPos] = newHeight
            }
            discoveredPositions.add(positionToSearch)
        }

        return heightsInBasin.toList()
    }

    override fun partOne(): Any {
        return input.flatMapIndexed { y, row ->
            row.filterIndexed { x, height ->
                getAdjacentHeights(
                    input,
                    x to y
                ).all { (_, adjacentHeight) -> adjacentHeight > height }
            }.map { lowPoint -> lowPoint + 1 }
        }.sum()
    }

    override fun partTwo(): Any {
        var positions = input.flatMapIndexed { y, row -> row.mapIndexed { x, _ -> x to y } }
        val basins: MutableList<List<Pair<Pair<Int, Int>, Int>>> = mutableListOf()
        while (positions.isNotEmpty()) {
            val (position, positionsLeft) = positions.first() to positions.drop(1)
            val basin = getBasin(input, position)
            if (basin != null) {
                basins.add(basin)
                positions = positionsLeft.filterNot { pos ->
                    basin.any { (basinPos) -> basinPos == pos }
                }
            } else {
                positions = positionsLeft
            }
        }
        return basins.map { basin -> basin.size }.sorted().takeLast(3).reduce { acc, size -> acc * size }
    }
}