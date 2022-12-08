package `2022`

import readInput
import kotlin.math.min

sealed class FsNode() {
    data class File(val name: String, val size: Long) : FsNode()
    data class Dir(val name: String, val children: MutableList<FsNode>, val parent: Dir?) : FsNode()
}

fun main() {
    fun part1(input: List<String>): String {
        var curDir = FsNode.Dir("/", mutableListOf(), null)
        val fs = hashMapOf<String, FsNode>()
        var index = 1

        while (index < input.size) {
            val command = input[index++]
            if (command.trim() == "$ ls") {
                while (index < input.size) {
                    val line = input[index]
                    if (line.startsWith("$"))
                        break
                    index++
                    if (line.split(" ")[0] == "dir")
                        curDir.children.add(FsNode.Dir(line.split(" ")[1], mutableListOf(), curDir))
                    else {
                        val size = line.split(" ")[0]
                        val fileName = line.split(" ")[1]
                        curDir.children.add(FsNode.File(fileName, size.toLong()))
                    }
                }
            }
            if (command.startsWith("$ cd")) {
                val nameDirToMove = command.split(" ")[2]
                curDir = if (nameDirToMove == "..")
                    if (curDir.parent == null) curDir else curDir.parent!!
                else
                    curDir.children.find { it is FsNode.Dir && it.name == nameDirToMove } as FsNode.Dir
            }
        }

        while (curDir.parent != null)
            curDir = curDir.parent!!


        fun calculateDirSize(dir: FsNode.Dir, onSizeEvent: (Long) -> Unit): Long {
            var size = 0L
            dir.children.forEach {
                if (it is FsNode.File)
                    size += it.size
                else if (it is FsNode.Dir)
                    size += calculateDirSize(it, onSizeEvent)
            }

            onSizeEvent(size)
            return size
        }

        var firstPart = 0L

        val allMemory = 70_000_000
        val needMemory = 30_000_000


       val usedMemory =  calculateDirSize(curDir) {
            //  println(it)
            if (it <= 100_000L)
                firstPart += it
        }

        val needToDelete = needMemory - (allMemory - usedMemory)

        var secondPart = Long.MAX_VALUE

        calculateDirSize(curDir) {
            if (it >= needToDelete)
                secondPart = min(it, secondPart)
        }



        return "$firstPart   $secondPart"
    }


    fun part2(input: List<String>): String {

        return "0"
    }

    val input = readInput("Day07", "2022")
    println(part1(input))
    println(part2(input))
}
