package com.arssycro.advent2021.day13

import com.arssycro.advent2021.readList

fun main() {
    Day13b().process()
}

class Day13b {
    fun process() {
        val inputs = readList("/day13.txt")
        val blankIndex = inputs.indexOf("")
        val gridInputs = inputs.subList(0, blankIndex).map { it.split(",").map { a -> a.toInt() } }
        val instructions = inputs.subList(blankIndex + 1, inputs.lastIndex + 1)

        var grid = makeGrid(gridInputs)

        val foldRegex = Regex("""fold along (x|y)=(\d+)""")
        for (instruction in instructions) {
            println(instruction)
            val (axis, value) = foldRegex.matchEntire(instruction)!!.destructured
            grid = when (axis) {
                "x" -> grid.foldX(value.toInt())
                "y" -> grid.foldY(value.toInt())
                else -> TODO()
            }
        }
        grid.print()
    }
}