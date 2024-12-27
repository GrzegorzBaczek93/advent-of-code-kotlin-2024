package utils

/**
 * Prints each element of given collection in new line
 */
fun Collection<Any>.printLines() {
    val iterator = this.iterator()
    while(iterator.hasNext()) {
        println(iterator.next())
    }
}

/**
 * Prints any value in new line
 */
fun Any.println() {
    println(this)
}

/**
 * Prints any value in same line
 */
fun Any.print() {
    print(this)
}