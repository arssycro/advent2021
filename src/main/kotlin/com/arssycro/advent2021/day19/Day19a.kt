package com.arssycro.advent2021.day19

import com.arssycro.advent2021.readList

fun main() {
    Day19a().process()
}

class Day19a {
    fun process() {
        val inputs = readList("/day19.txt")
        val scanners = getScanners(inputs)

        var primary = scanners[0]
        val toInspect = scanners.subList(1, scanners.size).toMutableList()
        while (toInspect.isNotEmpty()) {
            var merged = false
            for (scanner in toInspect) {
                val overlaps = findOverlappingBeacons(primary, scanner)
                if (overlaps.isNotEmpty()) {
                    val location = getScannerLocation(overlaps)
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
        println(primary.beacons.size)
    }
}