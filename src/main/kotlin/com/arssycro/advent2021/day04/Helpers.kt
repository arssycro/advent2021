package com.arssycro.advent2021.day04

fun String.toIntArray() = this.trim().split(Regex("\\s+")).map { it.toInt() }.toIntArray()

fun makeBoards(inputLines: List<String>): MutableList<Array<IntArray>> {
    val boards = mutableListOf<Array<IntArray>>()
    for (i in 2..inputLines.size step 6) {
        val row1 = inputLines[i].toIntArray()
        val row2 = inputLines[i + 1].toIntArray()
        val row3 = inputLines[i + 2].toIntArray()
        val row4 = inputLines[i + 3].toIntArray()
        val row5 = inputLines[i + 4].toIntArray()
        boards.add(arrayOf(row1, row2, row3, row4, row5))
    }
    return boards
}

fun markBoards(boards: List<Array<IntArray>>, number: Int) {
    for (board in boards) {
        for (i in 0..4) {
            for (j in 0..4) {
                if (board[i][j] == number) {
                    board[i][j] = -1
                }
            }
        }
    }
}

fun findWinners(boards: List<Array<IntArray>>): List<Array<IntArray>> {
    val winners = mutableListOf<Array<IntArray>>()
    for (board in boards) {
        for (i in 0..4) {
            if (board[i].sum() == -5) {
                winners.add(board)
            }

            if (board[0][i] + board[1][i] + board[2][i] + board[3][i] + board[4][i] == -5) {
                winners.add(board)
            }
        }
    }

    return winners
}