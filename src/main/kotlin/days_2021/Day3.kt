package days_2021

import util.Day

class Day3 : Day(3) {
    val input = inputList.map { it.toCharArray().toList() };
    val transposedInput = transpose(input)

    fun <T> transpose(list: List<List<T>>): List<List<T>> {
        val result: MutableList<MutableList<T>> = MutableList(list[0].size) { MutableList(list.size) { list[0][0] } }
        for ((i, col) in list.withIndex()) {
            for ((j, item) in col.withIndex()) {
                result[j][i] = item
            }
        }
        return result
    }

    fun invertBitChar(char: Char) = when (char) {
        '1' -> '0'
        '0' -> '1'
        else -> char
    }

    fun getOccurrences(list: List<Char>) =
        list.groupBy { it }.toList().sortedBy { it.first }.map { (key, occurrences) -> key to occurrences.size }

    fun getMostOccuringChar(list: List<Char>): Char {
        val charOccurences = getOccurrences(list);
        val (mostOccuringChar, mostOccurences) = charOccurences.maxByOrNull { (_, occurrences) -> occurrences }!!
        val isSingleMostOccuring = charOccurences.count { (_, occurences) -> occurences == mostOccurences } == 1

        return when {
            !isSingleMostOccuring -> '1'
            else -> mostOccuringChar
        }
    }

    fun getLeastOccuringChar(list: List<Char>) = getMostOccuringChar(list).let(::invertBitChar)

    fun getSecondLevelData(
        getFilterChar: (list: List<Char>) -> Char,
        list: List<List<Char>>,
        index: Int = 0
    ): List<List<Char>> {
        if (list.size <= 1) {
            return list
        }

        val filterOnChar = getFilterChar(transpose(list)[index])
        return getSecondLevelData(
            getFilterChar,
            list.filter { chars -> chars[index] == filterOnChar },
            index + 1
        )
    }

    fun bitCharArrayToInt(charArray: List<Char>) = charArray.joinToString("").toInt(2)

    override fun partOne(): Any {
        val gamma = transposedInput.map(::getMostOccuringChar).let(::bitCharArrayToInt)
        val epsilon = transposedInput.map(::getLeastOccuringChar).let(::bitCharArrayToInt)

        return gamma * epsilon
    }

    override fun partTwo(): Any {
        val oxygen = getSecondLevelData(::getMostOccuringChar, input).first().let(::bitCharArrayToInt)
        val scrubber = getSecondLevelData(::getLeastOccuringChar, input).first().let(::bitCharArrayToInt)

        return oxygen * scrubber
    }

}