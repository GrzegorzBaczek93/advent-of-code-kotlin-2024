package utils

private val SPECIAL_CHAR_REGEX = Regex("""[ -\/:-@\[-\`{-~]""")

fun Char.asNumber(): Int {
    return if (this.isLowerCase()) {
        this.code - 96
    } else {
        this.code - 38
    }
}

fun Char.isDot(): Boolean {
    return this == '.'
}

fun Char.isSpecial(): Boolean {
    return SPECIAL_CHAR_REGEX.matches("$this")
}
