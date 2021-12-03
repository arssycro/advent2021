package com.arssycro.advent2021.day03

import com.arssycro.advent2021.readList
import java.lang.IllegalStateException

fun main(args: Array<String>){
    Day03b().process()
}

class Day03b {
    fun process(){
        val numbers = readList("/day03.txt")

        val oxygen = getOxygen(numbers)
        val co2 = getCO2(numbers)

        val oxygenNumber = Integer.parseInt(oxygen, 2)
        val co2Number = Integer.parseInt(co2, 2)
        println(oxygenNumber * co2Number)
    }

    fun getOxygen(numbers: List<String>): String{
        var values = numbers
        for (i in 0..11){
            val count0 = values.map { it[i] }.count { it == '0' }
            val match = if (count0 > (values.size/2)) '0' else '1'

            values = values.filter { it[i] == match }
            if (values.size == 1){
                return values[0]
            }
        }

        throw IllegalStateException()
    }



    fun getCO2(numbers: List<String>): String{
        var values = numbers
        for (i in 0..11){
            val count0 = values.map { it[i] }.count { it == '0' }
            val match = if (count0 <= (values.size/2)) '0' else '1'

            values = values.filter { it[i] == match }
            if (values.size == 1){
                return values[0]
            }
        }

        throw IllegalStateException()
    }
}