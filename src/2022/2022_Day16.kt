package `2022`

import readInput
import java.util.*
import kotlin.math.max
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime


data class Node(val rate: Int, val neighborhoodsName: List<String>, val isOpen: Boolean)

private val memo: MutableMap<List<String>, Int> = mutableMapOf()

private lateinit var nonEmptyNode: Map<String, Node>

@OptIn(ExperimentalTime::class)
fun main() {

    data class Turn(val depth: Int, val score: Int, val nodeName: String, val openedNodeNames: MutableList<String>) :
        Comparable<Turn> {
        override fun compareTo(other: Turn): Int {
            return other.score.compareTo(score)
        }

    }


    fun search(maxD: Int, openedNodes: List<String>, score1: Int, nodes: Map<String, Node>, isPart2: Boolean): Int {
        val queue = PriorityQueue<Turn>()
        val seen = mutableSetOf<Turn>()

        queue.add(Turn(0, score1, "AA", openedNodes.toMutableList()))
        var bestScore = if (isPart2) 2369 else 0

        while (queue.isNotEmpty()) {
            val turn = queue.poll()
            val (depth, score, nodeName, openedNodeNames) = turn

            if (depth > maxD)
                continue
            seen.add(turn)

            val sorted =
                nonEmptyNode.filter { !openedNodeNames.contains(it.key) }.map { it.value.rate }.sortedDescending()

            var scoreForOpenedNodes = if (!isPart2)
                sorted
                    .take(((maxD - depth + 1) / 2))
                    .mapIndexed { index, i ->
                        val curDepth = (maxD - depth) - index - 1
                        i * curDepth
                    }.sum()
            else {
                val mult = ((1..maxD step 2) + (1..(maxD - depth + 1) step 2)).map { it * 2 }.sortedDescending()

                sorted
                    .take(maxD / 2 + ((maxD - depth + 1) / 2))
                    .mapIndexed { index, i ->
                        i * mult[index]
                    }.sum()
            }


            if (score + scoreForOpenedNodes < bestScore)
                continue


            bestScore = if (isPart2) {
                val memoKey = openedNodeNames.sorted()
                val value = if (memo.containsKey(memoKey))
                    memo[memoKey]!!
                else search(maxD, openedNodeNames, 0, nodes, false)
                memo[memoKey] = value

                max(bestScore, value + score)
            } else
                max(bestScore, score)


            if (bestScore > 4000)
                println(bestScore)

            if (openedNodeNames.size == nodes.size)
                continue



            if (!openedNodeNames.contains(nodeName) && nodes[nodeName]!!.rate != 0) {
                val newTurn = Turn(
                    depth + 1,
                    score + nodes[nodeName]!!.rate * (maxD - depth - 1),
                    nodeName,
                    openedNodeNames.plus(nodeName).toMutableList()
                )
                if (!seen.contains(newTurn))
                    queue.add(newTurn)
            }

            nodes[nodeName]!!.neighborhoodsName.forEach { neighborhoodName ->
                val newTurn = Turn(
                    depth + 1,
                    score,
                    neighborhoodName,
                    openedNodeNames.toMutableList()
                )
                if (!seen.contains(newTurn))
                    queue.add(newTurn)
            }
        }
        return bestScore
    }

    fun part1(nodes: Map<String, Node>, maxD: Int = 30): String {
        return search(30, emptyList(), 0, nodes, false).toString()
    }

    fun part2(nodes: Map<String, Node>): String {
        return search(26, emptyList(), 0, nodes, true).toString()
    }


    val inputLine = readInput("Day16", "2022")

    val nodes: Map<String, Node> = inputLine.associate {
        val name = it.substringAfter(" ").substringBefore(" ")
        val rate = it.substringAfter("rate=").substringBefore(";").toInt()
        val neighborhoods = it.substringAfter("valve").substringAfter(" ").split(",").map(String::trim)
        name to Node(rate, neighborhoods, false)
    }


    nonEmptyNode = nodes.filter { it.value.rate > 0 }

    println(measureTime {
        println(part1(nodes))
    })

    println(measureTime {
        println(part2(nodes))
    })

}