package day02

import readInput
import utils.withStopwatch
import kotlin.math.abs

fun main() {
    val testInput = readInput("input_02_test")
    val input = readInput("input_02")

    withStopwatch {
        println("Safe reports: ${testInput.countSafe()}")
    }
}

private fun List<String>.countSafe(): Int {
    return map { it.isSafe() }.count { it }
}

private fun String.isSafe(): Boolean {
    val level = split(" ").map { it.toInt() }
    var isIncreasing = false
    var isDecreasing = false

    (1..<level.size).forEach { index ->
        val diff = level[index] - level[index - 1]
        if (diff > 0) isIncreasing = true
        if (diff < 0) isDecreasing = true

        when {
            diff == 0 -> return false
            isIncreasing && isDecreasing -> return false
            abs(diff) > 3 -> return false
        }
    }

    return true
}