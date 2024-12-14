package utils

fun Int.parity(onEven: () -> Unit = {}, onOdd: () -> Unit = {}) {
    if (this % 2 == 0) {
        onEven()
    } else {
        onOdd()
    }
}