package com.arssycro.advent2021.day04

import com.arssycro.advent2021.readList

fun main() {
    Day04a().process()
}

class Day04a {
    fun process() {
        val inputLines = readList("/day04.txt")
        val numbers = inputLines[0].split(",").map { it.toInt() }
        val boards = makeBoards(inputLines)
        for (number in numbers) {
            markBoards(boards, number)
            val winningBoard = findWinners(boards)
            if (winningBoard.isNotEmpty()) {
                val boardValue = winningBoard[0].sumOf { it.filter { i -> i >= 0 }.sum() }
                println(boardValue * number)
                return
            }
        }
    }
}