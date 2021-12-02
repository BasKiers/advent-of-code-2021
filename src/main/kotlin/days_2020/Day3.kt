package days_2020;

import util.Day

class Day3 : Day(3, 2020) {
    val input = World(inputString)

    enum class Geology {
        TREE, GROUND
    }

    class World(val geology: List<List<Geology>>) {
        constructor(geologyStr: String) : this(
            geologyStr.split("\n").map { lineStr ->
                lineStr.toCharArray().map {
                    when (it) {
                        '#' -> Geology.TREE
                        else -> Geology.GROUND
                    }
                }
            }
        )

        fun getGeology(x: Int, y: Int) = if (y < geology.size) geology[y].let { line -> line[x % line.size] } else null;

        override fun toString() = geology.joinToString("\n") { line ->
            line.joinToString("") { if (it == Geology.TREE) "#" else "." }
        }
    }

    class Plane(val world: World, val slope: Pair<Int, Int>) : Iterator<Plane> {
        var x: Int = 0
        var y: Int = 0

        fun hasFlownIntoTree() = world.getGeology(x, y) == Geology.TREE

        override fun hasNext() = world.getGeology(x, y) != null

        override fun next(): Plane {
            x += slope.first
            y += slope.second
            return this
        }

        override fun toString() = "(x: $x, y: $y)"
    }

    fun getTreeEncounters(plane: Plane) = plane.iterator().asSequence().map { it.hasFlownIntoTree() }.count { it }

    override fun partOne(): Any {
        return getTreeEncounters(Plane(input, 3 to 1))
    }

    override fun partTwo(): Any {
        val slopes = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
        return slopes.fold(1L) { acc, slope -> acc * getTreeEncounters(Plane(input, slope)) }
    }
}
