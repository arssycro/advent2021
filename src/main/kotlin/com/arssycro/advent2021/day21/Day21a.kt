package com.arssycro.advent2021.day21

import com.arssycro.advent2021.readList

fun main() {
    Day20a().process()
}

class Day20a {
    fun process() {
        val inputs = readList("/day21.txt")
        val players = getPlayers(inputs)
        val diceRolls = play(players)
        val loser = players.first { it.score < 1000 }
        println((diceRolls - 1) * loser.score)
    }

    fun play(players: List<Player>): Int {
        var dice = 1
        var rolls = 0
        while (true) {
            for (player in players) {
                var total = dice * 3 + 3
                rolls += 3
                dice += 3
                player.position = ((player.position + total) % 10)
                player.score += player.position + 1
                if (player.score >= 1000) {
                    return dice
                }
            }
        }
    }
}