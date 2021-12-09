package com.arssycro.advent2021.day09

fun valueAndAdjacentValues(grid: Array<IntArray>, x: Int, y: Int): List<Int> {
    val values = mutableListOf<Int>()
    values.add(grid[x][y])
    if (x + 1 < grid.size) {
        values.add(grid[x + 1][y])
    }
    if (x - 1 >= 0) {
        values.add(grid[x - 1][y])
    }
    if (y + 1 < grid[x].size) {
        values.add(grid[x][y + 1])
    }
    if (y - 1 >= 0) {
        values.add(grid[x][y - 1])
    }
    return values
}