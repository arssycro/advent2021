package com.arssycro.advent2021.day01

import com.arssycro.advent2021.readList

fun main(args: Array<String>){
    Day01a().process()
}

class Day01a {
    fun process(){
        val measurements = readList("/day01.txt").map { it.toInt() }
        var increaseCount = 0
        for (i in 1 until measurements.size){
            if (measurements[i] > measurements[i-1]){
                increaseCount++
            }
        }

        println(increaseCount)
    }
}