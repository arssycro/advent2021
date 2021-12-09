package com.arssycro.advent2021.day09

import com.arssycro.advent2021.readList

fun main() {
    Day09a().process()
}

class Day09a {
    fun process() {
        val inputs = readList("/day09.txt")
        val grid = Array(inputs.size) { IntArray(inputs[0].length) }
        for (i in inputs.indices) {
            grid[i] = inputs[i].map { it.digitToInt() }.toIntArray()
        }

        val lowPoints = mutableListOf<Int>()
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                val values = valueAndAdjacentValues(grid, x, y)
                val groupedValues = values.groupBy { it }
                if (groupedValues[grid[x][y]]?.size == 1 && grid[x][y] == values.minOrNull()) {
                    lowPoints.add(grid[x][y])
                }
            }
        }
        println(lowPoints.sumOf { it + 1 })
    }
}