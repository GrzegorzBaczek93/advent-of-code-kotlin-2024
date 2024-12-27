package utils

data class Point(
    val x: Int,
    val y: Int,
)

fun <T> Point.nextTopOrNull(map: List<List<T>>): Point? = Point(this.x, this.y - 1).takeIf { it.isInBounds(map) }

fun <T> Point.nextBottomOrNull(map: List<List<T>>): Point? = Point(this.x, this.y + 1).takeIf { it.isInBounds(map) }

fun <T> Point.nextLeftOrNull(map: List<List<T>>): Point? = Point(this.x - 1, this.y).takeIf { it.isInBounds(map) }

fun <T> Point.nextRightOrNull(map: List<List<T>>): Point? = Point(this.x + 1, this.y).takeIf { it.isInBounds(map) }

fun <T> Point.isInBounds(map: List<List<T>>): Boolean {
    val indicesY = map.first().indices
    val indicesX = map.indices

    return this.x in indicesX && this.y in indicesY
}

fun <T> List<List<T>>.get(point: Point) = this[point.y][point.x]