import kotlin.math.sqrt

data class Coordinate(val x: Int, val y:Int)

fun distance(a: Coordinate, b: Coordinate): Double {
    val dx = (a.x - b.x).toDouble()
    val dy = (a.y - b.y).toDouble()

    return sqrt(dx * dx + dy * dy)
}

fun main () {
    print("점1 (x y 공백): ")
    val (x1, y1) = readln().trim().split(" ").map{it.toInt()}
    print("점2 (x y 공백): ")
    val (x2, y2) = readln().trim().split(" ").map{it.toInt()}
    val d = distance(Coordinate(x1, y1), Coordinate(x2, y2))
    print("거리 : $d")
}