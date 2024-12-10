package day06

import readInput
import utils.Point
import utils.swap
import utils.walkIndexed
import utils.withStopwatch

private enum class Direction(val character: Char) {
    Up('^') {
        override fun nextPosition(position: Point) = Point(position.x, position.y - 1)
        override fun changeDirection() = Right
    },
    Down('v') {
        override fun nextPosition(position: Point) = Point(position.x, position.y + 1)
        override fun changeDirection() = Left
    },
    Left('<') {
        override fun nextPosition(position: Point) = Point(position.x - 1, position.y)
        override fun changeDirection() = Up
    },
    Right('>') {
        override fun nextPosition(position: Point) = Point(position.x + 1, position.y)
        override fun changeDirection() = Down
    };

    abstract fun nextPosition(position: Point): Point
    abstract fun changeDirection(): Direction

    companion object {
        fun getDirection(char: Char): Direction? = entries.firstOrNull { it.character == char }
    }
}

fun main() {
    val testInput = readInput("input_day06_test")
    val input = readInput("input_day06")

    withStopwatch {
        println(input.countGuardRoute())
    }
}

private fun List<String>.countGuardRoute(): Int {
    var (position, direction) = this.findGuard()
    val cleanBoard = this.clearBoard(position)
    val visitedPositions = mutableSetOf<Point>()
    visitedPositions.add(position)

    while (cleanBoard.isInBounds(position)) {
        val nextPosition = direction.nextPosition(position)

        if (!cleanBoard.isInBounds(nextPosition)) break

        when {
            cleanBoard.getChar(nextPosition) == '.' -> {
                position = nextPosition
            }

            cleanBoard.getChar(nextPosition) == '#' -> {
                direction = direction.changeDirection()
                position = direction.nextPosition(position)
            }
        }
        visitedPositions.add(position)
    }

    return visitedPositions.size
}

private fun List<String>.findGuard(): Pair<Point, Direction> {
    walkIndexed { x, y, c ->
        val position = Point(x, y)
        Direction.getDirection(c)?.let { direction ->
            return position to direction
        }
    }

    throw Exception("There is no initial guard position my boi, check your input")
}

private fun List<String>.isInBounds(point: Point) = point.y in indices && point.x in first().indices

private fun List<String>.getChar(point: Point) = this[point.y][point.x]

private fun List<String>.clearBoard(point: Point): List<String> {
    val temp = this.toMutableList()
    temp[point.y] = temp[point.y].swap(point.x, '.')
    return temp
}

private fun List<String>.printBoard(currentPosition: Point, direction: Direction) {
    walkIndexed { x, y, c ->
        when {
            x == currentPosition.x && y == currentPosition.y -> print(direction.character)
            else -> print(c)
        }
        if (x == first().indices.last) println()
    }
    println("----------------------------------------")
}