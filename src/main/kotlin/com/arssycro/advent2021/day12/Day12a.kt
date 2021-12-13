package com.arssycro.advent2021.day12

import com.arssycro.advent2021.readList

fun main() {
    Day12a().process()
}

class Day12a {
    fun process() {
        val inputLines = readList("/day12.txt")
        val graph = createGraph(inputLines)
        println(graph)

        val permutations = getPermutations(graph, "start", mutableSetOf())
        println(permutations.size)
    }

    fun getPermutations(graph: Graph, vertex: String, visited: MutableSet<String>): List<List<String>> {
        if (vertex.isLowercase()) {
            visited.add(vertex)
        }

        if (vertex == "end") {
            return listOf(listOf(vertex))
        }

        val remainingEdges = graph.edges[vertex]!!.filter { !visited.contains(it) }
        if (remainingEdges.isEmpty()) {
            return listOf()
        }

        val permutations = mutableListOf<List<String>>()
        for (edge in remainingEdges) {
            if (edge.isLowercase()) {
                visited.add(edge)
            }
            getPermutations(graph, edge, visited).forEach { permutations.add(it + edge) }
            if (edge.isLowercase()) {
                visited.remove(edge)
            }
        }

        return permutations
    }

}