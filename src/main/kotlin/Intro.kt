fun greet(name: String?): String = "Hello, ${name ?: "Guest"}!"

fun grade(score: Int):String = when {
    score >= 90 -> "A"
    score >= 80 -> "B"
    score >= 70 -> "C"
    score >= 60 -> "D"
    else -> "F"
}

data class Point(val x: Int, val y: Int)