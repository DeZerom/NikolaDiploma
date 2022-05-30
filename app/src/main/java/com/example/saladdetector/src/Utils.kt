package com.example.saladdetector.src

import kotlin.math.pow
import kotlin.math.roundToInt

fun round(value: Double, places: Int): Double {
    require(places >= 0)
    val factor = 10.0.pow(places.toDouble()).toLong()
    val tmp = (value * factor).roundToInt()
    return tmp.toDouble() / factor
}