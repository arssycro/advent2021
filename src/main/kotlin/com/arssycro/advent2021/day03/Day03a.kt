package com.arssycro.advent2021.day03

import com.arssycro.advent2021.readList

fun main(args: Array<String>){
    Day03a().process()
}

class Day03a {
    fun process(){
        val numbers = readList("/day03.txt")
        var gamma = ""
        var epsilon = ""
        for (i in 0..11) {
            val count0 = numbers.map { it[i] }.count { it == '0' }
            if (count0 > (numbers.size/2)) {
                gamma += "0"
                epsilon += "1"
            } else {
                gamma += "1"
                epsilon += "0"
            }
        }

        val gammaNumber = Integer.parseInt(gamma, 2)
        val epsilonNumber = Integer.parseInt(epsilon, 2)
        println(gammaNumber * epsilonNumber)
    }
}