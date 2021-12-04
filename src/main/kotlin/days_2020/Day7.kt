package days_2020

import util.Day

class Day7 : Day(7, 2020) {
    val inputArgs =
        inputList.map { rule ->
            rule.split(" bags contain ").let { (color, contains) ->
                color to contains.split(" bag, ", " bags, ", " bag.", " bags.")
                    .filter { it.isNotEmpty() && it != "no other" }
                    .map { contain -> contain.substring(0, 1).toInt() to contain.substring(2) }
            }
        }
    val bags = inputArgs.toMap();

    fun getPackingList(
        bag: String,
        amount: Int = 1,
    ): List<Pair<Int, String>> {
        val list = bags[bag]
        if (list == null || list.isEmpty()) {
            return listOf(amount to bag)
        }
        return list.map { (number, color) -> getPackingList(color, number * amount) }.flatten() + (amount to bag)
    }

    override fun partOne(): Any {
        return bags.map { (bag) -> getPackingList(bag) }
            .count { list -> list.any { (_, color) -> color == "shiny gold" } } - 1
    }

    override fun partTwo(): Any {
        return getPackingList("shiny gold").fold(0) { sum, (amount) -> sum + amount } - 1
    }

}