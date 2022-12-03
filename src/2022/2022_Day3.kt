package `2022`

import readInput

fun main() {

    fun part1(input: List<String>) = input.sumOf {

        val first = it.take(it.length / 2)
        val second = it.reversed().take(it.length / 2)
        val visited = hashMapOf<Char, Boolean>()
        first.forEach { visited[it] = true }
        second.forEach {
            if (visited[it] == true)
                return@sumOf if (it.isLowerCase()) it.code.toLong() - 96 else it.code.toLong() - 38
        }
        0
    }

    fun part2(input: List<String>) = input.chunked(3).sumOf {

        val first = it[0]
        val second = it[1]
        val third = it[2]
        val visited = hashMapOf<Char, Long>()
        first.forEach { visited[it] = 1 }
        second.forEach { if (visited[it] == 1L) visited[it] = 2 }
        third.forEach {
            if (visited[it] == 2L) return@sumOf if (it.isLowerCase()) it.code.toLong() - 96 else it.code.toLong() - 38
        }
        0
    }

    val input = readInput("Day03", "2022")
    println(part1(input))
    println(part2(input))
}
