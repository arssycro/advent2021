package com.arssycro.advent2021.day20

import com.arssycro.advent2021.readList

fun main() {
    Day20b().process()
}

class Day20b {
    fun process() {
        val inputs = readList("/day20.txt")
        val algorithm = inputs[0]
        val image = inputs.subList(2, inputs.size)
        var grid = makeGrid(image)
        for (i in 1..50) {
            grid = enhance(grid, algorithm, i)
        }
        println(grid.sumOf {
            it.count { b -> b }
        })
    }
}