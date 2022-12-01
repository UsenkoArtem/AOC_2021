fun main() {
    val input = readInput("Day20")
    val betterizer = input.first()

    var N = 0
    var M = 0

    var map = input.drop(2).map { it }.toMutableList()

    fun getSumMatrix(point: Pair<Int, Int>) = buildList {
        add(-1 to -1)
        add(-1 to 0)
        add(-1 to 1)
        add(0 to -1)
        add(0 to 0)
        add(0 to 1)
        add(1 to -1)
        add(1 to 0)
        add(1 to 1)
    }.map {
        val (i, j) = point
        val (newI, newJ) = (it.first + i to it.second + j)
        if (newJ > -1 && newI > -1 && newI < N && newJ < M) newI to newJ
        else null
    }

    repeat(50) {
        val fillElement = if (betterizer[0] == '#') if (it % 2 == 0) "." else "#" else "."
        repeat(1) {
            val row = fillElement.repeat(map.first().length)
            map.add(0, row)
            map.add(row)
            map = map.map { "$fillElement$it$fillElement" }.toMutableList()
        }
        N = map.size
        M = map.first().length

        map = map.mapIndexed { rowIndex, row ->
            row.mapIndexed { index, value ->
                val subMatrix = getSumMatrix(rowIndex to index)

                val string =
                    subMatrix.map { if (it == null) fillElement else map[it.first][it.second] }.joinToString("")
                string.map { if (it == '#') 1 else 0 }.joinToString("").toInt(2).let { betterizer[it] }
            }.joinToString("")
        }.toMutableList()
    }

    println(map.sumOf { it.sumOf { if (it == '#') 1L else 0 } })
}