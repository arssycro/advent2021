package com.arssycro.advent2021.day17

fun main() {
    Day17b().process()
}

class Day17b {
    fun process() {
        val xRange = 230..283
        val yRange = -107..-57
        var maxes = mutableListOf<Int>()
        for (x in -1000..1000) {
            for (y in -1000..1000) {
                val max = moveProbe(Velocity(x, y), xRange, yRange)
                max?.let {
                    maxes.add(max)
                }
            }
        }
        println(maxes.size)
    }
}