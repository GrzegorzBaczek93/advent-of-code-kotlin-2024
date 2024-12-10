package day06

import readInput
import utils.Point
import utils.swap
import utils.walkIndexed
import utils.withStopwatch

private const val MAX_PATH_LENGTH = 25000

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
        val (position, direction) = input.findGuard()
        val cleanBoard = input.clearBoard(position)
        val guardPath = guardPath(cleanBoard, position, direction)
        println(possibleObstructions(cleanBoard, position, direction, guardPath.minus(position)).count())
    }
}

private fun possibleObstructions(
    board: List<String>,
    position: Point,
    direction: Direction,
    guardPath: Set<Point>,
): Set<Point> {
    val possibleObstructions = mutableSetOf<Point>()
    val mutableGuardPath = guardPath.toMutableSet()

    while (mutableGuardPath.isNotEmpty()) {
        val obstacle = mutableGuardPath.first()
        if (verifyPath(board, position, direction, obstacle)) {
            possibleObstructions.add(obstacle)
        }
        mutableGuardPath.remove(obstacle)
    }
    return possibleObstructions
}

private fun verifyPath(
    board: List<String>,
    position: Point,
    direction: Direction,
    obstacle: Point
): Boolean {
    var counter = 0
    var currentPosition = position
    var currentDirection = direction
    val visitedPositions = mutableMapOf<Point, Direction>()

    while (counter < MAX_PATH_LENGTH) {
        val nextPosition = currentDirection.nextPosition(currentPosition)

        when {
            !board.isInBounds(nextPosition) -> return false
            board.getChar(nextPosition) == '#' || nextPosition == obstacle -> {
                currentDirection = currentDirection.changeDirection()
            }

            board.getChar(nextPosition) == '.' -> {
                counter++
                currentPosition = nextPosition
                if (visitedPositions[currentPosition] == currentDirection) return true
                visitedPositions[currentPosition] = currentDirection
            }
        }
    }
    return false
}

private fun guardPath(
    board: List<String>,
    position: Point,
    direction: Direction,
): Set<Point> {
    var currentPosition = position
    var currentDirection = direction
    val visitedPositions = mutableSetOf<Point>()
    visitedPositions.add(position)

    while (board.isInBounds(currentPosition)) {
        val nextPosition = currentDirection.nextPosition(currentPosition)

        when {
            !board.isInBounds(nextPosition) -> break
            board.getChar(nextPosition) == '.' -> {
                currentPosition = nextPosition
                visitedPositions.add(currentPosition)
            }

            board.getChar(nextPosition) == '#' -> {
                currentDirection = currentDirection.changeDirection()
            }
        }
    }

    return visitedPositions
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

private fun printBoard(
    board: List<String>,
    currentPosition: Point,
    direction: Direction,
    obstacle: Point = Point(-1, -1)
) {
    board.walkIndexed { x, y, c ->
        when {
            x == obstacle.x && y == obstacle.y -> print('0')
            x == currentPosition.x && y == currentPosition.y -> print(direction.character)
            else -> print(c)
        }
        if (x == board.first().indices.last) println()
    }
    println("----------------------------------------")
}