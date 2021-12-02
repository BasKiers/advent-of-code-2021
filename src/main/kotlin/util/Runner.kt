package util

import org.reflections.Reflections
import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

@ExperimentalTime
object Runner {

    @JvmStatic
    fun main(args: Array<String>) {
        var year = 2021
        var day: Int? = null

        if (args.isNotEmpty()) {
            year = try {
                args[0].toInt()
            } catch (e: NumberFormatException) {
                printError("Year argument must be an integer")
                return
            }
        }
        if(args.size >= 2) {
            day = try {
                args[1].toInt()
            } catch (e: NumberFormatException) {
                printError("Day argument must be an integer")
                return
            }
        }

        val dayClasses = getAllDayClasses(year)?.filter { day == null || dayNumber(it.simpleName) == day }

        if (dayClasses != null) {
            println("\n=== Advent of Code $year ===")
            dayClasses.sortedBy { dayNumber(it.simpleName) }.forEach { printDay(it) }
        } else {
            printError("Couldn't find day classes - make sure you're in the right directory and try building again")
        }
    }

    private fun getAllDayClasses(year: Int): MutableSet<Class<out Day>>? {
        return Reflections("days_$year").getSubTypesOf(Day::class.java)
    }

    private fun printDay(dayClass: Class<out Day>) {
        println("\n=== DAY ${dayNumber(dayClass.simpleName)} ===")
        val day = dayClass.constructors[0].newInstance() as Day

        val partOne = measureTimedValue { day.partOne() }
        val partTwo = measureTimedValue { day.partTwo() }
        printParts(partOne, partTwo)
    }

    private fun printParts(partOne: TimedValue<Any>, partTwo: TimedValue<Any>) {
        val padding = max(
            partOne.value.toString().length,
            partTwo.value.toString().length
        ) + 14        // 14 is 8 (length of 'Part 1: ') + 6 more
        println("Part 1: ${partOne.value}".padEnd(padding, ' ') + "(${partOne.duration})")
        println("Part 2: ${partTwo.value}".padEnd(padding, ' ') + "(${partTwo.duration})")
    }

    private fun printError(message: String) {
        System.err.println("\n=== ERROR ===\n$message")
    }

    private fun dayNumber(dayClassName: String) = dayClassName.replace("Day", "").toInt()
}
