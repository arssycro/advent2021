package com.arssycro.advent2021.day12

//start-A
fun createGraph(inputs: List<String>): Graph {
    val regex = Regex("""(\w+)-(\w+)""")
    val vertices = mutableSetOf<String>()
    val edges = mutableMapOf<String, List<String>>()
    for (input in inputs) {
        val (cave1, cave2) = regex.matchEntire(input)!!.destructured
        vertices.add(cave1)
        vertices.add(cave2)

        edges[cave1] = (edges[cave1] ?: mutableListOf()) + cave2
        edges[cave2] = (edges[cave2] ?: mutableListOf()) + cave1
    }

    return Graph(vertices, edges)
}

fun String.isLowercase() = lowercase() == this

data class Graph(
    val vertices: Set<String>,
    val edges: Map<String, List<String>>
)