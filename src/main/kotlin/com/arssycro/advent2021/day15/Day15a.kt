package com.arssycro.advent2021.day15

import com.arssycro.advent2021.readList

fun main() {
    Day15a().process()
}

class Day15a {
    fun process() {
        val inputs = readList("/day15.txt")
        val graph = makeGraph(inputs)
        val output = graph.dijkstra(Pair(0, 0))
        println(output)
        val weight = graph.weight(output, Pair(0, 0), Pair(inputs.lastIndex, inputs[0].lastIndex))
        println(weight)
    }
}