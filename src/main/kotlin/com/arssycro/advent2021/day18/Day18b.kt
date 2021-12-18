package com.arssycro.advent2021.day18

import com.arssycro.advent2021.readList
import kotlin.math.max

fun main() {
    Day18b().process()
}

class Day18b {
    fun process() {
        val inputs = readList("/day18.txt")
        var max = Int.MIN_VALUE
        for (i in inputs.indices) {
            for (j in inputs.indices) {
                if (i == j) continue

                var string1 = processString(addInputs(inputs[i], inputs[j]))
                val total1 = calculate(string1.substring(1)).first
                max = max(max, total1)

                var string2 = processString(addInputs(inputs[j], inputs[i]))
                val total2 = calculate(string2.substring(1)).first
                max = max(max, total2)
            }
        }
        
        println(max)
    }
}