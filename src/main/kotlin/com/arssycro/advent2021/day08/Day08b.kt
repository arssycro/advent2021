package com.arssycro.advent2021.day08

import com.arssycro.advent2021.readList

fun main() {
    Day08b().process()
}

class Day08b {
    fun process() {
        val inputs = readList("/day08.txt").map { it.split("|").map { s -> s.trim() } }
            .map { Pair(it[0].split(" "), it[1].split(" ")) }
        var sum = 0
        for (input in inputs) {
            val charsByCount =
                input.first.flatMap { it.toCharArray().toList() }.groupBy { it }.values.groupBy { it.size }
                    .mapValues { it.value.map { a -> a[0] } }

            val segmentMappings = CharArray(7)
            // Solvable by count alone
            segmentMappings[1] = charsByCount[6]!![0]
            segmentMappings[4] = charsByCount[4]!![0]
            segmentMappings[5] = charsByCount[9]!![0]

            val segmentsByLength = (input.first + input.second).groupBy { it.length }

            val segments1 = segmentsByLength[SEGMENTS_1].firstCharSet()
            val segments4 = segmentsByLength[SEGMENTS_4].firstCharSet()
            val segments7 = segmentsByLength[SEGMENTS_7].firstCharSet()

            // Solvable by elimination
            segmentMappings[0] = (segments7 - segments1).first()
            segmentMappings[2] = (charsByCount[8]!! - segmentMappings[0]).first()

            val remaining = charsByCount[7]!!
            segmentMappings[6] = (remaining - segments4).first()
            segmentMappings[3] = (remaining - segmentMappings[6]).first()

            val numbers = getNumbers(input, segmentMappings)
            val output = input.second.map { numbers[it]!! }.joinToString("")
            sum += output.toInt()
        }
        println(sum)
    }

    fun getNumbers(splitInput: Pair<List<String>, List<String>>, segmentMappings: CharArray): Map<String, Int> {
        val mappings0 = getMappings(segmentMappings, 0, 1, 2, 4, 5, 6)
        val mappings2 = getMappings(segmentMappings, 0, 2, 3, 4, 6)
        val mappings3 = getMappings(segmentMappings, 0, 2, 3, 5, 6)
        val mappings5 = getMappings(segmentMappings, 0, 1, 3, 5, 6)
        val mappings6 = getMappings(segmentMappings, 0, 1, 3, 4, 5, 6)
        val mappings9 = getMappings(segmentMappings, 0, 1, 2, 3, 5, 6)

        val inputs = (splitInput.first + splitInput.second).toSet()
        val inputToNumber = mutableMapOf<String, Int>()
        for (input in inputs) {
            when (input.length) {
                SEGMENTS_1 -> {
                    inputToNumber[input] = 1
                    continue
                }
                SEGMENTS_4 -> {
                    inputToNumber[input] = 4
                    continue
                }
                SEGMENTS_7 -> {
                    inputToNumber[input] = 7
                    continue
                }
                SEGMENTS_8 -> {
                    inputToNumber[input] = 8
                    continue
                }
            }

            when (input.toCharArray().toSet()) {
                mappings0 -> inputToNumber[input] = 0
                mappings2 -> inputToNumber[input] = 2
                mappings3 -> inputToNumber[input] = 3
                mappings5 -> inputToNumber[input] = 5
                mappings6 -> inputToNumber[input] = 6
                mappings9 -> inputToNumber[input] = 9
            }
        }
        return inputToNumber
    }

    fun getMappings(segmentMappings: CharArray, vararg indices: Int): Set<Char> {
        val mapping = mutableSetOf<Char>()
        for (index in indices) {
            mapping.add(segmentMappings[index])
        }
        return mapping
    }
}

fun List<String>?.firstCharSet() = this!![0].toCharArray().toSet()