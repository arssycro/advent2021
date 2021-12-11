package com.arssycro.advent2021.day10

val chars = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
val openChars = chars.keys
val closeChars = chars.values

fun Char.isOpen() = openChars.contains(this)
fun Char.isClose() = closeChars.contains(this)
fun Char.closedBy(closer: Char) = chars[this] == closer