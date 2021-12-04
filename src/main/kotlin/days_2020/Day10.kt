package days_2020

import util.Day

class Day10 : Day(10, 2020) {
    val input = inputList.map(String::toInt).let { list -> list + 0 + (list.maxOf { it } + 3) }.sorted()

    override fun partOne(): Any {
        val groups = input.zipWithNext().groupBy { (a, b) -> b - a }
        return groups[1]!!.size * (groups[3]!!.size)
    }

    fun getPossiblePermutations(
        hops: List<Pair<Int, Int>>,
        knownResults: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        val optionalHop = hops.withIndex().find { (_, value) -> value.first + value.second <= 3 }?.index
        if (optionalHop == null) {
            return 1
        }
        val cachedRes = knownResults[hops.toString()];
        if (cachedRes != null) {
            return cachedRes
        }

        val lVal = listOf(hops[optionalHop + 1].let { (_, b) -> hops[optionalHop].toList().sum() to b }) + hops.drop(
            optionalHop + 2
        )
        val rVal = listOf(hops[optionalHop + 1]) + hops.drop(
            optionalHop + 2
        )

        val lRes = getPossiblePermutations(lVal, knownResults);
        knownResults[lVal.toString()] = lRes;
        val rRes = getPossiblePermutations(rVal, knownResults);
        knownResults[rVal.toString()] = rRes;
        return lRes + rRes;
    }

    override fun partTwo(): Any {
        return getPossiblePermutations(input.zipWithNext().map { (a, b) -> b - a }.zipWithNext())
    }

}