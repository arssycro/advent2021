package com.arssycro.advent2021.day22

import java.math.BigInteger
import kotlin.math.max
import kotlin.math.min

data class Rectangle(
    val xRange: IntRange,
    val yRange: IntRange,
    val zRange: IntRange,
    val status: Boolean,
    val exceptions: MutableList<Rectangle> = mutableListOf()
) {
    fun volume(): BigInteger = totalVolume() - exceptions.sumOf { it.volume() }

    fun totalVolume(): BigInteger = BigInteger.valueOf(
        xRange.distance().toLong()
    ) * BigInteger.valueOf(yRange.distance().toLong()) * BigInteger.valueOf(zRange.distance().toLong())

    fun contains(other: Rectangle) =
        xRange.contains(other.xRange) && yRange.contains(other.yRange) && zRange.contains(other.zRange)

    fun overlaps(other: Rectangle) =
        xRange.overlaps(other.xRange) && yRange.overlaps(other.yRange) && zRange.overlaps(other.zRange)

    fun ranges() = "$xRange, $yRange, $zRange"
}

fun IntRange.distance() = last - first + 1
fun IntRange.contains(other: IntRange) = (first <= other.first) && (last >= other.last)
fun IntRange.overlaps(other: IntRange) = (other.last >= first) && (other.first <= last)

fun getRectangles(
    inputs: List<String>,
    xLimit: IntRange? = null,
    yLimit: IntRange? = null,
    zLimit: IntRange? = null
): List<Rectangle> {
    val rectangles = mutableListOf<Rectangle>()
    for (input in inputs) {
        val split = input.split(" ", ",", "=", "..")
        val bool = split[0] == "on"

        val xLow = split[2].toInt()
        val xHigh = split[3].toInt()
        val (xMin, xMax) =
            if (xLimit == null) {
                Pair(xLow, xHigh)
            } else {
                if (xHigh < xLimit.first || xLow > xLimit.last) {
                    continue
                }
                Pair(max(xLow, xLimit.first), min(xHigh, xLimit.last))
            }

        val yLow = split[5].toInt()
        val yHigh = split[6].toInt()
        val (yMin, yMax) =
            if (yLimit == null) {
                Pair(yLow, yHigh)
            } else {
                if (yHigh < yLimit.first || yLow > yLimit.last) {
                    continue
                }
                Pair(max(yLow, yLimit.first), min(yHigh, yLimit.last))
            }

        val zLow = split[8].toInt()
        val zHigh = split[9].toInt()
        val (zMin, zMax) =
            if (zLimit == null) {
                Pair(zLow, zHigh)
            } else {
                if (zHigh < zLimit.first || zLow > zLimit.last) {
                    continue
                }
                Pair(max(zLow, zLimit.first), min(zHigh, zLimit.last))
            }

        val rectangle = Rectangle(xMin..xMax, yMin..yMax, zMin..zMax, bool)
        rectangles.add(rectangle)

    }
    return rectangles
}

fun getTotalArea(rectangles: List<Rectangle>): BigInteger {
    val activeRectangles = mutableListOf<Rectangle>()
    for (rectangle in rectangles) {
        val toRemove = mutableListOf<Rectangle>()
        for (active in activeRectangles) {
            if (rectangle.contains(active)) {
                toRemove.add(active)
            } else if (active.contains(rectangle)) {
                addException(active, rectangle)
            } else if (rectangle.overlaps(active)) {
                addException(active, getOverlap(active, rectangle))
            }
        }

        activeRectangles.removeAll(toRemove)
        if (rectangle.status) {
            activeRectangles.add(rectangle)
        }
    }

    return activeRectangles.sumOf { it.volume() }
}

fun addException(rectangle: Rectangle, newException: Rectangle) {
    val toRemove = mutableListOf<Rectangle>()
    for (exception in rectangle.exceptions) {
        if (exception == newException) return

        if (exception.contains(newException)) {
            addException(exception, newException)
        } else if (newException.contains(exception)) {
            toRemove.add(exception)
        } else if (newException.overlaps(exception)) {
            val overlap = getOverlap(newException, exception)
            addException(exception, overlap)
        }
    }
    rectangle.exceptions.removeAll(toRemove)
    rectangle.exceptions.add(newException)
}

fun getOverlap(rectangle1: Rectangle, rectangle2: Rectangle, status: Boolean = rectangle2.status): Rectangle {
    val overlap =
        Rectangle(
            max(rectangle1.xRange.first, rectangle2.xRange.first)..min(rectangle1.xRange.last, rectangle2.xRange.last),
            max(rectangle1.yRange.first, rectangle2.yRange.first)..min(rectangle1.yRange.last, rectangle2.yRange.last),
            max(rectangle1.zRange.first, rectangle2.zRange.first)..min(rectangle1.zRange.last, rectangle2.zRange.last),
            status
        )
    return overlap
}

fun makeGrid(x: IntRange, y: IntRange, z: IntRange): Array<Array<BooleanArray>> {
    val xSize = x.last - x.first + 1
    val ySize = y.last - y.first + 1
    val zSize = z.last - z.first + 1
    return Array(xSize) { Array(ySize) { BooleanArray(zSize) } }
}

fun processInstructions(
    grid: Array<Array<BooleanArray>>,
    xRange: IntRange,
    yRange: IntRange,
    zRange: IntRange,
    instructions: List<String>
) {
    val xShift = 0 - xRange.first
    val yShift = 0 - yRange.first
    val zShift = 0 - zRange.first

    for (instruction in instructions) {
        val split = instruction.split(" ")
        val bool = split[0] == "on"

        val ranges = split[1].split(",")
        val xs = ranges[0].split("=", "..")
        val ys = ranges[1].split("=", "..")
        val zs = ranges[2].split("=", "..")
        for (x in xs[1].toInt() + xShift..xs[2].toInt() + xShift) {
            if (x < 0 || x > grid.size - 1) {
                continue
            }
            for (y in ys[1].toInt() + yShift..ys[2].toInt() + yShift) {
                if (y < 0 || y > grid[x].size - 1) {
                    continue
                }
                for (z in zs[1].toInt() + zShift..zs[2].toInt() + zShift) {
                    if (z < 0 || z > grid[x][y].size - 1) {
                        continue
                    }
                    grid[x][y][z] = bool
                }
            }
        }
    }
}