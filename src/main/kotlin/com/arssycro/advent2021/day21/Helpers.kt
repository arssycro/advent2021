package com.arssycro.advent2021.day21

data class Player(
    val id: Int,
    var position: Int,
    var score: Int
)

val regex = Regex("""Player (\d+) starting position: (\d+)""")

//Player 1 starting position: 2
fun getPlayers(inputs: List<String>): List<Player> {
    return inputs.map {
        val (id, position) = regex.matchEntire(it)!!.destructured
        Player(id.toInt(), position.toInt() - 1, 0)
    }
}