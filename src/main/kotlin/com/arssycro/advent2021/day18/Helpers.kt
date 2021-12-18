package com.arssycro.advent2021.day18

import kotlin.math.ceil
import kotlin.math.floor

fun addInputs(input1: String, input2: String) = "[$input1,$input2]"

fun calculate(input: String): Pair<Int, Int> {
    var i = 0
    var total = 0

    var first = true
    val digits = StringBuilder()
    while (i < input.length) {
        if (input[i] == '[') {
            val calc = calculate(input.substring(i + 1))
            i += calc.second
            total += calc.first * (if (first) 3 else 2)
        } else if (input[i] == ']') {
            if (digits.isNotEmpty()) {
                total += 2 * digits.toString().toInt()
            }
            return Pair(total, i + 1)
        } else if (input[i] == ',') {
            first = false
            if (digits.isNotEmpty()) {
                total += 3 * digits.toString().toInt()
                digits.clear()
            }
        } else {
            digits.append(input[i])
        }

        i++
    }

    return Pair(total, i)
}

fun processString(input: String): String {
    var list = input.toList()

    outer@ while (true) {
        var levels = 0
        for (i in list.indices) {
            val str = list[i]
            if (str == "[") {
                levels++
                continue
            } else if (str == "]") {
                levels--
                continue
            }

            val int = str.toIntOrNull()
            if (int != null) {
                if (levels > 4) {
                    list = processExplode(list, int, i)
                    continue@outer
                }
            }
        }
        for (i in list.indices) {
            val str = list[i]
            val int = str.toIntOrNull()
            if (int != null) {
                if (int >= 10) {
                    list = processSplit(list, int, i)
                    continue@outer
                }
            }
        }
        break
    }

    return list.joinToString("")
}

fun processExplode(list: List<String>, int: Int, index: Int): List<String> {
    var newList = list.toMutableList()

    val previousNumberIndex = list.subList(0, index).indexOfLast { it.toIntOrNull() != null }
    if (previousNumberIndex != -1) {
        newList[previousNumberIndex] = (list[previousNumberIndex].toInt() + int).toString()
    }

    val secondIntIndex = list.subList(index + 1, list.size).indexOfFirst { it.toIntOrNull() != null }
    val secondInt = list[index + 1 + secondIntIndex].toInt()

    val remainder = list.subList(index + 1 + secondIntIndex + 1, list.size)
    val intAfterIndex = remainder.indexOfFirst { it.toIntOrNull() != null }
    if (intAfterIndex != -1) {
        val normalizedIndex = index + 1 + secondIntIndex + 1 + intAfterIndex
        newList[normalizedIndex] =
            (newList[normalizedIndex].toInt() + secondInt).toString()
    }

    val pairStart = index - 1
    val pairEnd = remainder.indexOfFirst { it == "]" } + index + 1 + secondIntIndex + 1

    return newList.subList(0, pairStart) + "0" + newList.subList(pairEnd + 1, newList.size)
}

fun processSplit(list: List<String>, int: Int, index: Int): List<String> {
    val first = floor(int / 2.0).toInt().toString()
    val second = ceil(int / 2.0).toInt().toString()
    val newPair = listOf("[", first, ",", second, "]")
    return list.subList(0, index) + newPair + list.subList(index + 1, list.size)
}

fun String.toList(): List<String> {
    val list = mutableListOf<String>()
    val digits = StringBuilder()
    for (char in this) {
        if (char.isDigit()) {
            digits.append(char)
        } else {
            if (digits.isNotEmpty()) {
                list.add(digits.toString())
                digits.clear()
            }

            list.add("$char")
        }
    }
    return list
}