package com.arssycro.advent2021.day07

import com.arssycro.advent2021.readList
import kotlin.math.abs

fun main() {
    Day07b().process()
}

class Day07b {
    fun process() {
        val inputLines = readList("/day07.txt")
        val ints = inputLines[0].split(",").map { it.toInt() }
        var currentMin = Int.MAX_VALUE
        for (i in 0..ints.maxOf { it }) {
            val fuel = ints.sumOf { sumFactorial(abs(it - i)) }
            if (fuel < currentMin) {
                currentMin = fuel
            }
        }
        println(currentMin)
    }

    fun sumFactorial(x: Int): Int {
        return when (x) {
            0, 1 -> x
            else -> x + sumFactorial(x - 1)
        }
    }
}