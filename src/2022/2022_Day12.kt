package `2022`

import readInput
import java.util.*
import kotlin.math.min


data class Point(val x: Int, val y: Int)

fun main() {


    fun find(s: Point, e: Point, input: List<String>): Int {
        val point = mutableListOf<MutableList<Int>>()

        input.forEach {
            point += MutableList(it.length) { Int.MAX_VALUE }
        }


        point[s.x][s.y] = 0

        val queue = LinkedList<Point>()
        queue.add(s)

        while (queue.isNotEmpty()) {
            val pos = queue.poll()

            val x = pos.x
            val y = pos.y
            val posPrice = point[x][y]

            if (x - 1 >= 0 && input[x][y].code + 1 >= input[x - 1][y].code) {
                if (posPrice + 1 < point[x - 1][y]) {
                    point[x - 1][y] = posPrice + 1
                    queue.add(Point(x - 1, y))
                }
            }
            if (x + 1 < input.size && input[x][y].code + 1 >= input[x + 1][y].code) {
                if (posPrice + 1 < point[x + 1][y]) {
                    point[x + 1][y] = posPrice + 1
                    queue.add(Point(x + 1, y))
                }
            }
            if (y - 1 >= 0 && input[x][y].code + 1 >= input[x][y - 1].code) {
                if (posPrice + 1 < point[x][y - 1]) {
                    point[x][y - 1] = posPrice + 1
                    queue.add(Point(x, y - 1))
                }
            }
            if (y + 1 < input[x].length && input[x][y].code + 1 >= input[x][y + 1].code) {
                if (posPrice + 1 < point[x][y + 1]) {
                    point[x][y + 1] = posPrice + 1
                    queue.add(Point(x, y + 1))
                }
            }

        }


        return point[e.x][e.y]
    }

    fun part1(input1: List<String>): String {
        val point = mutableListOf<MutableList<Int>>()
        var s = Point(0, 0)
        var e = Point(0, 0)
        repeat(input1.size) {
            point += MutableList(input1[it].length) { Int.MAX_VALUE }
            input1[it].forEachIndexed { index, i ->
                if (i == 'S')
                    s = Point(it, index)
                else
                    if (i == 'E')
                        e = Point(it, index)
            }

        }


        val input = input1.map {
            it.replace("S", "a").replace("E", "{")
        }

        return find(s, e, input).toString()
    }


    fun part2(input1: List<String>): String {
        var e = Point(0, 0)
        repeat(input1.size) {
            input1[it].forEachIndexed { index, i ->
                if (i == 'E')
                    e = Point(it, index)
            }

        }

        val input = input1.map {
            it.replace("S", "a").replace("E", "{")
        }

        var min = Int.MAX_VALUE

        input.forEachIndexed { i, s ->
            s.forEachIndexed { j, c ->
                if (c == 'a')
                    min = min(min, find(Point(i, j), e, input))
            }
        }


        return min.toString()
    }

    val input = readInput("Day12", "2022")
    println(part1(input))
    println(part2(input))
}
