package com.arssycro.advent2021.day14

import com.arssycro.advent2021.readList
import kotlin.math.ceil

fun main() {
    Day14b().process()
}

class Day14b {
    fun process() {
        val inputs = readList("/day14.txt")
        val template = inputs[0]
        val substitutions =
            inputs.subList(2, inputs.lastIndex + 1).map { it.split(" -> ") }.associate { it[0] to it[1] }

        var currentCounts = template.windowed(2).groupBy { it }.mapValues { it.value.size.toLong() }
        for (i in 1..40) {
            currentCounts = processSubstitutions(currentCounts, substitutions)
        }
        val countsOrdered =
            currentCounts.flatMap { listOf("${it.key[0]}" to it.value, "${it.key[1]}" to it.value) }
                .groupBy { it.first }
                .mapValues { ceil(it.value.sumOf { p -> p.second } / 2.0).toLong() }.values.sortedDescending()
        println(countsOrdered.first() - countsOrdered.last())
    }

    fun processSubstitutions(currentCounts: Map<String, Long>, substitutions: Map<String, String>): Map<String, Long> {
        val newCounts = mutableMapOf<String, Long>()
        for ((key, value) in currentCounts) {
            val substitution = substitutions[key]!!
            newCounts["${key[0]}$substitution"] = (newCounts["${key[0]}$substitution"] ?: 0L) + value
            newCounts["$substitution${key[1]}"] = (newCounts["$substitution${key[1]}"] ?: 0L) + value
        }
        return newCounts
    }
}