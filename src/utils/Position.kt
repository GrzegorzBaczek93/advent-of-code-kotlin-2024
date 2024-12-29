package utils

data class Position(
    val x: Int,
    val y: Int,
)

fun <T> Position.nextTopOrNull(map: List<List<T>>): Position? =
    Position(this.x, this.y - 1).takeIf { it.isInBounds(map) }

fun <T> Position.nextBottomOrNull(map: List<List<T>>): Position? =
    Position(this.x, this.y + 1).takeIf { it.isInBounds(map) }

fun <T> Position.nextLeftOrNull(map: List<List<T>>): Position? =
    Position(this.x - 1, this.y).takeIf { it.isInBounds(map) }

fun <T> Position.nextRightOrNull(map: List<List<T>>): Position? =
    Position(this.x + 1, this.y).takeIf { it.isInBounds(map) }

fun Position.nextTopOrNull(rangeX: IntRange, rangeY: IntRange): Position? =
    Position(this.x, this.y - 1).takeIf { it.isInBounds(rangeX, rangeY) }

fun Position.nextBottomOrNull(rangeX: IntRange, rangeY: IntRange): Position? =
    Position(this.x, this.y + 1).takeIf { it.isInBounds(rangeX, rangeY) }

fun Position.nextLeftOrNull(rangeX: IntRange, rangeY: IntRange): Position? =
    Position(this.x - 1, this.y).takeIf { it.isInBounds(rangeX, rangeY) }

fun Position.nextRightOrNull(rangeX: IntRange, rangeY: IntRange): Position? =
    Position(this.x + 1, this.y).takeIf { it.isInBounds(rangeX, rangeY) }

fun Position.getFourSides(rangeX: IntRange, rangeY: IntRange): List<Position> =
    listOfNotNull(
        this.nextTopOrNull(rangeX, rangeY),
        this.nextBottomOrNull(rangeX, rangeY),
        this.nextLeftOrNull(rangeX, rangeY),
        this.nextRightOrNull(rangeX, rangeY),
    )


fun <T> Position.isInBounds(map: List<List<T>>): Boolean {
    val indicesY = map.first().indices
    val indicesX = map.indices

    return this.isInBounds(indicesX, indicesY)
}

fun Position.isInBounds(rangeX: IntRange, rangeY: IntRange): Boolean {
    return this.x in rangeX && this.y in rangeY
}

fun <T> List<List<T>>.get(position: Position) = this[position.y][position.x]