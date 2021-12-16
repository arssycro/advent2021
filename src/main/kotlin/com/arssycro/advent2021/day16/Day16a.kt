package com.arssycro.advent2021.day16

import com.arssycro.advent2021.readList

fun main() {
    Day16a().process()
}

class Day16a {
    fun process() {
        val inputs = readList("/day16.txt").map { it.map { c -> binaryMap[c] }.joinToString("") }
        println(inputs)
        val response = processMessage(inputs[0], 0)
        println(versions.sum())
    }
}