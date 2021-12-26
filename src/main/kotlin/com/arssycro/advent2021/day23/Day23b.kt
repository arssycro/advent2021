package com.arssycro.advent2021.day23

import com.arssycro.advent2021.readList

fun main() {
    Day23b().process()
}

class Day23b {
    fun process() {
        val inputs = readList("/day23b.txt")
        val grid = makeGrid(inputs)
        println(playGameRecursive(grid, Int.MAX_VALUE, listOf(), mutableMapOf()))
    }
}