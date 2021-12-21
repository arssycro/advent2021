package com.arssycro.advent2021.day21

import com.arssycro.advent2021.readList

fun main() {
    Day20b().process()
}

class Day20b {
    fun process() {
        val inputs = readList("/day21.txt")
        val players = getPlayers(inputs)
        val startState = State(players[0].position, players[1].position, players[0].score, players[1].score)

        var player1Wins = 0L
        var player2Wins = 0L

        val states = mutableMapOf(startState to 1L)
        while (states.isNotEmpty()) {
            val (state, count) = states.entries.first()
            states.remove(state)

            for (roll1a in 1..3) {
                for (roll1b in 1..3) {
                    for (roll1c in 1..3) {
                        val roll1 = roll1a + roll1b + roll1c
                        val newPosition1 = (state.position1 + roll1) % 10
                        val newScore1 = state.score1 + newPosition1 + 1
                        if (newScore1 >= 21) {
                            player1Wins += count
                        } else {
                            for (roll2a in 1..3) {
                                for (roll2b in 1..3) {
                                    for (roll2c in 1..3) {
                                        val roll2 = roll2a + roll2b + roll2c
                                        val newPosition2 = (state.position2 + roll2) % 10
                                        val newScore2 = state.score2 + newPosition2 + 1
                                        if (newScore2 >= 21) {
                                            player2Wins += count
                                        } else {
                                            val newState = State(newPosition1, newPosition2, newScore1, newScore2)
                                            states[newState] = (states[newState] ?: 0L) + count
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        println(player1Wins)
        println(player2Wins)
    }
}

data class State(
    val position1: Int,
    val position2: Int,
    val score1: Int,
    val score2: Int
)