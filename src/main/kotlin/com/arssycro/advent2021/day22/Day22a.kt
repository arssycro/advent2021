package com.arssycro.advent2021.day22

import com.arssycro.advent2021.readList

fun main() {
    Day22a().process()
}

class Day22a {
    fun process() {
        val inputs = readList("/day22.txt")
        val xRange = -50..50
        val yRange = -50..50
        val zRange = -50..50

        val rectangles = getRectangles(inputs, xRange, yRange, zRange)
        val totalArea = getTotalArea(rectangles)
        println(totalArea)
    }
}