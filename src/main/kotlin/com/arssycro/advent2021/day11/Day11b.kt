package com.arssycro.advent2021.day11

import com.arssycro.advent2021.readList

fun main() {
    Day11b().process()
}

class Day11b {
    fun process() {
        val inputLines = readList("/day11.txt")
        val grid = makeGrid(inputLines)

        var i = 1
        while (true) {
            incrementGrid(grid)
            val count = countAndReset(grid)
            if (count == 100) {
                println(i)
                return
            }
            i++
        }
    }
}