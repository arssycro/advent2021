package com.arssycro.advent2021.day23

import java.util.*

interface Amphipod {
    val steps: Int
    val stepCost: Int

    fun totalCost() = steps * stepCost
}

data class Amber(val id: Int, override val steps: Int) : Amphipod {
    override val stepCost: Int
        get() = 1
}

data class Bronze(val id: Int, override val steps: Int) : Amphipod {
    override val stepCost: Int
        get() = 10
}

data class Copper(val id: Int, override val steps: Int) : Amphipod {
    override val stepCost: Int
        get() = 100
}

data class Desert(val id: Int, override val steps: Int) : Amphipod {
    override val stepCost: Int
        get() = 1000
}

fun makeGrid(inputs: List<String>): List<List<Any>> {
    val grid = mutableListOf<List<Any>>()
    val counts = mutableMapOf<Char, Int>()
    for (input in inputs) {
        val subList = input.map {
            val count = counts[it] ?: 1
            when (it) {
                'A' -> {
                    counts['A'] = count + 1
                    Amber(count, 0)
                }
                'B' -> {
                    counts['B'] = count + 1
                    Bronze(count, 0)
                }
                'C' -> {
                    counts['C'] = count + 1
                    Copper(count, 0)
                }
                'D' -> {
                    counts['D'] = count + 1
                    Desert(count, 0)
                }
                else -> it
            }
        }
        grid.add(subList)
    }
    return grid
}

data class Move(
    val amphipod: Amphipod,
    val start: Pair<Int, Int>,
    val end: Pair<Int, Int>,
    val moves: Int,
    val cost: Int = moves * amphipod.stepCost
)

fun moveAmphipod(grid: MutableList<MutableList<Any>>, move: Move) {
    val newAmphipod = when (move.amphipod) {
        is Amber -> Amber(move.amphipod.id, move.amphipod.steps + move.moves)
        is Bronze -> Bronze(move.amphipod.id, move.amphipod.steps + move.moves)
        is Copper -> Copper(move.amphipod.id, move.amphipod.steps + move.moves)
        is Desert -> Desert(move.amphipod.id, move.amphipod.steps + move.moves)
        else -> TODO()
    }
    grid[move.start.first][move.start.second] = '.'
    grid[move.end.first][move.end.second] = newAmphipod
}

fun getAmphipodLocations(grid: List<List<Any>>): Map<Amphipod, Pair<Int, Int>> {
    val locations = mutableMapOf<Amphipod, Pair<Int, Int>>()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            val item = grid[i][j]
            if (item is Amphipod) {
                locations[item] = Pair(i, j)
            }
        }
    }
    return locations
}

fun isAmphipodRoom(roomIndex: Int, amphipod: Amphipod): Boolean {
    return when (amphipod) {
        is Amber -> roomIndex == 3
        is Bronze -> roomIndex == 5
        is Copper -> roomIndex == 7
        is Desert -> roomIndex == 9
        else -> TODO()
    }
}

fun playGameRecursive(
    grid: List<List<Any>>,
    currentMinimum: Int,
    moves: List<Move>,
    knownValues: MutableMap<List<List<Any>>, Int>
): Int {

//            println("${queue.size} - ${state.done} - ${state.edges} - ${state.currentTotal}")
//            state.grid.forEach {
//                println(it.joinToString("") { a ->
//                    when (a) {
//                        is Amphipod -> a.javaClass.simpleName[0]
//                        else -> a
//                    }.toString()
//                })
//            }

    val currentMovesCost = moves.sumOf { it.cost }
    if (currentMovesCost > currentMinimum) {
        return Int.MAX_VALUE
    }
    if (isSolved(grid)) {
        println("Solved!")
        println(currentMovesCost)

        return 0
    }

    val possibleMoves = getPossibleMoves(grid)
    if (possibleMoves.isEmpty()) {
        return Int.MAX_VALUE
    }
    val cheapestMoves = possibleMoves.sortedBy { it.moves * it.amphipod.stepCost }
    var minimum = currentMinimum
    for (move in cheapestMoves) {
        val newGrid = grid.map { it.toMutableList() }.toMutableList()
        moveAmphipod(newGrid, move)
        val cost = if (knownValues.containsKey(newGrid)) {
            val known = knownValues[newGrid]!!
            val totalCost = if (known == Int.MAX_VALUE) known else known + move.cost
            totalCost
        } else {
            val cost = playGameRecursive(newGrid, minimum, moves + move, knownValues)
            val totalCost = if (cost == Int.MAX_VALUE) cost else cost + move.cost
            knownValues[newGrid] = totalCost
            totalCost
        }
        minimum = minOf(minimum, cost)
    }
    return minimum
}

fun playGameIterative(startGrid: List<List<Any>>): Int {
    val queue =
        PriorityQueue(compareByDescending<State> { it.done }
            .also { compareBy<State> { it.currentTotal } })
    queue.add(State(startGrid))

    val foundValues = mutableMapOf<List<List<Any>>, Int>()
    var minimum = Int.MAX_VALUE

    // TODO: Add cache
    while (queue.isNotEmpty()) {
        val state = queue.remove()
//            println("${queue.size} - ${state.done} - ${state.edges} - ${state.currentTotal}")
//            state.grid.forEach {
//                println(it.joinToString("") { a ->
//                    when (a) {
//                        is Amphipod -> a.javaClass.simpleName[0]
//                        else -> a
//                    }.toString()
//                })
//            }
        if (isSolved(state.grid)) {
            minimum = minOf(minimum, state.currentTotal)
            foundValues[state.grid] = state.currentTotal
            println(minimum)
            println(queue.size)
            continue
        }

        if (state.currentTotal > minimum) {
            continue
        }

        val possibleMoves = getPossibleMoves(state.grid).reversed()
        if (possibleMoves.isEmpty()) {
            continue
        }
        val cheapestMove = possibleMoves.minOf { it.moves * it.amphipod.stepCost }
        if (cheapestMove + state.currentTotal > minimum) {
            continue
        }
        for (move in possibleMoves) {
            val newGrid = state.grid.map { it.toMutableList() }.toMutableList()
            moveAmphipod(newGrid, move)
            queue.add(State(newGrid))
        }
    }
    return minimum
}

