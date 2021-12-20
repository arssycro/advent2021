package com.arssycro.advent2021.day19

import com.arssycro.advent2021.readList
import kotlin.math.abs
import kotlin.math.max

fun main() {
    Day19b().process()
}

class Day19b {
    fun process() {
        val inputs = readList("/day19.txt")
        val scanners = getScanners(inputs)

        var primary = scanners[0]
        val toInspect = scanners.subList(1, scanners.size).toMutableList()
        val locations = mutableListOf(Point(0, 0, 0))
        while (toInspect.isNotEmpty()) {
            var merged = false
            for (scanner in toInspect) {
                val overlaps = findOverlappingBeacons(primary, scanner)
                if (overlaps.isNotEmpty()) {
                    val location = getScannerLocation(overlaps)
                    locations.add(location.first)
                    println("Merging in scanner ${scanner.id}")
                    primary = mergeScanners(primary, scanner, location.first, location.second)
                    toInspect.remove(scanner)
                    merged = true
                    break
                }
            }
            if (!merged) {
                TODO()
            }
        }

        var maxDistance = 0
        for (location1 in locations) {
            for (location2 in locations) {
                if (location1 == location2) continue

                val distance =
                    abs(location1.x - location2.x) + abs(location1.y - location2.y) + abs(location1.z - location2.z)
                maxDistance = max(maxDistance, distance)
            }
        }
        println(maxDistance)
    }
}