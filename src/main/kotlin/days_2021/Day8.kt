package days_2021

import util.Day
import kotlin.math.pow

class Day8 : Day(8) {
    val input = inputList.map { it.split(" | ") }.map { (examples, answer) ->
        examples.split(" ").map(String::toCharArray).map(CharArray::toSet) to answer.split(" ").map(String::toCharArray).map(CharArray::toSet)
    }

    class SevenSegmentDisplay(mapping: Map<Int, Set<Char>>, words: List<Set<Char>>) {
        val values: List<Int>
        val value: Int

        init {
            values = words.map { word -> mapping.toList().first { (_, charSet) -> charSet == word }.first }
            value = values.reversed().reduceIndexed { index, acc, it -> acc + (it * (10F.pow(index))).toInt() }
        }


        companion object {
            fun from(examples: List<Set<Char>>, words: List<Set<Char>>): SevenSegmentDisplay {
                val numberToSegmentMapping: MutableMap<Int, Set<Char>> = mutableMapOf();
                for (number in listOf(2, 3, 4, 7, 6, 5)) {
                    for (example in examples.filter { it.size == number }) {
                        when (example.size) {
                            2 -> numberToSegmentMapping[1] = example
                            3 -> numberToSegmentMapping[7] = example
                            4 -> numberToSegmentMapping[4] = example
                            7 -> numberToSegmentMapping[8] = example
                            6 -> { // 6 9 0
                                val overlaps7 = numberToSegmentMapping[7]!!.all { char -> example.contains(char) }
                                val overlaps4 = numberToSegmentMapping[4]!!.all { char -> example.contains(char) }

                                if (overlaps7 && overlaps4) {
                                    numberToSegmentMapping[9] = example
                                } else if (overlaps7) {
                                    numberToSegmentMapping[0] = example
                                } else {
                                    numberToSegmentMapping[6] = example
                                }
                            }
                            5 -> { // 2, 5, 3
                                val overlaps7 = numberToSegmentMapping[7]!!.all { char -> example.contains(char) }
                                val rightTop = numberToSegmentMapping[1]!!.filter { char ->
                                    !numberToSegmentMapping[6]!!.contains(char)
                                }
                                val overlapsRightTop = rightTop.all { char -> example.contains(char) }

                                if (overlaps7) {
                                    numberToSegmentMapping[3] = example
                                } else if (overlapsRightTop) {
                                    numberToSegmentMapping[2] = example
                                } else {
                                    numberToSegmentMapping[5] = example
                                }
                            }
                        }
                    }
                }

                return SevenSegmentDisplay(numberToSegmentMapping, words)
            }
        }
    }

    override fun partOne(): Any {
        return input.sumOf { (examples, words) ->
            SevenSegmentDisplay.from(examples, words).values.filter {
                listOf(
                    1,
                    4,
                    7,
                    8
                ).contains(it)
            }.size
        }
    }

    override fun partTwo(): Any {
        return input.sumOf { (examples, words) -> SevenSegmentDisplay.from(examples, words).value }
    }

}