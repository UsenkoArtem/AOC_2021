private fun SolvePart2(intput: List<String>): Long = intput.sumOf { solve2(it) }
private fun SolvePart1(intput: List<String>): Long = intput.sumOf { solve1(it) }

fun solve1(intput: String): Long {
    val (patterns, output) = intput.split("|").map { it.trim() }
        .map { it.split(" ").map { it.toCharArray().sortedArray().joinToString("") } }


    return PatternLength.values().sumOf { patternLength ->
        val stringPattern = patterns.firstOrNull { it.length == patternLength.digital }
        if (stringPattern == null)
            0 else
            output.sumOf { if (it == stringPattern) 1 else 0L }
    }
}

fun solve2(intput: String): Long {
    val (patterns, output) = intput.split("|").map { it.trim() }
        .map { it.split(" ").map { it.toCharArray().sortedArray().joinToString("") } }

    var groupChars = patterns.flatMap { it.map { it } }.sorted().groupBy { it }.values
    val positionToChar = CharArray(7)

    val one = patterns.find { it.length == 2 }!!
    val seven = patterns.find { it.length == 3 }!!
    val four = patterns.find { it.length == 4 }!!
    val digitalToThirdLine = groupChars.first { it.size == 9 }.first()

    positionToChar[2] = digitalToThirdLine
    positionToChar[1] = one.first { it != positionToChar[2] }
    positionToChar[0] = seven.first { it != positionToChar[2] && it != positionToChar[1] }
    positionToChar[5] = groupChars.first { it.size == Line.Six.includeCount }.first()
    positionToChar[6] =
        four.first { it != positionToChar[2] && it != positionToChar[1] && it != positionToChar[5] }

    groupChars = groupChars.filter { !positionToChar.contains(it.first()) }

    Line.values().forEachIndexed { index, line ->
        if (!positionToChar[index].isLetter()) {
            val find = groupChars.find { it.size == line.includeCount }
            positionToChar[index] = find!!.first()
            groupChars = groupChars.filter { it.first() != find.first() }
        }
    }

    val s = Digit.values().map {
        it.lineNumbers.map { positionToChar[it - 1] }.sorted().joinToString("")
    }
    return output.map { d ->
        s.indexOf(d)
    }.joinToString("").toLong()
}

enum class PatternLength(val digital: Int) {
    Four(4),
    Eight(7),
    One(2),
    Seven(3)
}

enum class Digit(val lineNumbers: IntArray) {
    Zero(intArrayOf(1, 2, 3, 4, 5, 6)),
    One(intArrayOf(2, 3)),
    Two(intArrayOf(1, 2, 4, 5, 7)),
    Three(intArrayOf(1, 2, 3, 4, 7)),
    Four(intArrayOf(2, 3, 6, 7)),
    Five(intArrayOf(1, 3, 4, 6, 7)),
    Six(intArrayOf(1, 3, 4, 5, 6, 7)),
    Seven(intArrayOf(1, 2, 3)),
    Eight(intArrayOf(1, 2, 3, 4, 5, 6, 7)),
    Nine(intArrayOf(1, 2, 3, 4, 6, 7)),
}

enum class Line(val includeCount: Int) {
    One(8),
    Two(8),
    Three(9),
    Four(7),
    Five(4),
    Six(6),
    Seven(7),
}


fun main() {
    println(SolvePart1(readInput("Day8")))
    println(SolvePart2(readInput("Day8")))
}