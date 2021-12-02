package days

class Day2 : Day(2) {
    val commands: List<Pair<String, Int>> = inputList.map { line ->
        val (command, amount) = line.split(" ").zipWithNext()[0]
        Pair(command, amount.toInt())
    }

    open class Position(val x: Int,val y: Int) {
        open operator fun plus(i: Int) = Position(x, y + i)
        open operator fun minus(i: Int) = Position(x, y - i)
        open operator fun times(i: Int) = Position(x + i, y)

        fun getLocation() = x * y
    }

    class AimedPosition(x: Int,y: Int, val aim: Int = 0): Position(x, y) {
        override operator fun plus(i: Int) = AimedPosition(x, y, aim + i)
        override operator fun minus(i: Int) = AimedPosition(x, y, aim - i)
        override operator fun times(i: Int) = AimedPosition(x + i, y + aim * i, aim)
    }

    fun commandRunner(initialPosition: Position, command: Pair<String, Int>): Position {
        val (direction, amount) = command
        
        return when (direction) {
            "up" -> initialPosition - amount
            "down" -> initialPosition + amount
            "forward" -> initialPosition * amount
            else -> initialPosition
        }
    }

    override fun partOne(): Any {
        return commands.fold(Position(0, 0), ::commandRunner).getLocation()
    }

    override fun partTwo(): Any {
        return commands.fold(AimedPosition(0, 0), ::commandRunner).getLocation()
    }
}