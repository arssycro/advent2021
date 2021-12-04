package com.arssycro.advent2021.day04

import com.arssycro.advent2021.readList

fun main() {
    Day04b().process()
}

class Day04b {
    fun process() {
        val inputLines = readList("/day04.txt")
        val numbers = inputLines[0].split(",").map { it.toInt() }
        var boards = makeBoards(inputLines)
        var winningBoard: Array<IntArray>? = null
        var winningNumber = 0
        for (number in numbers) {
            markBoards(boards, number)
            val winners = findWinners(boards)
            if (winners.isNotEmpty()) {
                winningNumber = number
                winningBoard = winners[0]
                boards.removeAll(winners)
            }
        }

        val boardValue = winningBoard?.sumOf { it.filter { i -> i >= 0 }.sum() } ?: 0
        println(boardValue * winningNumber)
    }
}
