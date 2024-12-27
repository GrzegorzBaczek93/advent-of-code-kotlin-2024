package day09

import readInput
import utils.parity
import utils.withStopwatch

fun main() {
    val testInput = readInput("input_day09_test").first()
    val input = readInput("input_day09").first()

    withStopwatch {
        println(input.toFileSystem().defragmented().calculateChecksum())
        println(input.toChunkedFileSystem().defragmentedChunked().calculateChunkedChecksum())
    }
}

private fun String.toFileSystem(): MutableList<Int> {
    var counter = 0
    val fs = mutableListOf<Int>()

    forEachIndexed { index, char ->
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
    return fs
}

private fun String.toChunkedFileSystem(): MutableList<Chunk> {
    var counter = 0
    val fs = mutableListOf<Chunk>()

    forEachIndexed { index, char ->
        val size = char.digitToInt()
        index.parity(
            onEven = { fs.addLast(Chunk.File(size, counter++)) },
            onOdd = { fs.addLast(Chunk.Empty(size)) }
        )
    }
    return fs
}

private fun MutableList<Int>.defragmented(): List<Int> {
    var index = 0
    while (index < this.indices.last) {
        if (this[index] == -1) {
            var lastIndex = this.indices.last
            while (this[lastIndex] == -1 && lastIndex > index) {
                this.removeLast()
                lastIndex -= 1
            }
            this[index] = this[lastIndex]
            this.removeLast()
        }
        index++
    }

    return this
}

private fun MutableList<Chunk>.defragmentedChunked(): List<Chunk> {
    var endIndex = this.indices.last

    while (endIndex in this.indices) {
        if (this[endIndex] is Chunk.Empty) {
            --endIndex
            continue
        }
        var startIndex = 0
        while (startIndex in 0..<endIndex) {
            if (this[startIndex] is Chunk.File) {
                ++startIndex
                continue
            }

            if (this[startIndex].size == this[endIndex].size) {
                this.add(startIndex, this[endIndex])
                this[endIndex + 1] = Chunk.Empty(this[endIndex + 1].size)
                this.removeAt(startIndex + 1)
                break
            }

            if (this[startIndex].size > this[endIndex].size) {
                this[startIndex].size -= this[endIndex].size
                this.add(startIndex, this[endIndex])
                this[endIndex + 1] = Chunk.Empty(this[endIndex + 1].size)
                break
            }

            ++startIndex
        }
        --endIndex
    }

    return this
}


private fun List<Int>.calculateChecksum(): Long =
    foldIndexed(0L) { i, acc, element ->
        acc + i * element
    }

private fun List<Chunk>.calculateChunkedChecksum(): Long {
    var index = 0
    return fold(0L) { acc, chunk ->
        var temp = 0L
        repeat(chunk.size) {
            if (chunk is Chunk.File) temp += index * chunk.id
            ++index
        }
        acc + temp
    }
}

private sealed interface Chunk {
    var size: Int

    data class Empty(override var size: Int) : Chunk
    data class File(override var size: Int, val id: Int) : Chunk
}

private fun List<Chunk>.print() {
    this.forEach { chunk ->
        when (chunk) {
            is Chunk.Empty -> repeat(chunk.size) { print('.') }
            is Chunk.File -> repeat(chunk.size) { print(chunk.id) }
        }
    }
    println()
}
