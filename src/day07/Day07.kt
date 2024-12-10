package day07

import readInput
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_day07_test")
    val input = readInput("input_day07")

    withStopwatch {
        println(solve(input))
    }
}

private fun solve(input: List<String>): Long {
    return input.fold(0L) { acc, line ->
        val (p1, p2) = line.split(':')
        val testValue = p1.toLong()
        val numbers = p2.split(' ').filter { it.isNotEmpty() }.map { it.toLong() }

        if (verifyOperators(numbers.first(), numbers.slice(1..numbers.indices.last), testValue) > 0) {
            acc + testValue
        } else {
            acc
        }
    }
}

private fun verifyOperators(first: Long, rest: List<Long>, testValue: Long): Int {
    val addition = first + rest.first()
    val multiplication = first * rest.first()

    if (rest.size == 1) {
        val b1 = if (addition == testValue) 1 else 0
        val b2 = if (multiplication == testValue) 1 else 0
        return b1 + b2
    }

    val r1 = verifyOperators(addition, rest.slice(1..rest.indices.last), testValue)
    val r2 = verifyOperators(multiplication, rest.slice(1..rest.indices.last), testValue)

    return r1 + r2
}