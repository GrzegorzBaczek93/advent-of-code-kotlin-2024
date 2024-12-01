package utils

import java.text.SimpleDateFormat
import java.util.*

fun <T> withStopwatch(action: () -> T): T {
    val startTime = System.currentTimeMillis()
    return action().also { printTime(System.currentTimeMillis() - startTime) }
}

private fun printTime(millis: Long) = println("Action took: ${formatMilliseconds(millis)}")

private fun formatMilliseconds(millis: Long): String {
    val dateFormat = SimpleDateFormat("ss:SSSS")
    return dateFormat.format(Date(millis))
}
