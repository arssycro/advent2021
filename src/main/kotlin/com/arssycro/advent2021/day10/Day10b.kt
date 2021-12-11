package com.arssycro.advent2021.day10

import com.arssycro.advent2021.readList
import java.util.*

fun main() {
    Day10b().process()
}

class Day10b {
    val charPoints = mapOf(')' to 1, ']' to 2, '}' to 3, '>' to 4)

    fun process() {
        val inputLines = readList("/day10.txt")
        val errors = mutableListOf<Stack<Char>>()
        for (line in inputLines) {
            val opened = Stack<Char>()
            var corrupted = false
            for (char in line) {
                if (char.isOpen()) {
                    opened.add(char)
                    continue
                }

                if (char.isClose()) {
                    val last = opened.peek()
                    if (!last.closedBy(char)) {
                        corrupted = true
                        break
                    } else {
                        opened.pop()
                    }
                }
            }

            if (!corrupted) {
                opened.reverse()
                errors.add(opened)
            }
        }

        println(errors)

        val total =
            errors.map {
                it.map { i -> charPoints[chars[i]!!] ?: 0 }.map { i -> i.toLong() }.reduce { acc, c -> (acc * 5) + c }
            }.sorted()
        println(total)
        val middle = total.size / 2
        println(total[middle])
    }
}