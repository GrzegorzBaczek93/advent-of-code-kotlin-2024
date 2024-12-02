package day01

import readInput
import utils.putOrIncreaseBy
import utils.withStopwatch
import kotlin.math.abs

fun main() {
    val testInput = readInput("input_day01_test")
    val input = readInput("input_day01")

    withStopwatch {
        println("Distance: ${calculateDistance(testInput)}")
        println("Similarity: ${calculateSimilarity(testInput)}")
        println("Distance: ${calculateDistance(input)}")
        println("Similarity: ${calculateSimilarity(input)}")
    }
}

private fun calculateDistance(input: List<String>): Int {
    var result = 0

    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()
    input.forEach { line ->
        line.parseLine().let { pair ->
            firstList.add(pair.first)
            secondList.add(pair.second)
        }
    }

    firstList.sort()
    secondList.sort()

    firstList.indices.forEach { index ->
        result += abs(firstList[index] - secondList[index])
    }

    return result
}

private fun calculateSimilarity(input: List<String>): Int {
    var result = 0

    val list = mutableListOf<Int>()
    val map = mutableMapOf<Int, Int>()

    input.forEach { line ->
        line.parseLine().let { pair ->
            list.add(pair.first)
            map.putOrIncreaseBy(pair.second)
        }
    }

    list.forEach { result += it * map.getOrDefault(it, 0) }

    return result
}

private fun String.parseLine(): Pair<Int, Int> {
    val (first, second) = split("   ").map { it.toInt() }
    return first to second
}
