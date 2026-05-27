fun sumOfSquares(list: List<Int>): Int {
    return list.fold(0) {acc, value -> acc + (value * value)}
}

fun hasDuplicate (list: List<Int>): Boolean {
    val set = list.toSet()
    return list.size != set.size
}