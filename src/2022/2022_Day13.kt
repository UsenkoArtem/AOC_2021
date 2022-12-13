package `2022`

import readInputText
import java.util.*


sealed class Element {
    class ENumber(val number: Int) : Element()
    class EList(val list: List<Element>) : Element()
}

class StringParser(private val string: String) {

    private var index = 0

    fun get() = string[index]
    fun getAndMove() = string[index++]

}

fun parseLine(parse: StringParser): Element.EList {
    require(parse.get() == '[')
    parse.getAndMove()

    val elements = mutableListOf<Element>()

    while (parse.get() != ']') {
        if (parse.get() == ',')
            parse.getAndMove()
        else
            if (parse.get() == '[')
                elements += parseLine(parse)
            else {
                var number = ""
                while (parse.get().isDigit())
                    number += parse.getAndMove()

                if (parse.get() == ',')
                    parse.getAndMove()

                elements += Element.ENumber(number.toInt())
            }
    }
    parse.getAndMove()

    return Element.EList(elements)
}

fun compare(left: Element, right: Element): Int {

    if (left is Element.ENumber && right is Element.ENumber)
        return left.number.compareTo(right.number)

    if (left is Element.ENumber && right is Element.EList)
        return compare(Element.EList(listOf(left)), right)

    if (left is Element.EList && right is Element.ENumber)
        return compare(left, Element.EList(listOf(right)))

    if (left is Element.EList && right is Element.EList) {
        var index = 0

        while (index < left.list.size && index < right.list.size) {
            val leftElement = left.list[index]
            val rightElement = right.list[index]

            index++

            val compareResult = compare(leftElement, rightElement)

            if (compareResult != 0) return compareResult
        }

        if (left.list.size > right.list.size)
            return 1

        if (left.list.size < right.list.size)
            return -1

        return 0
    }
    error("")
}

fun main() {

    fun part1(list: List<Pair<Element.EList, Element.EList>>): String {
        return list.mapIndexed { index, pair ->
            if (compare(pair.first, pair.second) <= 0)
                (index + 1)
            else 0
        }.sum().toString()
    }

    val input = readInputText("Day13", "2022")

    val pairs = input.split("\n\n").map { s ->
        val split = s.split("\n")
        val left = split[0]
        val right = split[1]

        parseLine(StringParser(left)) to parseLine(StringParser(right))

    }

    println(part1(pairs))
    val firstPair = Element.EList(listOf(Element.ENumber(2)))
    val secondPair = Element.EList(listOf(Element.ENumber(6)))
    val part2 = pairs.flatMap { listOf(it.first, it.second) }
        .plus(firstPair)
        .plus(secondPair)


    val sortedList = part2.sortedWith(::compare)

    println(
        (
                (sortedList.indexOf(firstPair) + 1)
                        * (sortedList.indexOf(secondPair) + 1)
                )
            .toString()
    )

}
