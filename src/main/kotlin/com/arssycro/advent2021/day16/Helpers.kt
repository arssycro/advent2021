package com.arssycro.advent2021.day16

import java.math.BigInteger

val binaryMap = mapOf(
    '0' to "0000",
    '1' to "0001",
    '2' to "0010",
    '3' to "0011",
    '4' to "0100",
    '5' to "0101",
    '6' to "0110",
    '7' to "0111",
    '8' to "1000",
    '9' to "1001",
    'A' to "1010",
    'B' to "1011",
    'C' to "1100",
    'D' to "1101",
    'E' to "1110",
    'F' to "1111"
)

val versions = mutableListOf<Int>()

fun processMessage(message: String, current: Int): Pair<BigInteger, Int> {
    var index = current
    var value = BigInteger.ZERO
    while (index < message.length - 6) {
        val version = message.substring(index, index + 3).fromBinary().toInt()
        versions.add(version)
        index += 3

        val type = message.substring(index, index + 3).fromBinary().toInt()
        index += 3

        when (type) {
            4 -> {
                return getLiteral(message, index)
            }
            else -> {
                return getPackets(message, type, index)
            }
        }
    }

    return Pair(value, index)
}

fun getLiteral(message: String, current: Int): Pair<BigInteger, Int> {
    var index = current
    val builder = StringBuilder()
    var complete = false
    while (!complete && index < message.length) {
        val currentBit = message.substring(index + 1, index + 5)
        when (message[index]) {
            '0' -> {
                builder.append(currentBit)
                complete = true
            }
            '1' -> builder.append(currentBit)
        }
        index += 5
    }
    return Pair(builder.toString().fromBinary(), index)
}

fun getPackets(message: String, type: Int, current: Int): Pair<BigInteger, Int> {
    var index = current
    val lengthType = message[index]
    index++
    val literals = mutableListOf<BigInteger>()
    when (lengthType) {
        '0' -> {
            val length = message.substring(index, index + 15).fromBinary().toInt()
            index += 15
            var remainingLength = length
            while (remainingLength > 0) {
                val response = processMessage(message, index)
                remainingLength -= response.second - index
                index = response.second
                literals.add(response.first)
            }
        }
        '1' -> {
            val subPackets = message.substring(index, index + 11).fromBinary().toInt()
            index += 11
            for (i in 1..subPackets) {
                val response = processMessage(message, index)
                index = response.second
                literals.add(response.first)
            }
        }
        else -> TODO()
    }

    var value = when (type) {
        0 -> literals.sumOf { it }
        1 -> literals.reduce { acc, bigInteger -> acc * bigInteger }
        2 -> literals.minOrNull()!!
        3 -> literals.maxOrNull()!!
        5 -> if (literals[0] > literals[1]) BigInteger.ONE else BigInteger.ZERO
        6 -> if (literals[0] < literals[1]) BigInteger.ONE else BigInteger.ZERO
        7 -> if (literals[0] == literals[1]) BigInteger.ONE else BigInteger.ZERO
        else -> TODO()
    }

    return Pair(value, index)
}

fun String.fromBinary() = BigInteger(this, 2)