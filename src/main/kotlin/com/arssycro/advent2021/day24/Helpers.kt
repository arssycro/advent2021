package com.arssycro.advent2021.day24

fun getInstructions(inputs: List<String>): List<List<String>> {
    var instructions = mutableListOf<List<String>>()
    var currentList = mutableListOf<String>()
    for (input in inputs) {
        if (input.startsWith("inp") && currentList.isNotEmpty()) {
            instructions.add(currentList)
            currentList = mutableListOf()
        }
        currentList.add(input)
    }
    instructions.add(currentList)
    return instructions
}

fun processInstructions(
    inputs: List<String>,
    starters: Map<Int, String>,
    chooseString: (String, String) -> String
): Map<Int, String> {
    val output = mutableMapOf<Int, String>()
    for (starter in starters) {
        for (i in 1..9) {
            val values = mapOf('w' to 0, 'x' to 0, 'y' to 0, 'z' to starter.key)
            val newResult = processInstructions(inputs, values, i)['z']!!.toInt()
            if (output.containsKey(newResult)) {
                output[newResult] = chooseString(output[newResult]!!, "${starter.value}$i")
            } else {
                output[newResult] = "${starter.value}$i"
            }
        }
    }
    return output
}

fun processInstructions(inputs: List<String>, values: Map<Char, Int>, inputValue: Int): Map<Char, Int> {
    val newValues = values.toMutableMap()
    for (input in inputs) {
        val split = input.split(" ")
        when (split[0]) {
            "inp" -> newValues['w'] = inputValue
            "add" -> perform(split[1], split[2], newValues) { a, b -> a + b }
            "mul" -> perform(split[1], split[2], newValues) { a, b -> a * b }
            "div" -> perform(split[1], split[2], newValues) { a, b -> a / b }
            "mod" -> perform(split[1], split[2], newValues) { a, b -> a % b }
            "eql" -> perform(split[1], split[2], newValues) { a, b -> if (a == b) 1 else 0 }
        }
    }
    return newValues
}

fun perform(value1: String, value2: String, values: MutableMap<Char, Int>, operation: (Int, Int) -> Int) {
    val a = values[value1[0]]!!
    val b = value2.toIntOrNull() ?: values[value2[0]]!!
    values[value1[0]] = operation(a, b)
}