package com.arssycro.advent2021.day06

import com.arssycro.advent2021.readList

fun main() {
    Day06b().process()
}

class Day06b {
    fun process() {
        val inputLines = readList("/day06.txt")
        val intList = inputLines[0].split(",").map { it.toInt() }.toMutableList()

        var countMap = mutableMapOf<Int, Long>()
        for (i in intList.toSet()) {
            val count = getChildrenCount(i, 0, 256)
            countMap[i] = count
        }

        var count: Long = intList.size.toLong()
        for (i in intList) {
            count += countMap[i]!!
        }

        println(count)
    }
}