fun getPossibleMoves(grid: List<List<Any>>): List<Move> {
    val possibleMoves = mutableListOf<Move>()
    val amphipodLocations = getAmphipodLocations(grid)
    for ((amphipod, location) in amphipodLocations) {
        possibleMoves.addAll(getPossibleMoves(grid, amphipod, location))
    }
    return possibleMoves
}

fun getPossibleMoves(grid: List<List<Any>>, amphipod: Amphipod, location: Pair<Int, Int>): List<Move> {
    if (isBlocked(grid, location)) {
        return listOf()
    }
    if (isAmphipodRoom(location.second, amphipod)) {
        if (location.first == grid.lastIndex - 1 || (location.first in 2..(grid.lastIndex - 2) && checkRoomBelow(
                grid,
                (location.second - 1) / 2,
                location.first
            ))
        ) {
            return listOf()
        }
    }

    val currentMoves = if (location.first > 1) location.first - 1 else 0

    val walk = walkHallway(grid, amphipod, location.second)
    return walk.filter { currentMoves != 0 || it.key.first != 1 }
        .map { Move(amphipod, location, it.key, it.value + currentMoves) }
}

fun walkHallway(grid: List<List<Any>>, amphipod: Amphipod, beginJ: Int): Map<Pair<Int, Int>, Int> {
    val steps = mutableMapOf<Pair<Int, Int>, Int>()
    var moves = 0
    for (j in beginJ - 1 downTo 0) {
        moves++
        if (grid[1][j] != '.') {
            break
        }

        val below = grid[2][j]
        if (below == '#') {
            steps[Pair(1, j)] = moves
        } else if (below == '.') {
            if (isAmphipodRoom(j, amphipod) && isRoomOpen(grid, j, amphipod)) {
                for (n in grid.lastIndex downTo 2) {
                    if (n == 2) {
                        steps[Pair(n, j)] = moves + 1
                        break
                    }
                    if (grid.subList(3, n + 1).all { it[j] == '.' }) {
                        steps[Pair(n, j)] = moves + (n - 1)
                        break
                    }
                }
            }
        }
    }

    moves = 0
    for (j in beginJ + 1..grid[1].size) {
        moves++
        if (grid[1][j] != '.') {
            break
        }

        val below = grid[2][j]
        if (below == '#') {
            steps[Pair(1, j)] = moves
        } else if (below == '.') {
            if (isAmphipodRoom(j, amphipod) && isRoomOpen(grid, j, amphipod)) {
                for (n in grid.lastIndex downTo 2) {
                    if (n == 2) {
                        steps[Pair(n, j)] = moves + 1
                        break
                    }
                    if (grid.subList(3, n + 1).all { it[j] == '.' }) {
                        steps[Pair(n, j)] = moves + (n - 1)
                        break
                    }
                }
            }
        }
    }

    return steps
}

fun isBlocked(grid: List<List<Any>>, location: Pair<Int, Int>): Boolean {
    val i = location.first
    val j = location.second
    if (grid[i - 1][j] is Amphipod) {
        return true
    }
    for (n in 3..grid.lastIndex) {
        if (i == n && grid[i - (n - 1)][j] != '.') {
            return true
        }
    }
    if (grid[i - 1][j] != '.') {
        if (grid[i][j - 1] != '.') {
            if (grid[i][j + 1] != '.') {
                return true
            }
        }
    }
    return false
}

fun isSolved(grid: List<List<Any>>): Boolean {
    return checkRoom(grid, 1) && checkRoom(grid, 2) && checkRoom(grid, 3) && checkRoom(grid, 4)
}

fun isRoomOpen(grid: List<List<Any>>, roomIndex: Int, amphipod: Amphipod): Boolean {
    return grid.subList(2, grid.size - 1).all { it[roomIndex] == '.' || it[roomIndex]::class == amphipod::class }
}

fun checkRoomBelow(grid: List<List<Any>>, room: Int, row: Int): Boolean {
    return when (room) {
        1 -> grid.subList(row, grid.size - 1).all { it[3] is Amber }
        2 -> grid.subList(row, grid.size - 1).all { it[5] is Bronze }
        3 -> grid.subList(row, grid.size - 1).all { it[7] is Copper }
        4 -> grid.subList(row, grid.size - 1).all { it[9] is Desert }
        else -> TODO()
    }
}

fun checkRoom(grid: List<List<Any>>, room: Int): Boolean {
    return checkRoomBelow(grid, room, 2)
}


fun countDone(grid: List<List<Any>>): Int {
    var count = 0
    for (i in grid.lastIndex - 1..2) {
        if (grid[i][3] is Amber) {
            count++
        } else {
            break
        }
    }
    for (i in grid.lastIndex - 1..2) {
        if (grid[i][3] is Bronze) {
            count++
        } else {
            break
        }
    }
    for (i in grid.lastIndex - 1..2) {
        if (grid[i][3] is Copper) {
            count++
        } else {
            break
        }
    }
    for (i in grid.lastIndex - 1..2) {
        if (grid[i][3] is Desert) {
            count++
        } else {
            break
        }
    }
    return count
}

data class State(
    val grid: List<List<Any>>,
    val done: Int = countDone(grid),
    val currentTotal: Int = getAmphipodLocations(grid).keys.sumOf { it.totalCost() }
)