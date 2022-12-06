package `2022`

import readInput

fun main() {
    fun part1(input: List<String>): String {
        val string = input.first()

        for (i in 3 until string.length) {
            if (
                    string[i] != string[i - 1] && string[i] != string[i - 2] && string[i] != string[i - 3] &&
                    string[i - 1] != string[i - 2] && string[i - 1] != string[i - 2] && string[i - 1] != string[i - 3] &&
                    string[i - 2] != string[i - 3])
                return (i + 1).toString()
        }
        return "0"
    }


    fun part2(input: List<String>): String {
        val string = input.first()

        for (i in 13 until string.length) {
            val checkStr = string.substring(IntRange(i - 13, i))
            var checkPassed = true
            for (j in 0..13) {
                val containtCount = checkStr.sumOf { if (it == checkStr[j]) 1 else 0L }
                if (containtCount != 1L)
                    checkPassed = false
            }
            if (checkPassed) return (i + 1).toString()
        }
        return "0"
    }

    val input = readInput("Day06", "2022")
    println(part1(input))
    println(part2(input))
}
