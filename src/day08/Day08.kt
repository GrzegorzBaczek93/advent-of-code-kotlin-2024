package day08

import readInput
import utils.Point
import utils.walkIndexed
import utils.withStopwatch
import kotlin.math.abs

fun main() {
    val testInput = readInput("input_day08_test")
    val input = readInput("input_day08")

    withStopwatch {
        println(solve(input).count())
    }
}

private fun solve(input: List<String>): Set<Point> {
    val antennas = mutableMapOf<Char, Set<Point>>()
    val freqLocations = mutableSetOf<Point>()

    val xRange = input.first().indices
    val yRange = input.indices

    input.walkIndexed { x, y, c ->
        if (c != '.') {
            val positions = antennas.getOrDefault(c, emptySet())
            antennas[c] = positions.plus(Point(x, y))
        }
    }

    antennas.forEach { (_, set) ->
        val antennasPositions = set.toMutableSet()
        var current = antennasPositions.first()
        antennasPositions.remove(current)

        while (antennasPositions.isNotEmpty()) {
            antennasPositions.forEach { other ->
                fun getNewValues(a: Int, b: Int): Pair<Int, Int> {
                    return when {
                        a > b -> {
                            val diff = abs(a - b)
                            a + diff to b - diff
                        }

                        b > a -> {
                            val diff = abs(a - b)
                            a - diff to b + diff
                        }

                        else -> {
                            a to b
                        }
                    }
                }

                val (y1, y2) = getNewValues(current.y, other.y)
                val (x1, x2) = getNewValues(current.x, other.x)

                if (x1 in xRange && y1 in yRange) freqLocations.add(Point(x1, y1))
                if (x2 in xRange && y2 in yRange) freqLocations.add(Point(x2, y2))
            }
            current = antennasPositions.first()
            antennasPositions.remove(current)
        }
    }

    printBoard(input, freqLocations)
    return freqLocations
}

private fun printBoard(
    board: List<String>,
    freqLocations: Set<Point>,
) {
    board.walkIndexed { x, y, c ->
        when {
            freqLocations.contains(Point(x, y)) -> print('#')
            else -> print(c)
        }
        if (x == board.first().indices.last) println()
    }
}