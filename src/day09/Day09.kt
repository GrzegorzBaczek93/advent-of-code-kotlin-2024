package day09

import readInput
import utils.asNumber
import utils.parity
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_day09_test").first()
    val input = readInput("input_day09").first()

    withStopwatch {
        println(solvePt1(input))
//        println(input)
    }
}

private fun solvePt1(input: String): Long {
    var counter = 0
    val fs = mutableListOf<Int>()

    input.forEachIndexed { index, char ->
        index.parity(
            onEven = {
                repeat(char.digitToInt()) { fs.addLast(counter) }
                counter++
            },
            onOdd = {
                repeat(char.digitToInt()) { fs.addLast(-1) }
            }
        )
    }

    var index = 0
    while (index < fs.indices.last) {
        if (fs[index] == -1) {
            var lastIndex = fs.indices.last
            while (fs[lastIndex] == -1 && lastIndex > index) {
                fs.removeLast()
                lastIndex -= 1
            }
            fs[index] = fs[lastIndex]
            fs.removeLast()
        }
        index++
    }

    return fs.foldIndexed(0L) { i, acc, element ->
        acc + i * element
    }
}