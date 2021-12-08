package days_2021

import util.Day
import kotlin.math.pow

class Day8 : Day(8) {
    val input = inputList.map { it.split(" | ") }.map { (examples, answer) ->
        examples.split(" ").map(String::toCharArray) to answer.split(" ").map(String::toCharArray)
    }

    class SevenSegmentDisplay(mapping: Map<Int, Set<Char>>, words: List<Set<Char>>) {
        val values: List<Int>
        val value: Int

        init {
            values = words.map { word -> mapping.toList().first { (_, charSet) -> charSet == word }.first }
            value = values.reversed().reduceIndexed { index, acc, it -> acc + (it * (10F.pow(index))).toInt() }
        }


        companion object {
            fun from(examples: List<CharArray>, words: List<CharArray>): SevenSegmentDisplay {
                val numberToSegmentMapping: MutableMap<Int, Set<Char>> = mutableMapOf();
                for (number in listOf(2, 3, 4, 7, 6, 5)) {
                    for (example in (examples + words).filter { it.size == number }.map(CharArray::toSet)) {
                        when (example.size) {
                            2 -> numberToSegmentMapping[1] = example
                            3 -> numberToSegmentMapping[7] = example
                            4 -> numberToSegmentMapping[4] = example
                            7 -> numberToSegmentMapping[8] = example
                            6 -> { // 6 9 0
                                val overlap = (numberToSegmentMapping.getOrDefault(
                                    1,
                                    setOf()
                                ) + numberToSegmentMapping.getOrDefault(
                                    7,
                                    setOf()
                                )).all { char -> example.contains(char) }
                                val overlaps4 = numberToSegmentMapping.getOrDefault(
                                    4,
                                    setOf()
                                ).all { char -> example.contains(char) }

                                if (overlap && overlaps4) {
                                    numberToSegmentMapping[9] = example
                                } else if (overlap) {
                                    numberToSegmentMapping[0] = example
                                } else {
                                    numberToSegmentMapping[6] = example
                                }
                            }
                            5 -> { // 2, 5, 3
                                val overlapWith3 = numberToSegmentMapping.getOrDefault(
                                    1,
                                    setOf()
                                ) + numberToSegmentMapping.getOrDefault(7, setOf())

                                if (overlapWith3.all { char -> example.contains(char) }) {
                                    numberToSegmentMapping[3] = example
                                } else if (numberToSegmentMapping.contains(4) && numberToSegmentMapping.contains(7)) {
                                    val mask = numberToSegmentMapping.getOrDefault(
                                        4,
                                        setOf()
                                    ) + numberToSegmentMapping.getOrDefault(7, setOf())
                                    val res = example.filter { char -> !mask.contains(char) }
                                    if (res.size > 1) {
                                        numberToSegmentMapping[2] = example
                                    } else {
                                        numberToSegmentMapping[5] = example
                                    }
                                } else if (numberToSegmentMapping.contains(6)) {
                                    val mask = numberToSegmentMapping.getOrDefault(6, setOf())
                                    val res = example.filter { char -> !mask.contains(char) }
                                    if (res.size == 1) {
                                        numberToSegmentMapping[2] = example
                                    } else {
                                        numberToSegmentMapping[5] = example
                                    }
                                }
                            }
                        }
                    }
                }

                return SevenSegmentDisplay(numberToSegmentMapping, words.map(CharArray::toSet))
            }
        }
    }

    override fun partOne(): Any {
        return input.map { (examples, words) ->
            SevenSegmentDisplay.from(examples, words).values.filter {
                listOf(
                    1,
                    4,
                    7,
                    8
                ).contains(it)
            }.size
        }.sum()
    }

    override fun partTwo(): Any {
        return input.map { (examples, words) -> SevenSegmentDisplay.from(examples, words).value }.sum()
    }

}