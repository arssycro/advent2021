package com.arssycro.advent2021.day05

import com.arssycro.advent2021.readList

fun main() {
    Day05a().process()
}

class Day05a {
    fun process() {
        val inputLines = readList("/day05.txt")
        val inputs = inputLines.map { it.split(" -> ") }.map {
            Pair(
                it[0].split(",").map { i -> i.toInt() }.toPair(),
                it[1].split(",").map { i -> i.toInt() }.toPair()
            )
        }.filter { it.first.first == it.second.first || it.first.second == it.second.second }
        val board = makeBoard(inputs)
        for (input in inputs){
            markBoard(board, input)
        }
        val count = board.flatMap { it.asList() }.count { it > 1 }
        println(count)
    }
}