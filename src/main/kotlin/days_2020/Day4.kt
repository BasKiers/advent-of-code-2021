package days_2020

import util.Day

class Day4 : Day(4, 2020) {
    val input = inputString.split("\n\n")
        .map {
            it.split("\n", " ").filter { it != "" }.map { keyvalue ->
                keyvalue.split(":").let { (key, value) -> key to value }
            }
        }.map { Passport.from(it.toMap()) }

    data class Passport(
        val birthYear: Int,
        val issueYear: Int,
        val expirationYear: Int,
        val height: String,
        val hairColor: String,
        val eyeColor: String,
        val id: String,
        val country: String?
    ) {
        companion object {
            fun from(data: Map<String, String>): Passport? {
                return try {
                    Passport(
                        birthYear = data.getValue("byr").toInt(),
                        issueYear = data.getValue("iyr").toInt(),
                        expirationYear = data.getValue("eyr").toInt(),
                        height = data.getValue("hgt"),
                        hairColor = data.getValue("hcl"),
                        eyeColor = data.getValue("ecl"),
                        id = data.getValue("pid"),
                        country = data["cid"]
                    )
                } catch (e: Exception) {
                    null
                }
            }
        }

        fun isValid(): Boolean {
            // byr (Birth Year) - four digits; at least 1920 and at most 2002.
            //            iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            //            eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
            //            hgt (Height) - a number followed by either cm or in:
            //            If cm, the number must be at least 150 and at most 193.
            //            If in, the number must be at least 59 and at most 76.
            //            hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            //            ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            //            pid (Passport ID) - a nine-digit number, including leading zeroes.
            //            cid (Country ID) - ignored, missing or not.
            return try {
                check(birthYear in 1920..2002)
                check(issueYear in 2010..2020)
                check(expirationYear in 2020..2030)

                val heightAmount = height.dropLast(2).toIntOrNull()
                val heightMetric = height.takeLast(2)
                check(listOf("in", "cm").contains(heightMetric))
                check(heightAmount in (if (heightMetric == "cm") 150..193 else 59..76))

                check("""^#[0-9a-f]{6}$""".toRegex().matches(hairColor))
                check(listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(eyeColor))
                check("""^\d{9}$""".toRegex().matches(id))

                true
            } catch (e: IllegalStateException) {
                println(e)
                false
            }
        }
    }

    override fun partOne(): Any {
        return input.count { it != null }
    }

    override fun partTwo(): Any {
        return input.count { it != null && it.isValid() }
    }
}