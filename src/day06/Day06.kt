package day06

import readInput
import utils.Position
import utils.swap
import utils.walkIndexed
import utils.withStopwatch

private const val MAX_PATH_LENGTH = 25000

private enum class Direction(val character: Char) {
    Up('^') {
        override fun nextPosition(position: Position) = Position(position.x, position.y - 1)
        override fun changeDirection() = Right
    },
    Down('v') {
        override fun nextPosition(position: Position) = Position(position.x, position.y + 1)
        override fun changeDirection() = Left
    },
    Left('<') {
        override fun nextPosition(position: Position) = Position(position.x - 1, position.y)
        override fun changeDirection() = Up
    },
    Right('>') {
        override fun nextPosition(position: Position) = Position(position.x + 1, position.y)
        override fun changeDirection() = Down
    };

    abstract fun nextPosition(position: Position): Position
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
    position: Position,
    direction: Direction,
    guardPath: Set<Position>,
): Set<Position> {
    val possibleObstructions = mutableSetOf<Position>()
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
    position: Position,
    direction: Direction,
    obstacle: Position
): Boolean {
    var counter = 0
    var currentPosition = position
    var currentDirection = direction
    val visitedPositions = mutableMapOf<Position, Direction>()

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
    position: Position,
    direction: Direction,
): Set<Position> {
    var currentPosition = position
    var currentDirection = direction
    val visitedPositions = mutableSetOf<Position>()
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

private fun List<String>.findGuard(): Pair<Position, Direction> {
    walkIndexed { x, y, c ->
        val position = Position(x, y)
        Direction.getDirection(c)?.let { direction ->
            return position to direction
        }
    }

    throw Exception("There is no initial guard position my boi, check your input")
}

private fun List<String>.isInBounds(position: Position) = position.y in indices && position.x in first().indices

private fun List<String>.getChar(position: Position) = this[position.y][position.x]

private fun List<String>.clearBoard(position: Position): List<String> {
    val temp = this.toMutableList()
    temp[position.y] = temp[position.y].swap(position.x, '.')
    return temp
}

private fun printBoard(
    board: List<String>,
    currentPosition: Position,
    direction: Direction,
    obstacle: Position = Position(-1, -1)
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