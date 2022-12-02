package `2022`

import readInput
import kotlin.math.max

fun main() {

    fun part1(input: List<String>): Long {
        var maxCalories = 0L
        var caloriesPerElf = 0L
        input.forEach {
            if (it.isEmpty()) {
                maxCalories = max(maxCalories, caloriesPerElf)
                caloriesPerElf = 0
            } else caloriesPerElf += it.toLong()
        }
        return if (maxCalories > caloriesPerElf) maxCalories else caloriesPerElf
    }

    fun part2(input: List<String>): Long {
        val maxCalories = mutableListOf<Long>()
        var caloriesPerElf = 0L
        input.forEach {
            if (it.isEmpty()) {
                maxCalories.add(caloriesPerElf)
                caloriesPerElf = 0
            } else caloriesPerElf += it.toLong()
        }

        maxCalories.add(caloriesPerElf)
        maxCalories.sortDescending()
        return maxCalories.take(3).sum()
    }


    val input = readInput("Day01", "2022")
    println(part1(input))
    println(part2(input))
}
