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