package days

class Day2 : Day(2) {
    val commands: List<Pair<String, Int>> = inputList.map { line ->
        val (command, amount) = line.split(" ").zipWithNext()[0]
        Pair(command, amount.toInt())
    }

    fun linearCommandRunner(initialPosition: Triple<Int, Int, Int>, command: Pair<String, Int>): Triple<Int, Int, Int> {
        val (x, y, aim) = initialPosition
        val (direction, amount) = command

        return when (direction) {
            "up" -> Triple(x, y - amount, aim)
            "down" -> Triple(x, y + amount, aim)
            "forward" -> Triple(x + amount, y, aim)
            else -> initialPosition
        }
    }

    fun aimedCommandRunner(initialPosition: Triple<Int, Int, Int>, command: Pair<String, Int>): Triple<Int, Int, Int> {
        val (x, y, aim) = initialPosition
        val (direction, amount) = command

        return when (direction) {
            "up" -> Triple(x, y - amount, aim)
            "down" -> Triple(x, y + amount, aim)
            "forward" -> Triple(x + amount, y, aim)
            else -> initialPosition
        }
    }

    fun getFinalPosition(calcPositionFunc: (Triple<Int, Int, Int>, command: Pair<String, Int>) -> Triple<Int, Int, Int>): Pair<Int, Int> {
        return commands.fold(Triple(0, 0, 0)) { position, command ->
            calcPositionFunc(position, command)
        }.let { (x, y) -> x to y }
    }

    override fun partOne(): Any {
        return getFinalPosition(::linearCommandRunner).let { (x, y) -> x * y }
    }

    override fun partTwo(): Any {
        return getFinalPosition(::aimedCommandRunner).let { (x, y) -> x * y }
    }
}