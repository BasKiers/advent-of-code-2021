package util

abstract class Day(dayNumber: Int, val year: Int = 2021) {

    // lazy delegate ensures the property gets computed only on first access
    protected val inputList: List<String> by lazy { InputReader.getInputAsList(year, dayNumber) }
    protected val inputString: String by lazy { InputReader.getInputAsString(year, dayNumber) }

    abstract fun partOne(): Any

    abstract fun partTwo(): Any
}