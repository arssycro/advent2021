package com.arssycro.advent2021.day14

import com.arssycro.advent2021.readList

fun main() {
    Day14a().process()
}

class Day14a {
    fun process() {
        val inputs = readList("/day14.txt")
        val template = inputs[0]
        val substitutions =
            inputs.subList(2, inputs.lastIndex + 1).map { it.split(" -> ") }.associate { it[0] to it[1] }

        var current = template
        for (i in 1..10) {
            current = processSubstitutions(current, substitutions)
        }

        val charByOccurrences = current.toCharArray().groupBy { it }.mapValues { it.value.size }
        val occurrences = charByOccurrences.values.sortedDescending()
        println(occurrences.first() - occurrences.last())
    }
    
    fun processSubstitutions(input: String, substitutions: Map<String, String>): String {
        return input.windowed(2)
            .joinToString("") { if (substitutions.containsKey(it)) "${it[0]}${substitutions[it]!!}" else "${it[0]}" } + input.last()
    }
}