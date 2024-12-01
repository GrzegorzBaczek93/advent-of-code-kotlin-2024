package utils

inline fun <P1, P2, R> multiLet(p1: P1?, p2: P2?, action: (P1, P2) -> R): R? {
    return if (p1 != null && p2 != null) action(p1, p2) else null
}

inline fun <P1, P2, P3, R> multiLet(p1: P1?, p2: P2?, p3: P3?, action: (P1, P2, P3) -> R): R? {
    return if (p1 != null && p2 != null && p3 != null) action(p1, p2, p3) else null
}

inline fun <P1, P2, P3, P4, R> multiLet(p1: P1?, p2: P2?, p3: P3?, p4: P4?, action: (P1, P2, P3, P4) -> R): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) action(p1, p2, p3, p4) else null
}
