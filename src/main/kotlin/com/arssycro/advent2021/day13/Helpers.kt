package com.arssycro.advent2021.day13

fun makeGrid(inputs: List<List<Int>>): Array<BooleanArray> {
    val maxY = inputs.maxOf { it[0] } + 1
    val maxX = inputs.maxOf { it[1] } + 1
    val grid = Array(maxX) { BooleanArray(maxY) }
    for (input in inputs) {
        grid[input[1]][input[0]] = true
    }

    return grid
}

fun Array<BooleanArray>.foldX(value: Int): Array<BooleanArray> {
    val newGrid = Array(this.size) { BooleanArray(value) }
    for (x in this.indices) {
        for (y in 0 until value) {
            newGrid[x][y] = this[x][y] || this[x][value + (value - y)]
        }
    }
    return newGrid
}

fun Array<BooleanArray>.foldY(value: Int): Array<BooleanArray> {
    val newGrid = Array(value) { BooleanArray(this[0].size) }
    for (x in 0 until value) {
        for (y in this[0].indices) {
            newGrid[x][y] = this[x][y] || this[value + (value - x)][y]
        }
    }
    return newGrid
}

fun Array<BooleanArray>.print() {
    forEach { println(it.joinToString("") { b -> if (b) "#" else "." }) }
}