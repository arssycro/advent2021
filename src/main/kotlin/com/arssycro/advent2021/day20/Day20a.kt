package com.arssycro.advent2021.day20

import com.arssycro.advent2021.print
import com.arssycro.advent2021.readList

fun main() {
    Day20a().process()
}

class Day20a {
    fun process() {
        val inputs = readList("/day20.txt")
        val algorithm = inputs[0]
        val image = inputs.subList(2, inputs.size)
        var grid = makeGrid(image)
        grid.print()
        println()
        for (i in 1..2) {
            grid = enhance(grid, algorithm, i)
            grid.print()
            println()
        }
        println(grid.sumOf {
            it.count { b -> b }
        })
    }
}