import java.util.*


private var N = 0
private var M = 0
private fun Solution(map: List<List<Int>>): Long {
    N = map.size
    M = map.first().size
    val graph = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
    map.forEachIndexed { i, row ->
        row.forEachIndexed { j, _ ->
            val mainNodeIndex = i * N + j
            GetNeighborhoods(i to j).map {
                val nodeIndex = it.first * N + it.second
                val nodeValue = map[it.first][it.second]
                graph.putIfAbsent(mainNodeIndex, mutableListOf())
                graph[mainNodeIndex]!!.add(nodeIndex to nodeValue)
            }
        }
    }
    return GetMinPath(graph)
}

fun GetMinPath(graph: MutableMap<Int, MutableList<Pair<Int, Int>>>): Long {
    val visited = mutableSetOf<Int>()
    val cost = IntArray(N * M)
    cost.fill(Int.MAX_VALUE)
    cost[0] = 0

    val compareByLength = compareBy<Pair<Int, Int>> { it.second }


    val queue = PriorityQueue(compareByLength)

    queue.add(0 to 0)

    while (visited.size != N * M) {

        if (queue.isEmpty())
            break

        val (u, _) = queue.poll()

        if (visited.contains(u))
            continue

        visited.add(u)

        graph[u]!!.forEach { (v, value) ->
            if (!visited.contains(v)) {
                val newCost = cost[u] + value
                if (newCost < cost[v])
                    cost[v] = newCost
                queue.add(v to cost[v])
            }
        }
    }
    return cost.last().toLong()

}

private fun GetNeighborhoods(point: Pair<Int, Int>) = buildList {
    add(0 to 1)
    add(0 to -1)
    add(1 to 0)
    add(-1 to 0)
}.mapNotNull {
    val (i, j) = point
    val (newI, newJ) = (it.first + i to it.second + j)
    if (newJ > -1 && newI > -1 && newI < N && newJ < M)
        newI to newJ
    else null
}

fun main() {
    val input = readInput("Day15")
    val map = input.map { line -> line.map { Character.getNumericValue(it) } }
    val mapDuplicateByVerticalLine: List<MutableList<Int>> = map.map { line ->
        val result = mutableListOf<Int>()
        repeat(5) { rep ->
            result.plusAssign(line.map { if (it + rep > 9) it + rep - 9 else it + rep })
        }
        result
    }
    val mapDuplicateByHorizontalLine = mutableListOf<List<Int>>()
    repeat(5) { rep ->
        mapDuplicateByVerticalLine.map { line ->
            mapDuplicateByHorizontalLine.plusAssign(line.map { if (it + rep > 9) it + rep - 9 else it + rep })
        }
    }

    println(Solution(map))
    println(Solution(mapDuplicateByHorizontalLine))
}