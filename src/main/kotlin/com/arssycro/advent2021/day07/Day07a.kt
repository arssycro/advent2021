package com.arssycro.advent2021.day07

import com.arssycro.advent2021.readList
import kotlin.math.abs

fun main() {
    Day07a().process()
}

class Day07a {
    fun process() {
        val inputLines = readList("/day07.txt")
        val ints = inputLines[0].split(",").map { it.toInt() }
        var currentMin = Int.MAX_VALUE
        for (i in 0..ints.maxOf { it }) {
            val fuel = ints.sumOf { abs(it - i) }
            if (fuel < currentMin) {
                currentMin = fuel
            }
        }
        println(currentMin)
    }
}