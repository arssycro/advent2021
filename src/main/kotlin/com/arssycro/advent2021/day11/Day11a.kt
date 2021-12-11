package com.arssycro.advent2021.day11

import com.arssycro.advent2021.readList

fun main() {
    Day11a().process()
}

class Day11a {
    fun process() {
        val inputLines = readList("/day11.txt")
        val grid = makeGrid(inputLines)

        var count = 0
        for (i in 1..100) {
            incrementGrid(grid)
            count += countAndReset(grid)
        }
        println(count)
    }
}