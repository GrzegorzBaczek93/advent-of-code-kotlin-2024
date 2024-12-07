package day04

import readInput
import utils.walkIndexed
import utils.withStopwatch

private const val XMAS_PATTERN = "XMAS"
private const val MAS_PATTERN = "MAS"

fun main() {
    val testInput = readInput("input_day04_test")
    val input = readInput("input_day04")

    withStopwatch {
        println(verifyPt1(input))
        println(verifyPt2(input))
    }
}

private fun verifyPt1(input: List<String>): Int {
    var result = 0
    input.walkIndexed { x, y, c ->
        if (c == 'X') {
            if (input.verifyRight(x, y, XMAS_PATTERN)) result++
            if (input.verifyLeft(x, y, XMAS_PATTERN)) result++
            if (input.verifyTop(x, y, XMAS_PATTERN)) result++
            if (input.verifyBottom(x, y, XMAS_PATTERN)) result++
            if (input.verifyTopRight(x, y, XMAS_PATTERN)) result++
            if (input.verifyTopLeft(x, y, XMAS_PATTERN)) result++
            if (input.verifyBottomRight(x, y, XMAS_PATTERN)) result++
            if (input.verifyBottomLeft(x, y, XMAS_PATTERN)) result++
        }
    }
    return result
}

private fun verifyPt2(input: List<String>): Int {
    var result = 0

    input.walkIndexed { x, y, c ->
        if (c == 'A') {
            if (x - 1 >= 0 && x + 1 <= input.first().length - 1 && y - 1 >= 0 && y + 1 <= input.indices.last) {
                if ((input.verifyBottomRight(x - 1, y - 1, MAS_PATTERN) || input.verifyBottomRight(
                        x - 1,
                        y - 1,
                        MAS_PATTERN.reversed()
                    )) &&
                    (input.verifyTopRight(x - 1, y + 1, MAS_PATTERN) || input.verifyTopRight(
                        x - 1,
                        y + 1,
                        MAS_PATTERN.reversed()
                    ))
                ) {
                    result++
                }
            }
        }
    }

    return result
}

private fun List<String>.verifyRight(x: Int, y: Int, pattern: String): Boolean {
    if (size - 1 - x < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y][x + index] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyLeft(x: Int, y: Int, pattern: String): Boolean {
    if (x < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y][x - index] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyTop(x: Int, y: Int, pattern: String): Boolean {
    if (y < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y - index][x] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyBottom(x: Int, y: Int, pattern: String): Boolean {
    if (indices.last - y < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y + index][x] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyTopRight(x: Int, y: Int, pattern: String): Boolean {
    if (y < pattern.length - 1 || size - 1 - x < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y - index][x + index] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyTopLeft(x: Int, y: Int, pattern: String): Boolean {
    if (y < pattern.length - 1 || x < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y - index][x - index] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyBottomRight(x: Int, y: Int, pattern: String): Boolean {
    if (indices.last - y < pattern.length - 1 || size - 1 - x < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y + index][x + index] != pattern[index]) return false }
    return true
}

private fun List<String>.verifyBottomLeft(x: Int, y: Int, pattern: String): Boolean {
    if (indices.last - y < pattern.length - 1 || x < pattern.length - 1) return false
    pattern.indices.forEach { index -> if (this[y + index][x - index] != pattern[index]) return false }
    return true
}