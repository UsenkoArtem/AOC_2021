package `2022`

import readInput

fun main() {

    fun part1(input: List<String>) = input.sumOf {
        val pair1 = it.split(",")[0]
        val pair2 = it.split(",")[1]
        val l1 = pair1.split("-")[0].toLong()
        val r1 = pair1.split("-")[1].toLong()
        val l2 = pair2.split("-")[0].toLong()
        val r2 = pair2.split("-")[1].toLong()

        return@sumOf if (l1 <= l2 && r1 >= r2) 1L
        else if (l2 <= l1 && r2 >= r1) 1L
        else 0L

    }

    fun part2(input: List<String>) = input.sumOf {
        val pair1 = it.split(",")[0]
        val pair2 = it.split(",")[1]
        val l1 = pair1.split("-")[0].toLong()
        val r1 = pair1.split("-")[1].toLong()
        val l2 = pair2.split("-")[0].toLong()
        val r2 = pair2.split("-")[1].toLong()

        return@sumOf if (l1 > r2 || r1 < l2) 0 else 1L

    }

    val input = readInput("Day04", "2022")
    println(part1(input))
    println(part2(input))
}
