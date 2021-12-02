package days_2020

import util.Day

class Day2 : Day(2, 2020) {
    val validationRegex = """(\d+)-(\d+)\s(\w):\s?(\w+)""".toRegex()
    val input = inputList.map { validationRegex.matchEntire(it)!!.groupValues.drop(1) }
        .map { (arg1, arg2, letter, password) -> Triple(Pair(arg1.toInt(), arg2.toInt()), letter.first(), password) }

    abstract class PasswordValidator(val args: Pair<Int, Int>, val letter: Char) {
        abstract fun isValid(password: String): Boolean
    }

    class WithCharacterValidator(args: Pair<Int, Int>, letter: Char) : PasswordValidator(args, letter) {
        override fun isValid(password: String) =
            password.toList().count { it == letter } in args.first..args.second
    }

    class StrictCharacterValidator(args: Pair<Int, Int>, letter: Char) : PasswordValidator(args, letter) {
        override fun isValid(password: String) =
            args.toList().map(Int::dec)
                .filter { position -> position <= password.length && password[position] == letter }.size == 1
    }

    override fun partOne(): Any {
        return input.map { (args, letter, password) -> WithCharacterValidator(args, letter).isValid(password) }
            .count { it }
    }

    override fun partTwo(): Any {
        return input.map { (args, letter, password) -> StrictCharacterValidator(args, letter).isValid(password) }
            .count { it }
    }
}