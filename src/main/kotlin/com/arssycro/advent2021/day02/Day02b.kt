package com.arssycro.advent2021.day02

import com.arssycro.advent2021.readList

fun main(args: Array<String>){
    Day02b().process()
}

class Day02b {
    fun process(){
        val directions = readList("/day02.txt")
        var horizontal = 0
        var depth = 0
        var aim = 0
        for (direction in directions){
            val split = direction.split(" ")
            val amount = split[1].toInt()
            when (split[0]){
                "forward" -> {
                    horizontal += amount
                    depth += (aim * amount)
                }
                "down" -> aim += amount
                "up" -> aim -= amount
            }
        }
        println(horizontal * depth)
    }
}