package com.arssycro.advent2021.day16

import com.arssycro.advent2021.readList

fun main() {
    Day16b().process()
}

class Day16b {
    fun process() {
        val inputs = readList("/day16.txt").map { it.map { c -> binaryMap[c] }.joinToString("") }
        println(inputs)
        val response = processMessage(inputs[0], 0)
        println(response.first)
    }
}