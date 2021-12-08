package com.arssycro.advent2021.day08

import com.arssycro.advent2021.readList

fun main() {
    Day08a().process()
}

class Day08a {
    fun process() {
        val inputs = readList("/day08.txt").map { it.split("|").map { s -> s.trim() } }
            .map { Pair(it[0].split(" "), it[1].split(" ")) }
        val outputSegments = inputs.flatMap { it.second }.groupBy { it.length }

        val segments = listOf(SEGMENTS_1, SEGMENTS_4, SEGMENTS_7, SEGMENTS_8)
        var count = 0
        for (segment in segments) {
            count += outputSegments[segment]?.size ?: 0
        }
        println(count)
    }
}