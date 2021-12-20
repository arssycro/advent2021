package com.arssycro.advent2021

import kotlin.reflect.KClass

fun readList(file: String) = KClass::class.java.getResource(file)!!.readText().split("\r\n")

fun List<*>.printEach() = forEach { println(it) }

fun Array<BooleanArray>.print() = forEach {
    println(it.joinToString("") { b -> if (b) "#" else "." })
}