package day12

import readInput
import utils.*

fun main() {
    val testInput = readInput("input_day12_test")
    val input = readInput("input_day12")

    withStopwatch {
        solve(input).println()
    }
}

private fun solve(input: List<String>): Int {
    var result = 0
    val rangeX = 0..input.first().length
    val rangeY = 0..input.size

    val tiles = mutableSetOf<FarmTile>()
    input.walkIndexed { x, y, type -> tiles.add(FarmTile(Position(x, y), type)) }

    while (tiles.isNotEmpty()) {
        val checked = mutableListOf<FarmTile>()
        val unchecked = mutableListOf<FarmTile>()
        unchecked.add(tiles.first())
        tiles.remove(tiles.first())

        while (unchecked.isNotEmpty()) {
            val current = unchecked.first()
            unchecked.removeFirst()

            current.position.getFourSides(rangeX, rangeY).mapNotNull { pos ->
                tiles.firstOrNull { it.position == pos && it.type == current.type }
            }.onEach {
                unchecked.add(it)
                tiles.remove(it)
            }

            checked.add(current)
        }

        checked.forEach { tile ->
            tile.sides = 4 - tile.position.getFourSides(rangeX, rangeY)
                .map { pos -> if (checked.firstOrNull { it.position == pos } == null) 0 else 1 }.sum()
        }

        result += checked.size * checked.sumOf { it.sides }
    }

    return result
}


data class FarmTile(
    val position: Position,
    val type: Char,
    var sides: Int = 0,
)
