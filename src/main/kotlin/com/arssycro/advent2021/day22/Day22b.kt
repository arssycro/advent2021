package com.arssycro.advent2021.day22

import com.arssycro.advent2021.readList

fun main() {
    Day22b().process()
}

class Day22b {
    fun process() {
        val inputs = readList("/day22.txt")
        val rectangles = getRectangles(inputs)
        val totalArea = getTotalArea(rectangles)
        println(totalArea)
    }
}