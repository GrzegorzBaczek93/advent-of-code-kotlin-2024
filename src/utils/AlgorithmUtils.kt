package utils

/**
 * Least common multiplier algorithm
 */
fun lcm(numbers: List<Long>): Long {
    return numbers.fold(1) { acc, number -> (acc * number) / gcd(acc, number) }
}

/**
 * Greatest common divisor algorithm
 */
fun gcd(num1: Long, num2: Long): Long {
    return if (num2 == 0L) num1 else gcd(num2, num1 % num2)
}
