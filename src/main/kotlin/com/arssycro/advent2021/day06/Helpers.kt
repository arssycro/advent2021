package com.arssycro.advent2021.day06

fun getChildrenCount(elementStart: Int, day: Int, limit: Int): Long {
    var count: Long = 0
    val first = day + elementStart + 1
    if (first <= limit) {
        for (i in first..limit step 7) {
            count++
            count += getChildrenCount(8, i, limit)
        }
    }
    return count
}