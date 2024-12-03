package day03

import readInput
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_day03_test")
    val input = readInput("input_day03")

    withStopwatch {
        println(testInput.calculate())
        println(input.calculate())
    }
}

private fun List<String>.calculate(): Long {
    val filtered = filterOperations()
    var enabled = true
    var result = 0L

    filtered.onEach { operation ->
        when {
            operation.startsWith("mul") -> if (enabled) result += operation.multiply()
            operation.startsWith("don't") -> enabled = false
            operation.startsWith("do") -> enabled = true
        }
    }

    return result
}

private fun List<String>.filterOperations(): List<String> {
    val mulRegex = Regex("(mul\\([0-9]+,[0-9]+\\))|(don't\\(\\))|(do\\(\\))")
    return this.map { mulRegex.findAll(it).map { it.value }.toList() }.flatten()
}

private fun String.multiply(): Long {
    val (first, second) = split(',').map { it.filter { it.isDigit() }.toLong() }
    return first * second
}
