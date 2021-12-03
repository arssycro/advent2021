package com.arssycro.advent2021.day02

import com.arssycro.advent2021.readList

fun main(args: Array<String>){
    Day02a().process()
}

class Day02a {
    fun process(){
        val directions = readList("/day02.txt")
        var horizontal = 0
        var depth = 0
        for (direction in directions){
            val split = direction.split(" ")
            val amount = split[1].toInt()
            when (split[0]){
                "forward" -> horizontal += amount
                "down" -> depth += amount
                "up" -> depth -= amount
            }
        }
        println(horizontal * depth)
    }
}