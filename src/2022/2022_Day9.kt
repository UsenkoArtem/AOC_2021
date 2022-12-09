package `2022`

import readInput
import kotlin.math.abs


fun main() {
    fun moveTail(h: Pair<Int, Int>, t: Pair<Int, Int>): Pair<Int, Int> {
        if (abs(h.first - t.first) <= 1 && abs(h.second - t.second) <= 1)
            return t

        if (abs(h.first - t.first) == 0) {
            return if (h.second > t.second)
                t.first to t.second + 1
            else t.first to t.second - 1
        }

        if (abs(h.second - t.second) == 0) {
            return if (h.first > t.first)
                t.first + 1 to t.second
            else t.first - 1 to t.second
        }

        if (h.first > t.first) {
            return if (h.second > t.second)
                t.first + 1 to t.second + 1
            else t.first + 1 to t.second - 1
        }

        return if (h.second > t.second)
            t.first - 1 to t.second + 1
        else t.first - 1 to t.second - 1
    }

    fun solution(input: List<String>, ropeLength: Int): String {

        val rope = mutableListOf<Pair<Int, Int>>()
        repeat(ropeLength) { rope.add(1000 to 1000) }

        val tailPosition = mutableSetOf<Pair<Int, Int>>()
        tailPosition.add(rope.first())

        input.forEach { command ->
            val move = command.split(" ").first()
            val count = command.split(" ").last().toInt()
            val changeFunction: (Pair<Int, Int>) -> Pair<Int, Int> = {
                when (move) {
                    "R" -> it.first + 1 to it.second
                    "L" -> it.first - 1 to it.second
                    "U" -> it.first to it.second + 1
                    "D" -> it.first to it.second - 1
                    else -> it
                }
            }


            repeat(count) {
                rope[0] = changeFunction(rope.first())
                repeat(ropeLength - 1) { index ->
                    rope[index + 1] = moveTail(rope[index], rope[index + 1])
                }

                tailPosition.add(rope.last())
            }
        }

        return tailPosition.size.toString()
    }

    val input = readInput("Day09", "2022")
    println(solution(input, 2))
    println(solution(input, 10))
}
