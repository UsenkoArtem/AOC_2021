fun main() {
    fun CalcualteIncreaseDepth(depths: List<Int>): Int {
        var result = 0
        depths.reduce { acc, curValue ->
            if (acc < curValue) result++
            curValue
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val depths = input.map { it.toInt() }
        return CalcualteIncreaseDepth(depths)
    }

    fun part2(input: List<String>): Int {
        val depths = input.map { it.toInt() }
        return CalcualteIncreaseDepth(depths.drop(2).mapIndexed { index, value ->
            value + depths[index] + depths[index + 1]
        })
    }


    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
