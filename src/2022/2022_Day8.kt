package `2022`

import readInput
import kotlin.math.max


fun main() {
    fun part1(input: List<String>): String {

        val n = input.size
        val m = input[0].length

        var visibleTree = 0

        for (i in 0 until n)
            for (j in 0 until m) {
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) visibleTree += 1
                else {


                    val curTree = input[i][j]

                    val left = input[i].take(j).max()
                    val right = input[i].drop(j + 1).max()
                    val top = input.take(i).maxOfOrNull { it[j] }!!
                    val down = input.drop(i + 1).maxOfOrNull { it[j] }!!
                    if (curTree > left || curTree > right || curTree > down || curTree > top) {
                        visibleTree += 1
                    }
                }
            }


        return visibleTree.toString()
    }


    fun part2(input: List<String>): String {
        val n = input.size
        val m = input[0].length

        var visibleTreePoint = 0

        for (i in 0 until n)
            for (j in 0 until m) {
                if ((i == 0 || j == 0 || i == n - 1 || j == m - 1).not()) {
                    val curTree = input[i][j]

                    var leftMax = j
                    var rightMax = m - j - 1
                    var downMax = n - i - 1
                    var topMax = i

                    for (k in j - 1 downTo 0)
                        if (input[i][k] >= curTree) {
                            leftMax = (j - k)
                            break
                        }

                    for (k in j + 1 until m) {
                        if (input[i][k] >= curTree) {
                            rightMax = k - j
                            break
                        }
                    }


                    for (k in i - 1 downTo 0)
                        if (input[k][j] >= curTree) {
                            topMax = i - k
                            break
                        }

                    for (k in i + 1 until n)
                        if (input[k][j] >= curTree) {
                            downMax = k - i
                            break
                        }



                    visibleTreePoint = max(visibleTreePoint, leftMax * rightMax * downMax * topMax)
                }
            }


        return visibleTreePoint.toString()
    }

    val input = readInput("Day08", "2022")
    println(part1(input))
    println(part2(input))
}
