package com.arssycro.advent2021.day20

fun makeGrid(inputs: List<String>): Array<BooleanArray> {
    val grid = Array(inputs.size) { BooleanArray(inputs[0].length) }
    for (i in inputs.indices) {
        grid[i] = inputs[i].map { it == '#' }.toBooleanArray()
    }
    return grid
}

fun enhance(grid: Array<BooleanArray>, algorithm: String, run: Int): Array<BooleanArray> {
    val tempGrid = Array(grid.size + 4) { BooleanArray(grid[0].size + 4) }
    for (x in tempGrid.indices) {
        for (y in tempGrid[0].indices) {
            if (x <= 1 || y <= 1 || x >= tempGrid.lastIndex - 1 || y >= tempGrid[0].lastIndex - 1) {
                tempGrid[x][y] =
                    if (run == 1) false else if (run % 2 == 0) algorithm[0] == '#' else if (algorithm[0] == '#') algorithm.last() == '#' else false
            } else {
                tempGrid[x][y] = grid[x - 2][y - 2]
            }
        }
    }

    val infinite = if (run % 2 == 0 && algorithm[0] == '#') algorithm.last() == '#' else algorithm[0] == '#'
    val newGrid = Array(tempGrid.size) { BooleanArray(tempGrid[0].size) }
    for (x in newGrid.indices) {
        for (y in newGrid[0].indices) {
            if (x == 0 || y == 0 || x == newGrid.lastIndex || y == newGrid[0].lastIndex) {
                newGrid[x][y] = infinite
            } else {
                newGrid[x][y] = getAlgorithmValue(tempGrid, x, y, algorithm)
            }
        }
    }
    return newGrid
}

fun getAlgorithmValue(grid: Array<BooleanArray>, x: Int, y: Int, algorithm: String): Boolean {
    val builder = StringBuilder()
    for (row in x - 1..x + 1) {
        for (column in y - 1..y + 1) {
            builder.append(if (grid[row][column]) '1' else '0')
        }
    }

    val algoIndex = Integer.parseInt(builder.toString(), 2)
//    println("$x,$y = $builder ($algoIndex) = ${algorithm[algoIndex]}")
    return algorithm[algoIndex] == '#'
}