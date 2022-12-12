package `2022`

import readInputText


fun main() {

    data class Monkey(
        val startingItems: List<Long>,
        val operation: String,
        val inspected: String,
        val div: Long,
        val onTrue: Int,
        val onFalse: Int
    )

    fun parse(str: String): Monkey {
        val startingItems =
            Regex("Starting items: ([\\d, ]+)").find(str)!!.groupValues[1].split(", ").map { it.toLong() }
        val (op, numberToUp) = Regex("Operation: new = old ([+*]) (old|\\d+)").find(str)!!.destructured
        val div = Regex("divisible by (\\d+)").find(str)!!.groupValues[1].toLong()
        val onTrue = Regex("If true: throw to monkey (\\d)").find(str)!!.groupValues[1].toInt()
        val onFalse = Regex("If false: throw to monkey (\\d)").find(str)!!.groupValues[1].toInt()

        return Monkey(startingItems, op, numberToUp, div, onTrue, onFalse)
    }

    fun part1(input: List<String>, rounds: Int, worryLevelChanger: (Long) -> Long): String {
        val monkeys = input.map { parse(it) }
        val items = Array<MutableList<Long>>(monkeys.size) { mutableListOf() }
        val inspects = Array(input.size) { 0 }

        monkeys.forEachIndexed { index, monkey ->
            items[index] = monkey.startingItems.toMutableList()
        }

        for (round in 0 until rounds) {
            monkeys.forEachIndexed { index, monkey ->

                items[index].forEach { item ->
                    val inspected = if (monkey.inspected == "old") item else monkey.inspected.toLong()
                    var newWorryLevel = when (monkey.operation) {
                        "+" -> item + inspected
                        "*" -> item * inspected
                        else -> error("")
                    }

                    inspects[index]++
                    newWorryLevel = worryLevelChanger(newWorryLevel)

                    if (newWorryLevel % monkey.div == 0L)
                        items[monkey.onTrue] += newWorryLevel
                    else
                        items[monkey.onFalse] += newWorryLevel
                }
                items[index].clear()

            }
        }

        return inspects.sorted().takeLast(2).map { it.toLong() }.fold(1L) { a, b -> a * b }.toString()
    }

    val input = readInputText("Day11", "2022").split("\n\n")
    val monkey = input.map { parse(it) }.map { it.div }.fold(1L) { acc, l -> acc * l }
    println(part1(input, 20) { it / 3 })
    println(part1(input, 10000) { it % monkey })
}
