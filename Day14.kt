fun main() {

    fun simulateOneDay(formula: String, instruction: HashMap<String, String>): String {
        val pairs = formula.drop(1).mapIndexed { index, _ -> formula[index] + formula[index + 1].toString() }
        return pairs.mapIndexed { index, pair ->
            if (instruction.contains(pair)) {
                if (index == pairs.size - 1)
                    pair.first() + instruction[pair]!! + pair.last()
                else pair.first() + instruction[pair]!!
            } else pair
        }.joinToString("")
    }

    fun part1(formula: String, instruction: HashMap<String, String>): Int {
        var finalFormula = formula
        repeat(10) {
            finalFormula = simulateOneDay(finalFormula, instruction)
        }
        val formulaCharsIncludeSize = finalFormula.groupBy { it }.map { it.value.size }
        return formulaCharsIncludeSize.maxOf { it } - formulaCharsIncludeSize.minOf { it }
    }

    fun simulateOneDayByPairs(
        pairs: MutableMap<String, Long>,
        instruction: HashMap<String, String>
    ): MutableMap<String, Long> {
        val resultPair = mutableMapOf<String, Long>()
        pairs.forEach { pair ->
            if (instruction.contains(pair.key)) {
                val firstPair = pair.key.first() + instruction[pair.key]!!
                val secondPair = instruction[pair.key]!! + pair.key.last()

                resultPair.putIfAbsent(firstPair, 0)
                resultPair.putIfAbsent(secondPair, 0)
                resultPair[firstPair] = resultPair[firstPair]!! + pair.value
                resultPair[secondPair] = resultPair[secondPair]!! + pair.value
            }
        }
        return resultPair
    }

    fun part2(formula: String, instruction: HashMap<String, String>): Long {
        var finalPairs = mutableMapOf<String, Long>()
        formula.drop(1).mapIndexed { index, _ -> formula[index] + formula[index + 1].toString() }.forEach {
            finalPairs.putIfAbsent(it, 0)
            finalPairs[it] = finalPairs[it]!! + 1
        }
        repeat(40) {
            finalPairs = simulateOneDayByPairs(finalPairs, instruction)
        }
        val resultHashMap = mutableMapOf<Char, Long>()
        finalPairs.forEach { (pair, count) ->
            pair.drop(1).forEach {
                resultHashMap.putIfAbsent(it, 0)
                resultHashMap[it] = resultHashMap[it]!! + count
            }
        }
        resultHashMap[formula.first()] = resultHashMap[formula.first()]!! + 1
        return resultHashMap.values.maxOf { it } - resultHashMap.values.minOf { it }
    }


    val input = readInput("Day14")
    val formula = input.first()
    val instruction = hashMapOf<String, String>()
    input.drop(2).map { it.split(" -> ") }.map { instruction[it.first()] = it.last() }
    println(part1(formula, instruction))
    println(part2(formula, instruction))
}
