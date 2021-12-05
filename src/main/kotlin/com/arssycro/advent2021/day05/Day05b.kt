package com.arssycro.advent2021.day05

import com.arssycro.advent2021.readList
import kotlin.math.abs

fun main() {
    Day05b().process()
}

class Day05b {
    fun process() {
        val inputLines = readList("/day05.txt")
        val inputs = inputLines.map { it.split(" -> ") }.map {
            Pair(
                it[0].split(",").map { i -> i.toInt() }.toPair(),
                it[1].split(",").map { i -> i.toInt() }.toPair()
            )
        }
        val board = makeBoard(inputs)
        for (input in inputs){
            markBoard(board, input)
        }
        val count = board.flatMap { it.asList() }.count { it > 1 }
        println(count)
    }
}