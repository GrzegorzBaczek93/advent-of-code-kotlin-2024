package utils

inline fun <T> List<T>.ifNotEmpty(action: (List<T>) -> Unit) {
    if (isNotEmpty()) action(this)
}

inline fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val resultList = mutableListOf<List<T>>()
    val buffer = mutableListOf<T>()

    for (element in this) {
        if (predicate(element)) {
            buffer.ifNotEmpty { resultList.add(buffer.toList()) }
            buffer.clear()
        } else {
            buffer.add(element)
        }
    }
    buffer.ifNotEmpty { resultList.add(buffer.toList()) }

    return resultList.toList()
}

/**
 * Removes n last entries from list and return it.
 */
fun <T> MutableList<T>.removeLast(n: Int): List<T> {
    if (isEmpty()) {
        return emptyList()
    }
    if (n >= size) {
        val removedItems = this.toList()
        clear()
        return removedItems
    }
    val removedItems = this.takeLast(n)
    repeat(n) { removeLast() }
    return removedItems
}

/**
 * Returns the multiplication of all elements in the collection.
 */
fun List<Int>.multiply(): Int {
    if (this.isEmpty()) return 0

    var sum = 1
    for (element in this) {
        sum *= element
    }
    return sum
}

/**
 * Returns the multiplication of all elements in the collection.
 */
fun List<Long>.multiply(): Long {
    if (this.isEmpty()) return 0

    var sum: Long = 1
    for (element in this) {
        sum *= element
    }
    return sum
}

/**
 * For each indexed for two-dimensional
 */
inline fun List<String>.walkIndexed(action: (x: Int, y: Int, c: Char) -> Unit) {
    forEachIndexed { y, internal ->
        internal.forEachIndexed { x, e ->
            action(x, y, e)
        }
    }
}

/**
 * Increases value in index element or put default value there instead
 */
fun MutableList<Int>.putOrIncreaseBy(index: Int, value: Int = 1) {
    if (index < this.size) {
        this[index] += value
    } else {
        this.add(index, value)
    }
}

/**
 * Gets value for given index or put default value there if non existent
 */
fun <T> MutableList<T>.getOrPut(index: Int, defaultValue: T): T {
    return if (index in 0..lastIndex) {
        get(index)
    } else {
        add(index, defaultValue)
        defaultValue
    }
}

inline fun <T> Iterable<T>.sumOfIndexed(selector: (Int, T) -> Long): Long {
    var sum: Long = 0
    forEachIndexed { index, element ->
        sum += selector(index, element)
    }
    return sum
}
