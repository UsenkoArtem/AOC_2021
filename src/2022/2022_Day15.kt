package `2022`

import readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


data class Sensor(val pos: Pair<Int, Int>, val beacon: Pair<Int, Int>)

fun IntRange.intersects(other: IntRange): Boolean {
    return kotlin.math.max(this.first, other.first) <= kotlin.math.min(this.last, other.last)
}

fun IntRange.intersection(other: IntRange): IntRange {
    return kotlin.math.max(this.first, other.first)..kotlin.math.min(this.last, other.last)
}

class RangeSet {
    var ranges = mutableSetOf<IntRange>()

    fun addRange(range: IntRange) {
        val intersectingRanges = ranges.filter { it.intersects((range.first - 1)..(range.last + 1)) }

        val toMerge = intersectingRanges.toMutableSet()
        toMerge.add(range)
        ranges.removeAll(intersectingRanges)
        val newRange = (toMerge.minOf { it.first }..toMerge.maxOf { it.last })
        ranges.add(newRange)
    }

    fun intersection(other: RangeSet): RangeSet {
        val newSet = RangeSet()
        ranges.forEach { range ->
            other.ranges.forEach { otherRange ->
                if (range.intersects(otherRange)) {
                    newSet.addRange(range.intersection(otherRange))
                }
            }
        }
        return newSet
    }

    override fun toString(): String {
        return ranges.joinToString(", ") { "${it.first} to ${it.last}" }
    }
}

fun main() {

    fun part1(lines: List<Sensor>, yLine: Int = 10): String {

        val noBeacons = RangeSet()
        lines.forEach { (line, beacon) ->
            val (sX, sY) = line
            val (bX, bY) = beacon

            val distanceSB = abs(sX - bX) + abs(sY - bY)

            val distanceToYLine = abs(sY - yLine)

            if (distanceToYLine <= distanceSB) {
                val maxDist = distanceSB - distanceToYLine
                val maxX = sX + maxDist
                val minX = sX - maxDist
                val xRange = minX..maxX
                if (bY == yLine && bX in xRange) {
                    if (minX != bX)
                        noBeacons.addRange(minX until bX)
                    if (maxX != bX)
                        noBeacons.addRange((bX + 1)..maxX)
                } else noBeacons.addRange(xRange)
            }


        }

        return noBeacons.ranges.sumOf { it.last - it.first + 1 }.toString()
    }

    fun part2(lines: List<Sensor>, maxL: Int = 0, maxR: Int = 20): String {

        (2933730..maxR).forEach { yLine ->

            var noBeacons = RangeSet()
            lines.forEach { (line, beacon) ->
                val (sX, sY) = line
                val (bX, bY) = beacon
                val distanceSB = abs(sX - bX) + abs(sY - bY)
                val distanceToYLine = abs(sY - yLine)
                if (distanceToYLine <= distanceSB) {
                    val maxDist = distanceSB - distanceToYLine
                    val maxX = sX + maxDist
                    val minX = sX - maxDist
                    val xRange = max(maxL, minX)..min(maxR, maxX)
                    noBeacons.addRange(xRange)
                }
            }
            noBeacons = noBeacons.intersection(RangeSet().apply { addRange(maxL..maxR) })

            if (noBeacons.ranges.size > 1) {
                val x = noBeacons.ranges.minByOrNull { it.first }!!.last + 1
                val frequency = x.toLong() * 4000000L + yLine.toLong()
                println("($x, $yLine -> $frequency")
                return frequency.toString()
            }


        }
        return ""
    }

    val inputLine = readInput("Day15", "2022")

    val sensors = inputLine.map {
        val x = it.substringAfter("x=").substringBefore(",").trim().toInt()
        val y = it.substringAfter("y=").substringBefore(":").trim().toInt()
        val bx = it.substringAfterLast("x=").substringBefore(",").trim().toInt()
        val by = it.substringAfterLast("y=").toInt()
        Sensor(x to y, bx to by)
    }

    println(part1(sensors, yLine = 2000000))
    println(part2(sensors, maxR = 4000000))

}
