package `2022`

import readInput


//A, X - Rock
//B, Y -Paper
//C, Z - Scissors


fun main() {

    fun part1(input: List<String>): Long {
        val pointForRound = hashMapOf<Char, HashMap<Char, Long>>().also {
            it['A'] = hashMapOf('X' to 3, 'Y' to 6, 'Z' to 0)
            it['B'] = hashMapOf('X' to 0, 'Y' to 3, 'Z' to 6)
            it['C'] = hashMapOf('X' to 6, 'Y' to 0, 'Z' to 3)
        }

        val pointForSelectedShape = hashMapOf<Char, Long>('X' to 1, 'Y' to 2, 'Z' to 3)

        return input.sumOf {
            val player1 = it.split(" ")[0].first()
            val player2 = it.split(" ")[1].first()
            pointForRound[player1]!![player2]!! + pointForSelectedShape[player2]!!
        }
    }

    fun part2(input: List<String>): Long {

        val pointForRound = hashMapOf<Char, HashMap<Char, Long>>().also {
            it['X'] = hashMapOf('A' to 3, 'B' to 1, 'C' to 2)
            it['Y'] = hashMapOf('A' to 1, 'B' to 2, 'C' to 3)
            it['Z'] = hashMapOf('A' to 2, 'B' to 3, 'C' to 1)
        }

        return input.sumOf {
            val player1 = it.split(" ")[0].first()
            val player2 = it.split(" ")[1].first()
            when (player2) {
                'X' -> 0L
                'Y' -> 3L
                'Z' -> 6L
                else -> 0
            } + pointForRound[player2]!![player1]!!

        }
    }


    val input = readInput("Day02", "2022")
    println(part1(input))
    println(part2(input))
}
