package com.arssycro.advent2021.day11

fun makeGrid(inputs: List<String>): Array<IntArray> {
    val grid = Array(inputs.size) { IntArray(inputs[0].length) }
    for (i in inputs.indices) {
        for (j in inputs[i].indices) {
            grid[i][j] = inputs[i][j].digitToInt()
        }
    }
    return grid
}

fun Array<IntArray>.print() {
    forEach { println(it.joinToString("")) }
}

fun incrementGrid(grid: Array<IntArray>) {
    val litUp = mutableSetOf<Pair<Int, Int>>()
    for (x in grid.indices) {
        for (y in grid[x].indices) {
            grid[x][y]++
            if (grid[x][y] > 9) {
                litUp.add(Pair(x, y))
            }
        }
    }

    for (pair in litUp) {
        incrementNeighbors(grid, pair.first, pair.second, (litUp - setOf(pair)).toMutableSet())
    }
}

fun countAndReset(grid: Array<IntArray>): Int {
    var count = 0
    for (x in grid.indices) {
        for (y in grid[x].indices) {
            if (grid[x][y] > 9) {
                count++
                grid[x][y] = 0
            }
        }
    }
    return count
}

fun incrementNeighbors(grid: Array<IntArray>, x: Int, y: Int, visited: MutableSet<Pair<Int, Int>>) {
    if (!visited.add(Pair(x, y))) {
        return
    }

    val xDec = x > 0
    val xInc = x < grid.size - 1
    val yDec = y > 0
    val yInc = y < grid.size - 1
    if (xDec) {
        if (grid[x - 1][y] == 9) {
            incrementNeighbors(grid, x - 1, y, visited)
        }
        grid[x - 1][y]++
        if (yDec) {
            if (grid[x - 1][y - 1] == 9) {
                incrementNeighbors(grid, x - 1, y - 1, visited)
            }
            grid[x - 1][y - 1]++
        }
        if (yInc) {
            if (grid[x - 1][y + 1] == 9) {
                incrementNeighbors(grid, x - 1, y + 1, visited)
            }
            grid[x - 1][y + 1]++
        }
    }
    if (xInc) {
        if (grid[x + 1][y] == 9) {
            incrementNeighbors(grid, x + 1, y, visited)
        }
        grid[x + 1][y]++
        if (yDec) {
            if (grid[x + 1][y - 1] == 9) {
                incrementNeighbors(grid, x + 1, y - 1, visited)
            }
            grid[x + 1][y - 1]++
        }
        if (yInc) {
            if (grid[x + 1][y + 1] == 9) {
                incrementNeighbors(grid, x + 1, y + 1, visited)
            }
            grid[x + 1][y + 1]++
        }
    }
    if (yDec) {
        if (grid[x][y - 1] == 9) {
            incrementNeighbors(grid, x, y - 1, visited)
        }
        grid[x][y - 1]++
    }
    if (yInc) {
        if (grid[x][y + 1] == 9) {
            incrementNeighbors(grid, x, y + 1, visited)
        }
        grid[x][y + 1]++
    }
}