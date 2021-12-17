package com.arssycro.advent2021.day17

import kotlin.math.max

fun main() {
    Day17a().process()
}

class Day17a {
    fun process() {
        val xRange = 230..283
        val yRange = -107..-57
        var maxY = Int.MIN_VALUE
        for (x in -1000..1000) {
            for (y in -1000..1000) {
                val max = moveProbe(Velocity(x, y), xRange, yRange)
                max?.let {
                    maxY = max(maxY, max)
                }
            }
        }
        println(maxY)
    }
}