package day08

import readInput
import utils.Position
import utils.walkIndexed
import utils.withStopwatch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val testInput = readInput("input_day08_test")
    val input = readInput("input_day08")

    withStopwatch {
        println(solvePt1(input).count())
        println(solvePt2(input).count())
    }
}

private fun solvePt2(input: List<String>): Set<Position> {
    val antennas = mutableMapOf<Char, Set<Position>>()
    val freqLocations = mutableSetOf<Position>()

    val xRange = input.first().indices
    val yRange = input.indices

    input.walkIndexed { x, y, c ->
        if (c != '.') {
            val positions = antennas.getOrDefault(c, emptySet())
            antennas[c] = positions.plus(Position(x, y))
        }
    }

    freqLocations.addAll(antennas.flatMap { it.value })

    antennas.forEach { (_, set) ->
        val antennasPositions = set.toMutableSet()
        var current = antennasPositions.first()
        antennasPositions.remove(current)

        while (antennasPositions.isNotEmpty()) {
            antennasPositions.forEach { other ->
                when {
                    current.x == other.x -> {
                        generateAntennas(current.y, other.y, yRange) { y ->
                            freqLocations.add(Position(current.x, y))
                        }
                    }

                    current.y == other.y -> {
                        generateAntennas(current.x, other.x, xRange) { x ->
                            freqLocations.add(Position(x, current.y))
                        }
                    }

                    else -> {
                        generateAntennas(current, other, xRange, yRange) { x, y ->
                            freqLocations.add(Position(x, y))
                        }
                    }
                }
            }
            current = antennasPositions.first()
            antennasPositions.remove(current)
        }
    }

    return freqLocations
}

private fun generateAntennas(p1: Position, p2: Position, xRange: IntRange, yRange: IntRange, onValue: (Int, Int) -> Unit) {
    val diffY = abs(p1.y - p2.y)
    val diffX = abs(p1.x - p2.x)
    val (px1, px2) = listOf(p1, p2).sortedBy { it.x }

    if (px1.y > px2.y) {
        var currentX = px1.x - diffX
        var currentY = px1.y + diffY
        while (currentX in xRange && currentY in yRange) {
            onValue(currentX, currentY)
            currentX -= diffX
            currentY += diffY
        }

        currentX = px2.x + diffX
        currentY = px2.y - diffY
        while (currentX in xRange && currentY in yRange) {
            onValue(currentX, currentY)
            currentX += diffX
            currentY -= diffY
        }
    }
    if (px1.y < px2.y) {
        var currentX = px1.x - diffX
        var currentY = px1.y - diffY
        while (currentX in xRange && currentY in yRange) {
            onValue(currentX, currentY)
            currentX -= diffX
            currentY -= diffY
        }

        currentX = px2.x + diffX
        currentY = px2.y + diffY
        while (currentX in xRange && currentY in yRange) {
            onValue(currentX, currentY)
            currentX += diffX
            currentY += diffY
        }
    }
}

private fun generateAntennas(v1: Int, v2: Int, maxRange: IntRange, onValue: (Int) -> Unit) {
    val diffY = abs(v1 - v2)

    for (v in min(v1, v2) downTo maxRange.first step diffY) {
        onValue(v)
    }
    for (v in max(v1, v2)..maxRange.last step diffY) {
        onValue(v)
    }
}

private fun solvePt1(input: List<String>): Set<Position> {
    val antennas = mutableMapOf<Char, Set<Position>>()
    val freqLocations = mutableSetOf<Position>()

    val xRange = input.first().indices
    val yRange = input.indices

    input.walkIndexed { x, y, c ->
        if (c != '.') {
            val positions = antennas.getOrDefault(c, emptySet())
            antennas[c] = positions.plus(Position(x, y))
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

                if (x1 in xRange && y1 in yRange) freqLocations.add(Position(x1, y1))
                if (x2 in xRange && y2 in yRange) freqLocations.add(Position(x2, y2))
            }
            current = antennasPositions.first()
            antennasPositions.remove(current)
        }
    }

    return freqLocations
}