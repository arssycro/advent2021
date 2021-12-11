package com.arssycro.advent2021.day10

import com.arssycro.advent2021.readList
import java.util.*

fun main() {
    Day10a().process()
}

class Day10a {
    val charPoints = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)

    fun process() {
        val inputLines = readList("/day10.txt")
        val errors = mutableListOf<Char>()
        for (line in inputLines) {
            val opened = Stack<Char>()
            for (char in line) {
                if (char.isOpen()) {
                    opened.add(char)
                    continue
                }

                if (char.isClose()) {
                    val last = opened.peek()
                    if (!last.closedBy(char)) {
                        errors.add(char)
                        break
                    } else {
                        opened.pop()
                    }
                }
            }
        }

        val total = errors.sumOf { charPoints[it] ?: 0 }
        println(total)
    }
}