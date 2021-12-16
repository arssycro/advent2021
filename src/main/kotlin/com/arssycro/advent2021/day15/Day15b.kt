package com.arssycro.advent2021.day15

import com.arssycro.advent2021.readList

fun main() {
    Day15b().process()
}

class Day15b {
    fun process() {
        val inputs = augmentInputs(readList("/day15.txt"), 5)
        val graph = makeGraph(inputs)
        val output = graph.dijkstra(Pair(0, 0))
        val weight = graph.weight(output, Pair(0, 0), Pair(inputs.lastIndex, inputs[0].lastIndex))
        println(weight)
    }

    fun augmentInputs(inputs: List<String>, times: Int): List<String> {
        val newInputs = mutableListOf<String>()

        for (i in 0 until times) {
            val currentInputs = inputs.map { it.shifted(i * 1) }
            newInputs.addAll(augmentHorizontal(currentInputs, times))
        }
        return newInputs
    }

    fun augmentHorizontal(inputs: List<String>, times: Int): List<String> {
        val newInputs = mutableListOf<String>()
        for (input in inputs) {
            val builder = StringBuilder(input.length * times)
            for (i in 0 until times) {
                builder.append(input.shifted(i * 1))
            }
            newInputs.add(builder.toString())
        }
        return newInputs
    }
}

fun String.shifted(shift: Int) = toCharArray().map { ((it.digitToInt() - 1 + shift) % 9) + 1 }
    .joinToString("")