package com.arssycro.advent2021.day23

import com.arssycro.advent2021.readList

fun main() {
    Day23a().process()
}

class Day23a {
    fun process() {
        val inputs = readList("/day23.txt")
        val grid = makeGrid(inputs)
        println(playGameRecursive(grid, Int.MAX_VALUE, listOf(), mutableMapOf()))
    }
}