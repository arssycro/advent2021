package com.arssycro.advent2021.day09

import com.arssycro.advent2021.readList

fun main() {
    Day09b().process()
}

class Day09b {
    fun process() {
        val inputs = readList("/day09.txt")
        val grid = Array(inputs.size) { IntArray(inputs[0].length) }
        for (i in inputs.indices) {
            grid[i] = inputs[i].map { it.digitToInt() }.toIntArray()
        }

        val lowPoints = mutableListOf<Int>()
        val lowIndices = mutableListOf<Pair<Int, Int>>()
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                val values = valueAndAdjacentValues(grid, x, y)
                val groupedValues = values.groupBy { it }
                if (groupedValues[grid[x][y]]?.size == 1 && grid[x][y] == values.minOrNull()) {
                    lowPoints.add(grid[x][y])
                    lowIndices.add(Pair(x, y))
                }
            }
        }

        var basinSizes = mutableListOf<Int>()
        for (index in lowIndices) {
            basinSizes.add(basinCount(grid, index.first, index.second, mutableSetOf()))
        }
        println(basinSizes.sortedDescending().take(3).reduce { acc, i -> acc * i })
    }

    fun basinCount(grid: Array<IntArray>, x: Int, y: Int, visited: MutableSet<Pair<Int, Int>>): Int {
        if (!visited.add(Pair(x, y))) {
            return 0
        }

        if (grid[x][y] == 9) {
            return 0
        }

        var basinCount = 0
        if (x - 1 >= 0) {
            basinCount += basinCount(grid, x - 1, y, visited)
        }
        if (x + 1 < grid.size) {
            basinCount += basinCount(grid, x + 1, y, visited)
        }
        if (y - 1 >= 0) {
            basinCount += basinCount(grid, x, y - 1, visited)
        }
        if (y + 1 < grid[0].size) {
            basinCount += basinCount(grid, x, y + 1, visited)
        }
        return basinCount + 1
    }
}