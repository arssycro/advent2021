package com.arssycro.advent2021.day15

import java.util.*

data class Graph(
    val vertices: Set<Pair<Int, Int>>,
    val edges: Map<Pair<Int, Int>, Set<Pair<Int, Int>>>,
    val weights: Map<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int>
) {
    fun weight(
        pathTree: Map<Pair<Int, Int>, Pair<Int, Int>?>,
        start: Pair<Int, Int>,
        end: Pair<Int, Int>
    ): Int {
        if (pathTree[end] == null) return 0

        val nextPath = pathTree[end]!!
        return weights[Pair(nextPath, end)]!! + weight(pathTree, start, nextPath)
    }

    fun dijkstra(start: Pair<Int, Int>): Map<Pair<Int, Int>, Pair<Int, Int>?> {
        var priorityQueue =
            PriorityQueue((compareBy<Pair<Int, Int>> { it.first }))
        priorityQueue.add(start)

        val known = mutableSetOf<Pair<Int, Int>>()

        val delta = vertices.associateWith { Int.MAX_VALUE }.toMutableMap()
        delta[start] = 0

        val previous: MutableMap<Pair<Int, Int>, Pair<Int, Int>?> = vertices.associateWith { null }.toMutableMap()

        while (priorityQueue.isNotEmpty() && known != vertices) {
            val v = priorityQueue.remove()!!

            edges[v]?.let {
                it.forEach { neighbor ->
                    val newPath = delta.getValue(v) + weights.getValue(Pair(v, neighbor))

                    if (newPath < delta.getValue(neighbor)) {
                        delta[neighbor] = newPath
                        previous[neighbor] = v
                        priorityQueue.add(neighbor)
                    }
                }
            }

            println("Processed ${known.size} of ${vertices.size} vertices")
            known.add(v)
        }
        println("Processed ${known.size} of ${vertices.size} vertices")

        return previous.toMap()
    }
}

fun makeGraph(inputs: List<String>): Graph {
    val vertices = mutableSetOf<Pair<Int, Int>>()
    val edges = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
    val weights = mutableMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, Int>()
    for (i in inputs.indices) {
        for (j in inputs[i].indices) {
            val current = Pair(i, j)
            vertices.add(current)

            if (i > 0) {
                val newJ = Pair(i - 1, j)
                edges.computeIfAbsent(current) { mutableSetOf() }.add(newJ)
                weights[Pair(current, newJ)] = inputs[i - 1][j].digitToInt()
            }

            if (i < inputs.lastIndex) {
                val newJ = Pair(i + 1, j)
                edges.computeIfAbsent(current) { mutableSetOf() }.add(newJ)
                weights[Pair(current, newJ)] = inputs[i + 1][j].digitToInt()
            }

            if (j > 0) {
                val newJ = Pair(i, j - 1)
                edges.computeIfAbsent(current) { mutableSetOf() }.add(newJ)
                weights[Pair(current, newJ)] = inputs[i][j - 1].digitToInt()
            }

            if (j < inputs[i].lastIndex) {
                val newJ = Pair(i, j + 1)
                edges.computeIfAbsent(current) { mutableSetOf() }.add(newJ)
                weights[Pair(current, newJ)] = inputs[i][j + 1].digitToInt()
            }
        }
    }
    return Graph(vertices, edges, weights)
}

