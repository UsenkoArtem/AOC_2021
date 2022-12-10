package `2022`

import readInput


fun main() {

    fun emulate(input: List<String>): List<Int> {
        val vals = mutableListOf<Int>()
        var x = 1

        for (instr in input) {
            vals.add(x)

            if (instr.startsWith("addx")) {
                vals.add(x)
                x += instr.split(" ")[1].toInt()
            }
        }

        return vals
    }

    fun part1(input: List<String>): String {
        val values = emulate(input)
        var sum = 0
        for (i in 20..220 step 40) {
            sum += i * values[i - 1]
        }

        return sum.toString()
    }

    fun part2(input: List<String>): String {
        val values = emulate(input)
        val screen = StringBuilder()
        for (i in 0 until 240) {
            if (i % 40 == 0) screen.appendLine()
            val sprite = values[i]
            if ((i % 40) in sprite - 1..sprite + 1) screen.append("#") else screen.append(" ")
        }

        return screen.toString()
    }

    val input = readInput("Day10", "2022")
    println(part1(input))
    println(part2(input))
}
