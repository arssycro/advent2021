package com.arssycro.advent2021.day12

import com.arssycro.advent2021.readList

fun main() {
    Day12b().process()
}

class Day12b {
    fun process() {
        val inputLines = readList("/day12.txt")
        val graph = createGraph(inputLines)
        println(graph)

        val permutations = getPermutations(graph, "start", mutableSetOf("start"), null)
        println(permutations.size)
    }

    fun getPermutations(
        graph: Graph,
        vertex: String,
        visited: MutableSet<String>,
        visitedSecond: String?
    ): List<List<String>> {
        if (vertex.isLowercase()) {
            visited.add(vertex)
        }

        if (vertex == "end") {
            return listOf(listOf(vertex))
        }

        val remainingEdges =
            graph.edges[vertex]!!.filter { (visitedSecond == null || !visited.contains(it)) && it != "start" }
        if (remainingEdges.isEmpty()) {
            return listOf()
        }

        val permutations = mutableListOf<List<String>>()
        var newVisitedSecond = visitedSecond
        for (edge in remainingEdges) {
            if (edge.isLowercase()) {
                if (visited.contains(edge)) {
                    newVisitedSecond = edge
                }
                visited.add(edge)
            }
            val newPermutations = getPermutations(graph, edge, visited, newVisitedSecond)
            newPermutations.forEach { permutations.add(it + edge) }
            if (edge.isLowercase()) {
                if (newVisitedSecond == visitedSecond) {
                    visited.remove(edge)
                } else {
                    newVisitedSecond = visitedSecond
                }
            }
        }

        return permutations
    }

}