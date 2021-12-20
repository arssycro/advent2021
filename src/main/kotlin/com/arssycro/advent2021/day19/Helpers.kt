package com.arssycro.advent2021.day19

import kotlin.math.pow
import kotlin.math.sqrt

data class Scanner(
    val id: Int,
    val beacons: Set<Point>,
    val distances: Map<Point, Map<Point, Double>>
)

data class Point(
    val x: Int,
    val y: Int,
    val z: Int
) {
    private val xDouble = x.toDouble()
    private val yDouble = y.toDouble()
    private val zDouble = z.toDouble()

    fun cartesian(other: Point): Double {
        return sqrt(
            (other.xDouble - xDouble).pow(2) + (other.yDouble - yDouble).pow(2) + (other.zDouble - zDouble).pow(2)
        )
    }

    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Point): Point {
        return Point(x - other.x, y - other.y, z - other.z)
    }
}

val scannerRegex = Regex("""--- scanner (\d+) ---""")

fun getScanners(inputs: List<String>): List<Scanner> {
    var id = 0
    var beacons = mutableSetOf<Point>()
    val scanners = mutableListOf<Scanner>()
    for (input in inputs) {
        if (input.isEmpty()) {
            scanners.add(createScanner(id, beacons))
            beacons = mutableSetOf()
            continue
        }

        if (input.startsWith("---")) {
            id = scannerRegex.matchEntire(input)!!.groupValues[1].toInt()
            continue
        }

        beacons.add(createPoint(input))
    }

    scanners.add(createScanner(id, beacons))
    return scanners
}

fun createScanner(id: Int, beacons: Set<Point>): Scanner {
    val distances = mutableMapOf<Point, MutableMap<Point, Double>>()
    for (point1 in beacons) {
        for (point2 in beacons) {
            if (point1 == point2) continue

            distances.computeIfAbsent(point1) { mutableMapOf() }[point2] = point1.cartesian(point2)
        }
    }
    return Scanner(id, beacons, distances)
}

fun createPoint(input: String): Point {
    val split = input.split(",").map { it.toInt() }
    return Point(split[0], split[1], split[2])
}

fun findOverlappingBeacons(scanner1: Scanner, scanner2: Scanner): List<Pair<Point, Point>> {
    for (distances1 in scanner1.distances.entries) {
        for (distances2 in scanner2.distances.entries) {
            val matched = distances1.value.entries.filter { distances2.value.containsValue(it.value) }
            if (matched.size >= 11) {
                val overlaps = mutableListOf<Pair<Point, Point>>()
                overlaps.add(Pair(distances1.key, distances2.key))
                for (match in matched) {
                    val other = distances2.value.entries.find { it.value == match.value } ?: TODO()
                    overlaps.add(Pair(match.key, other.key))
                }
                return overlaps
            }
        }
    }
    return listOf()
}

data class Transformation(
    val xVal: String,
    val yVal: String,
    val zVal: String
) {
    fun transform(point: Point): Point {
        return Point(valueFor(xVal, point), valueFor(yVal, point), valueFor(zVal, point))
    }

    fun valueFor(value: String, point: Point): Int {
        val multiplier = if (value[0] == '-') -1 else 1
        return multiplier * when (value[value.lastIndex]) {
            'x' -> point.x
            'y' -> point.y
            'z' -> point.z
            else -> TODO()
        }
    }
}

val transformations = listOf(
    Transformation("x", "y", "z"),
    Transformation("x", "y", "-z"),
    Transformation("x", "-y", "z"),
    Transformation("x", "-y", "-z"),
    Transformation("-x", "y", "z"),
    Transformation("-x", "y", "-z"),
    Transformation("-x", "-y", "z"),
    Transformation("-x", "-y", "-z"),
    Transformation("x", "z", "y"),
    Transformation("x", "z", "-y"),
    Transformation("x", "-z", "y"),
    Transformation("x", "-z", "-y"),
    Transformation("-x", "z", "y"),
    Transformation("-x", "z", "-y"),
    Transformation("-x", "-z", "y"),
    Transformation("-x", "-z", "-y"),
    Transformation("y", "x", "z"),
    Transformation("y", "x", "-z"),
    Transformation("y", "-x", "z"),
    Transformation("y", "-x", "-z"),
    Transformation("-y", "x", "z"),
    Transformation("-y", "x", "-z"),
    Transformation("-y", "-x", "z"),
    Transformation("-y", "-x", "-z"),
    Transformation("y", "z", "x"),
    Transformation("y", "z", "-x"),
    Transformation("y", "-z", "x"),
    Transformation("y", "-z", "-x"),
    Transformation("-y", "z", "x"),
    Transformation("-y", "z", "-x"),
    Transformation("-y", "-z", "x"),
    Transformation("-y", "-z", "-x"),
    Transformation("z", "x", "y"),
    Transformation("z", "x", "-y"),
    Transformation("z", "-x", "y"),
    Transformation("z", "-x", "-y"),
    Transformation("-z", "x", "y"),
    Transformation("-z", "x", "-y"),
    Transformation("-z", "-x", "y"),
    Transformation("-z", "-x", "-y"),
    Transformation("z", "y", "x"),
    Transformation("z", "y", "-x"),
    Transformation("z", "-y", "x"),
    Transformation("z", "-y", "-x"),
    Transformation("-z", "y", "x"),
    Transformation("-z", "y", "-x"),
    Transformation("-z", "-y", "x"),
    Transformation("-z", "-y", "-x"),
)

fun getScannerLocation(overlappingBeacons: List<Pair<Point, Point>>): Pair<Point, Transformation> {
    for (transformation in transformations) {
        var foundDiff: Point? = null
        for (overlap in overlappingBeacons) {
            val currentDiff = overlap.first - transformation.transform(overlap.second)
            if (foundDiff == null) {
                foundDiff = currentDiff
            } else if (foundDiff != currentDiff) {
                foundDiff = null
                break
            }
        }
        foundDiff?.let { return Pair(it, transformation) }
    }

    TODO()
}

fun mergeScanners(
    scanner1: Scanner,
    scanner2: Scanner,
    scanner2Location: Point,
    transformation: Transformation
): Scanner {
    val mergedBeacons = scanner1.beacons + scanner2.beacons.map { transformation.transform(it) + scanner2Location }
    return createScanner(scanner1.id, mergedBeacons)
}