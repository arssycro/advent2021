package com.arssycro.advent2021.day01

import com.arssycro.advent2021.readList

fun main(args: Array<String>){
    Day01b().process()
}

class Day01b {
    fun process(){
        val measurements = readList("/day01.txt").map { it.toInt() }
        var increaseCount = 0
        for (i in 4 .. measurements.size){
            val previous3 = measurements.subList(i-4, i-1).sum()
            val current3 = measurements.subList(i-3, i).sum()
            if (current3 > previous3){
                increaseCount++
            }
        }

        println(increaseCount)
    }
}