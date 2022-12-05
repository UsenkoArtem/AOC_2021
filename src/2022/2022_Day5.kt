package `2022`

import readInput

fun main() {
    val position = hashMapOf<Int, MutableList<String>>().also {
        it[1] = mutableListOf("B", "S", "V", "Z", "G", "P", "W")
        it[2] = mutableListOf("J", "V", "B", "C", "Z", "F")
        it[3] = mutableListOf("V", "L", "M", "H", "N", "Z", "D", "C")
        it[4] = mutableListOf("L", "D", "M", "Z", "P", "F", "J", "B")
        it[5] = mutableListOf("V", "F", "C", "G", "J", "B", "Q", "H")
        it[6] = mutableListOf("G", "F", "Q", "T", "S", "L", "B")
        it[7] = mutableListOf("L", "G", "C", "Z", "V")
        it[8] = mutableListOf("N", "L", "G")
        it[9] = mutableListOf("J", "F", "H", "C")

    }
    val positioTEst = hashMapOf<Int, MutableList<String>>().also {
        it[1] = mutableListOf("Z", "N")
        it[2] = mutableListOf("M", "C", "D")
        it[3] = mutableListOf("P")

    }

    fun part1(input: List<String>): String {
        val part1Position = position.toMutableMap()

        input.forEach {
            val move = it.substringAfter(" ").substringBefore(" ").toInt()
            val pos1 = it.substringAfter("from").substringBefore("to").trim().toInt()
            val pos2 = it.substringAfter("to").trim().toInt()


            if (part1Position[pos1]?.isNotEmpty() == true) {
                part1Position[pos1]!!.subList(part1Position[pos1]!!.size - move, part1Position[pos1]!!.size).forEach {
                    part1Position[pos2]!!.add(it)
                }
                repeat(move) {
                    part1Position[pos1]!!.removeLast()
                }
            }
            repeat(move) {

            }
        }


        return part1Position.map { it.value.last() }.joinToString("")
    }

    fun part2(input: List<String>) = 0

    val input = readInput("Day05", "2022")
    println(part1(input))
    println(part2(input))
}
