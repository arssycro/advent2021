package com.arssycro.advent2021

import java.net.URL
import kotlin.reflect.KClass

fun readList(file: String) = KClass::class.java.getResource(file)!!.readText().split("\r\n")

