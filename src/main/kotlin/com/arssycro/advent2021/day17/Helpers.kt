package com.arssycro.advent2021.day17

import kotlin.math.max

//The probe's x position increases by its x velocity.
//The probe's y position increases by its y velocity.
//Due to drag, the probe's x velocity changes by 1 toward the value 0; that is, it decreases by 1 if it is greater than 0, increases by 1 if it is less than 0, or does not change if it is already 0.
//Due to gravity, the probe's y velocity decreases by 1.

data class Velocity(
    val x: Int,
    val y: Int
)

fun moveProbe(velocity: Velocity, xRange: IntRange, yRange: IntRange): Int? {
    var xVel = velocity.x
    var yVel = velocity.y

    var x = 0
    var y = 0
    var maxY = Int.MIN_VALUE
    while (true) {
        x += xVel
        y += yVel
        maxY = max(maxY, y)

        if (xRange.contains(x) && yRange.contains(y)) {
            return maxY
        }
        if (xVel == 0 && (x > xRange.last || x < xRange.first)) {
            return null
        }
        if (y < yRange.first) {
            return null
        }


        if (xVel > 0) {
            xVel--
        } else if (xVel < 0) {
            xVel++
        }
        yVel--
    }
}