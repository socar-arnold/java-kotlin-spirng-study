import kotlin.math.sqrt

data class Point(val x: Int, val y: Int) {
    init {
        require(x in 0..24){ "x 좌표는 0~24 사이여야 합니다."}
        require(y in 0..24){ "y 좌표는 0~24 사이여야 합니다."}
    }
}

interface Measurable {
    fun measure(): Double
}

class Line(val p1: Point, val p2: Point) : Measurable {
    override fun measure(): Double {
        val dx = (p2.x - p1.x).toDouble()
        val dy = (p2.y - p1.y).toDouble()
        return sqrt(dx * dx + dy * dy)
    }
}
class Triangle(val p1: Point, val p2:Point, val p3: Point) : Measurable {
    override  fun measure(): Double {
        val a = Line(p1,p2).measure()
        val b = Line(p2,p3).measure()
        val c = Line(p3,p1).measure()
        val s = (a+b+c) / 2.0
        return sqrt(s*(s-a)*(s-b)*(s-c))
    }
}
class Rectangle(val p1: Point, val p2: Point, val p3: Point, val p4: Point) : Measurable {
    init {
        require(isRightAngle(p1, p2, p3)) { "직사각형이 아닙니다" }
        require(isRightAngle(p4, p1, p2)) { "직사각형이 아닙니다" }
    }

    override fun measure(): Double {
        val width = Line(p1, p2).measure()
        val height = Line(p2, p3).measure()
        return width*height
    }

    private fun isRightAngle(a: Point, b: Point, c:Point): Boolean {
        val v1x = b.x - a.x
        val v1y = b.y - a.y
        val v2x = c.x - b.x
        val v2y = c.y - b.y

        return (v1x * v2x + v1y * v2y) == 0

    }
}

