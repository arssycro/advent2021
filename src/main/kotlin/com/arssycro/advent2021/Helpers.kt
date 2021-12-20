package com.arssycro.advent2021

import kotlin.reflect.KClass

fun readList(file: String) = KClass::class.java.getResource(file)!!.readText().split("\r\n")

fun List<*>.printEach() = forEach { println(it) }
