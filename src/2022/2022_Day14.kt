package `2022`

import readInput
import readInputText
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.max
import kotlin.math.min


fun main() {

    fun part1(lines: List<List<Pair<Int, Int>>>): String {
        val map = Array(1000) { Array(1000) { "." } }
        val sandPoint = 500 to 0


        for (line in lines) {

            for (index in 1 until line.size) {
                val prevPoint = line[index - 1]
                val curPoint = line[index]

                val minX = min(prevPoint.first, curPoint.first)
                val maxX = max(prevPoint.first, curPoint.first)
                val minY = min(prevPoint.second, curPoint.second)
                val maxY = max(prevPoint.second, curPoint.second)

                for (y in minY..maxY)
                    for (x in minX..maxX)
                        map[y][x] = "#"

            }
        }

        var result = 0
        repeat(100_000) {
            var sandFindPos = true
            var (x, y) = sandPoint
            while (true) {
                if (y + 1 >= 1000) {
                    sandFindPos = false
                    break
                }
                if (map[y + 1][x] == ".")
                    y++
                else {
                    if (x - 1 < 0) {
                        sandFindPos = false
                        break
                    }
                    if (map[y + 1][x - 1] == ".") {
                        y++
                        x--
                    } else {
                        if (x + 1 > 1000) {
                            sandFindPos = false
                            break
                        }
                        if (map[y + 1][x + 1] == ".") {
                            x++
                            y++
                        } else break
                    }
                }
            }

            if (sandFindPos)
                map[y][x] = "o".also { result++ }
            else return@repeat


//            for (i in 0..10) {
//                for (j in 490..510)
//                    print(map[i][j])
//                println()
//            }
//
//            println()
//            println()
        }


        return result.toString()
    }

    fun part2(lines: List<List<Pair<Int, Int>>>): String {
        val map = Array(1000) { Array(1000) { "." } }
        val sandPoint = 500 to 0

        var absoluteY = 0


        for (line in lines) {

            for (index in 1 until line.size) {
                val prevPoint = line[index - 1]
                val curPoint = line[index]

                val minX = min(prevPoint.first, curPoint.first)
                val maxX = max(prevPoint.first, curPoint.first)
                val minY = min(prevPoint.second, curPoint.second)
                val maxY = max(prevPoint.second, curPoint.second)

                for (y in minY..maxY)
                    for (x in minX..maxX)
                        map[y][x] = "#"

                absoluteY = max(absoluteY, maxY)

            }
        }

        for (i in 0 until 1000)
            map[absoluteY + 2][i] = "#"

        var result = 1
        repeat(1000000) {
            var sandFindPos = false
            var (x, y) = sandPoint
            while (true) {
                if (y + 1 >= 1000) {
                    sandFindPos = false
                    break
                }
                if (map[y + 1][x] == ".") {
                    sandFindPos = true
                    y++
                } else {
                    if (x - 1 < 0) {
                        sandFindPos = false
                        break
                    }
                    if (map[y + 1][x - 1] == ".") {
                        sandFindPos = true
                        y++
                        x--
                    } else {
                        if (x + 1 > 1000) {
                            sandFindPos = false
                            break
                        }
                        if (map[y + 1][x + 1] == ".") {
                            sandFindPos = true
                            x++
                            y++
                        } else break
                    }
                }
            }

            if (sandFindPos)
                map[y][x] = "o".also { result++ }
            else return@repeat

        }


        return result.toString()
    }

    val inputText = readInputText("Day14", "2022")
    val inputLine = readInput("Day14", "2022")

    val lines = inputLine.map {
        it.split("->").map { it.trim() }.map {
            val point = it.split(",")
            point[0].toInt() to point[1].toInt()
        }
    }

    println(part1(lines))
    println(part2(lines))


}
