package com.arssycro.advent2021.day05

import kotlin.math.max
import kotlin.math.min

fun <T> List<T>.toPair() = Pair(get(0), get(1))

fun makeBoard(inputs: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Array<IntArray> {
    val flatInputs = inputs.flatMap { listOf(it.first, it.second) }
    val maxX = flatInputs.maxOf { it.first }
    val maxY = flatInputs.maxOf { it.second }
    return Array(maxX + 1) { IntArray(maxY + 1) }
}

fun markBoard(board: Array<IntArray>, directions: Pair<Pair<Int, Int>, Pair<Int, Int>>) {
    val lowX = min(directions.first.first, directions.second.first)
    val highX = max(directions.first.first, directions.second.first)
    val lowY = min(directions.first.second, directions.second.second)
    val highY = max(directions.first.second, directions.second.second)

    if (lowX == highX) {
        for (y in lowY..highY) {
            board[lowX][y]++
        }
        return
    }

    if (lowY == highY) {
        for (x in lowX..highX) {
            board[x][lowY]++
        }
        return
    }

    val diff = highX - lowX
    val xMultiple = if (directions.first.first == lowX) 1 else -1
    val yMultiple = if (directions.first.second == lowY) 1 else -1

    for (i in 0..diff){
        board[directions.first.first+(i*xMultiple)][directions.first.second+(i*yMultiple)]++
    }
}