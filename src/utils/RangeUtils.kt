package utils

fun IntRange.isSubRange(other: IntRange): Boolean =
    first in other && last in other || other.first in this && other.last in this

fun IntRange.isOverlapping(other: IntRange): Boolean =
    first in other || last in other || other.first in this || other.last in this
