package com.arssycro.advent2021.day25

import com.arssycro.advent2021.readList

fun main() {
    Day25a().process()
}

class Day25a {
    fun process() {
        val inputs = readList("/day25.txt")
        val grid = makeGrid(inputs)

        var i = 1
        while (true) {
            val moves = move(grid)
            if (moves == 0) {
                println(i)
                return
            }

            i++
        }
    }

    fun makeGrid(inputs: List<String>): Array<CharArray> {
        val grid = Array(inputs.size) { CharArray(inputs[0].length) }
        for (i in inputs.indices) {
            grid[i] = inputs[i].toCharArray()
        }
        return grid
    }

    fun move(grid: Array<CharArray>): Int {
        var moves = 0
        moves += move(grid, '>')
        moves += move(grid, 'v')
        return moves
    }

    fun move(grid: Array<CharArray>, check: Char): Int {
        val movables = movables(grid, check)
        for (movable in movables) {
            if (check == '>') {
                if (movable.second == grid[0].lastIndex) {
                    grid[movable.first][0] = check
                } else {
                    grid[movable.first][movable.second + 1] = check
                }
            } else {
                if (movable.first == grid.lastIndex) {
                    grid[0][movable.second] = check
                } else {
                    grid[movable.first + 1][movable.second] = check
                }
            }

            grid[movable.first][movable.second] = '.'
        }
        return movables.size
    }

    fun movables(grid: Array<CharArray>, check: Char): List<Pair<Int, Int>> {
        val movable = mutableListOf<Pair<Int, Int>>()
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] != check) continue

                if (check == '>') {
                    if (j == grid[i].lastIndex) {
                        if (grid[i][0] == '.') {
                            movable.add(Pair(i, j))
                        }
                    } else {
                        if (grid[i][j + 1] == '.') {
                            movable.add(Pair(i, j))
                        }
                    }
                } else {
                    if (i == grid.lastIndex) {
                        if (grid[0][j] == '.') {
                            movable.add(Pair(i, j))
                        }
                    } else {
                        if (grid[i + 1][j] == '.') {
                            movable.add(Pair(i, j))
                        }
                    }
                }
            }
        }
        return movable
    }
}