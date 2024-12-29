package day11

import readInput
import utils.println
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_day11_test")
    val input = readInput("input_day11")

    withStopwatch {
        solve(input.first()).println()
    }
}

private fun solve(input: String): Int {
    var stones = input.split(" ").map { it.toLong() }

    repeat(25) {
        val temp = mutableListOf<Long>()
        stones.forEach { stone ->
            temp.addAll(stone.transform())
        }
        stones = temp
    }

    return stones.count()
}

private fun Long.transform(): List<Long> {
    return when {
        this == 0L -> listOf(1L)
        this.containsEventNumberOfDigits() -> {
            val first = this.toString().let { it.slice(0..<it.length / 2) }.toLong()
            val second = this.toString().let { it.slice(it.length / 2..<it.length) }.toLong()
            listOf(first, second)
        }

        else -> listOf(this * 2024L)
    }
}

private fun Long.containsEventNumberOfDigits(): Boolean {
    return this.toString().length % 2 == 0
}