package utils


/**
 * Returns 6th *element* from the list.
 *
 * Throws an [IndexOutOfBoundsException] if the size of this list is less than 6.
 */
operator fun <T> List<T>.component6(): T {
    return get(5)
}

/**
 * Returns 7th *element* from the list.
 *
 * Throws an [IndexOutOfBoundsException] if the size of this list is less than 7.
 */
operator fun <T> List<T>.component7(): T {
    return get(6)
}

/**
 * Returns 8th *element* from the list.
 *
 * Throws an [IndexOutOfBoundsException] if the size of this list is less than 8.
 */
operator fun <T> List<T>.component8(): T {
    return get(7)
}
