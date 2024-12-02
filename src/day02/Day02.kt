package day02

import readInput
import utils.withStopwatch
import utils.without
import kotlin.math.abs

fun main() {
    val testInput = readInput("input_day02_test")
    val input = readInput("input_day02")

    withStopwatch {
        println("Safe reports: ${testInput.countSafe()}")
        println("Safe reports: ${input.countSafe()}")
    }
}

private fun List<String>.countSafe(): Int {
    return map { it.parse().isSafeWithCorrection() }.count { it }
}

private fun String.parse(): List<Int> = split(" ").map { it.toInt() }

private fun List<Int>.isSafeWithCorrection(): Boolean {
    return if (isSafe()) true else correctAndTryAgain()
}

private fun List<Int>.isSafe(): Boolean {
    var isIncreasing = false
    var isDecreasing = false

    (1..<size).forEach { index ->
        val diff = get(index) - get(index - 1)
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

private fun List<Int>.correctAndTryAgain(): Boolean {
    (2..<size).forEach { index ->
        val e1 = get(index - 2)
        val e2 = get(index - 1)
        val e3 = get(index)

        // high - low - high
        if (e1 > e2 && e2 < e3) {
            if (without(index - 2).isSafe()) return true
            if (without(index - 1).isSafe()) return true
            if (without(index).isSafe()) return true
            return false
        }

        // low - high - low
        if (e1 < e2 && e2 > e3) {
            if (without(index - 2).isSafe()) return true
            if (without(index - 1).isSafe()) return true
            if (without(index).isSafe()) return true
            return false
        }

        // first two are equal or diff is bigger than 3
        if (e1 == e2 || abs(e1 - e2) > 3) {
            if (without(index - 2).isSafe()) return true
            if (without(index - 1).isSafe()) return true
            return false
        }

        // last two are equal or diff is bigger than 3
        if (e2 == e3 || abs(e2 - e3) > 3) {
            if (without(index - 1).isSafe()) return true
            if (without(index - 0).isSafe()) return true
            return false
        }
    }

    throw Exception("This shouldn't really happen xD")
}
