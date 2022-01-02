package com.arssycro.advent2021.day24

import com.arssycro.advent2021.readList

fun main() {
    Day24b().process()
}

class Day24b {
    fun process() {
        val inputs = readList("/day24.txt")
        val instructions = getInstructions(inputs)
        var lastProcessed = mapOf(0 to "")
        for (i in instructions.indices) {
            println(i)
            val processed =
                processInstructions(instructions[i], lastProcessed) { a, b -> minOf(a, b) }
            val filtered = processed.entries.filter { it.key < 1000000000 }.associate { it.key to it.value }
            lastProcessed = filtered
            println(filtered.size)
        }

        val values13 = lastProcessed[0]
        println(values13)
    }
}