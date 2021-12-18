package com.arssycro.advent2021.day18

import com.arssycro.advent2021.readList

fun main() {
    Day18a().process()
}

class Day18a {
    fun process() {
        val inputs = readList("/day18.txt")
        var additiveString = addInputs(inputs[0], inputs[1])
        additiveString = processString(additiveString)
        for (input in inputs.subList(2, inputs.size)) {
            additiveString = processString(addInputs(additiveString, input))
        }

        println(additiveString)
        println(calculate(additiveString.substring(1)).first)
    }
}