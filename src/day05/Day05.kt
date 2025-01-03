package day05

import readInput
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_day05_test")
    val input = readInput("input_day05")

    withStopwatch {
        println(solve(input))
        println(solveWithCorrection(input))
    }
}

private fun solve(input: List<String>): Int {
    val (orderingRules, updates) = getOrderingRules(input)
    val parsedRules = parseOrderingRules(orderingRules)

    var result = 0

    updates.forEach { update ->
        val parsedUpdate = parseUpdate(update)
        var isCorrect = true
        parsedUpdate.forEachIndexed { index, i ->
            val rules = parsedRules.getOrDefault(i, emptySet())
            val numbersBefore = parsedUpdate.slice(0..<index)
            if (numbersBefore.any { rules.contains(it) }) {
                isCorrect = false
                return@forEachIndexed
            }
        }
        if (isCorrect) result += parsedUpdate[parsedUpdate.size / 2]
    }

    return result
}

private fun solveWithCorrection(input: List<String>): Int {
    val (orderingRules, updates) = getOrderingRules(input)
    val parsedRules = parseOrderingRules(orderingRules)
    var result = 0

    updates.forEach { update ->
        val parsedUpdate = parseUpdate(update)
        val correctedUpdate = mutableListOf<Int>()
        var isCorrect = true

        parsedUpdate.forEachIndexed { index, i ->
            val rules = parsedRules.getOrDefault(i, emptySet())
            if (parsedUpdate.firstIncorrectIndex(index, rules) != -1) {
                isCorrect = false
                run correction@{
                    (index - 1 downTo 0).forEach { i2 ->
                        if (correctedUpdate.firstIncorrectIndex(i2, rules) == -1) {
                            correctedUpdate.add(i2, i)
                            return@correction
                        }
                    }
                }
            } else {
                correctedUpdate.addLast(i)
            }
        }
        if (!isCorrect) result += correctedUpdate[correctedUpdate.size / 2]
    }

    return result
}

private fun List<Int>.firstIncorrectIndex(index: Int, rules: Set<Int>) =
    this.slice(0..<index).indexOfFirst { rules.contains(it) }

private fun getOrderingRules(input: List<String>): Pair<List<String>, List<String>> {
    val emptyLineIndex = input.indexOfFirst { it.isEmpty() }
    return input.slice(0..<emptyLineIndex) to input.slice(emptyLineIndex + 1..input.indices.last)
}

private fun parseOrderingRules(input: List<String>): Map<Int, Set<Int>> {
    val parsed = mutableMapOf<Int, Set<Int>>()

    input.forEach { rule ->
        val (x, y) = rule.split('|').map { it.toInt() }

        if (parsed.containsKey(x)) {
            val temp = parsed[x]!!
            parsed += x to (temp + y)
        } else {
            parsed += x to setOf(y)
        }
    }

    return parsed
}

private fun parseUpdate(update: String): List<Int> {
    return update.split(',').map { it.toInt() }
}