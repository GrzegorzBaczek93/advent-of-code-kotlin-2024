package day10

import readInput
import utils.*

fun main() {
    val testInput = readInput("input_day10_test")
    val input = readInput("input_day10")

    withStopwatch {
        println(countTrails(input.asListOfInt()))
        println(countTrails(input.asListOfInt(), false))
    }
}

private fun countTrails(input: List<List<Int>>, distinct: Boolean = true): Int {
    val heads = findAllHeads(input)

    return heads.fold(0) { acc, head ->
        acc + findTrails(input, head).let { if (distinct) it.toSet() else it }.count()
    }
}

private fun findTrails(input: List<List<Int>>, pos: Point): List<Point> {
    if (input.get(pos) == 9) {
        return listOf(pos)
    }

    return listOfNotNull(
        pos.nextTopOrNull(input),
        pos.nextBottomOrNull(input),
        pos.nextLeftOrNull(input),
        pos.nextRightOrNull(input),
    ).filter { next -> input.get(next) == input.get(pos) + 1 }
        .map { next -> findTrails(input, next) }
        .flatten()
}

private fun findAllHeads(input: List<List<Int>>): List<Point> {
    val elements = mutableListOf<Point>()

    input.indexed { x, y, e ->
        if (e == 0) elements.addLast(Point(x, y))
    }

    return elements
}
